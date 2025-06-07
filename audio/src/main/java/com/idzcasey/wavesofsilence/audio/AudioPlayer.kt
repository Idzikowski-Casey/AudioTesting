package com.idzcasey.wavesofsilence.audio

import android.content.Context
import android.util.Log
import com.idzcasey.wavesofsilence.coroutines.ApplicationScope
import com.idzcasey.wavesofsilence.database.SoundSource
import com.idzcasey.wavesofsilence.database.SoundSourceData
import com.idzcasey.wavesofsilence.database.Mixes
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioPlayer @Inject constructor(
    @ApplicationScope private val appScope: CoroutineScope,
    @ApplicationContext private val context: Context
) {

    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.NoResultYet)
    val playerState = _playerState.asStateFlow()

    private val _soundSources =
        MutableStateFlow<Map<String, SoundSource>>(SoundSourceData.associateBy { it.id })

    /**
     * A set of currently playing sources. Strings are the IDs that should map to soundSources
     */
    private val _currentlyPlayingSources = MutableStateFlow<Set<String>>(emptySet())
    private val _currentlyPlayingMix = MutableStateFlow<String?>(null)

    // region public methods

    val soundSources = _soundSources.asStateFlow()
    val currentlyPlayingSources = _currentlyPlayingSources.asStateFlow()
    val currentlyPlayingMix = _currentlyPlayingMix.asStateFlow()
    val mixes = Mixes.associateBy { it.id }

    fun initializeSources() {
        val fileHelper = FileHelper()

        appScope.launch {
            SoundSourceData.forEach { soundSource ->
                Log.i("AudioPlayer", "Adding sound source: ${soundSource.name}")
                val result = if (soundSource.filename != null) {
                    // try and get buffer from file
                    val sharedBuffer = fileHelper.readWavAsFloatArray(context, soundSource.filename)
                    sharedBuffer?.let { buffer ->
                        addGenericBufferSoundSource(
                            soundSource.id,
                            soundSource.volume,
                            buffer,
                            soundSource.name
                        )
                    }
                } else {
                    // its a native sound source
                    addSoundSource(
                        soundSource.id,
                        soundSource.type.id,
                        soundSource.volume,
                        soundSource.name
                    )
                }
                // if added successfully in SoundMixer, add to state
                if (result == 0) {
                    Log.i("AudioPlayer", "Added sound source: ${soundSource.name}")
                } else {
                    Log.e("AudioPlayer", "Failed to add sound source: ${soundSource.name}")
                }
            }
        }
    }

    fun togglePlayBack() {
        if (playerState.value == PlayerState.Started) {
            setPlaybackEnabled(false)
        } else {
            setPlaybackEnabled(true)
        }
    }

    fun setSoundSourceVolume(id: String, volume: Float) {
        val currentSource = _soundSources.value[id] ?: return

        val result = updateSoundSourceVolume(id, volume)

        if (result == 0) {
            // A OKAY
            _soundSources.update {
                it.plus(id to currentSource.copy(volume = volume))
            }
            if (volume > 0f) {
                _currentlyPlayingSources.update {
                    it.plus(id)
                }
            } else {
                _currentlyPlayingSources.update {
                    it.minus(id)
                }
            }
            // clear currently playing mix because
            // a volume was changed after a mix was started
            _currentlyPlayingMix.value = null
        }
    }

    /**
     * Starts a mix with the given ID
     *
     * Will first stop any currently playing soundSources and
     * then set volume for each soundSource in the mix
     *
     * @param id The ID of the mix to start
     */
    fun startMix(id: String) {
        val mix = mixes[id] ?: return

        // turn off all existing sounds
        currentlyPlayingSources.value.forEach {
            setSoundSourceVolume(it, 0f)
        }

        // set volume for each sound source in mix
        mix.soundSources.forEach {
            setSoundSourceVolume(it.id, it.volume)
        }
        _currentlyPlayingMix.value = id

        // attempt to start playback if not already started
        if (playerState.value != PlayerState.Started) {
            setPlaybackEnabled(true)
        }
    }

    /**
     * Stops the currently playing mix given an ID
     *
     * First validates that the mix is currently playing and exists
     * Then turns off all sounds in the mix and stops playback if playback ongoing
     *
     * @param id The ID of the mix to stop
     */
    fun stopMix(id: String) {
        if (_currentlyPlayingMix.value != id) return
        val mix = mixes[id] ?: return

        // turn off all sounds in mix
        mix.soundSources.forEach {
            setSoundSourceVolume(it.id, 0f)
        }
        _currentlyPlayingMix.value = null

        if (playerState.value == PlayerState.Started) {
            setPlaybackEnabled(false)
        }
    }

    // endregion

    // region private funtions

    private fun setPlaybackEnabled(enabled: Boolean) {

        appScope.launch {
            val result = if (enabled) {
                startPlayer()
            } else {
                stopPlayer()
            }

            val newUiState = if (result == 0) {
                if (enabled) {
                    PlayerState.Started
                } else {
                    PlayerState.Stopped
                }
            } else {
                PlayerState.Unknown(result)
            }

            _playerState.update { newUiState }
        }
    }

    fun release() {
        setPlaybackEnabled(false)
    }

    // endregion

    // region native methods

    private external fun startPlayer(): Int
    private external fun stopPlayer(): Int
    private external fun updateSoundSourceVolume(id: String, volume: Float): Int
    private external fun addSoundSource(
        id: String,
        type: Int,
        volume: Float,
        displayName: String
    ): Int

    private external fun addGenericBufferSoundSource(
        id: String,
        volume: Float,
        buffer: FloatArray,
        displayName: String
    ): Int

    // end region

    companion object {
        // Used to load the 'audio' library on application startup.
        init {
            System.loadLibrary("audio")
        }
    }
}
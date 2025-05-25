package com.application.audio

import android.content.Context
import android.util.Log
import com.application.coroutines.ApplicationScope
import com.application.database.SoundSource
import com.application.database.SoundSourceData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.Closeable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioPlayer @Inject constructor(
    @ApplicationScope private val appScope: CoroutineScope,
    @ApplicationContext private val context: Context
): Closeable {

    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.NoResultYet)
    val playerState = _playerState.asStateFlow()

    private val _soundSources =
        MutableStateFlow<Map<String, SoundSource>>(SoundSourceData.associateBy { it.id })

    // region public methods

    val soundSources = _soundSources.asStateFlow()

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
                if( result == 0) {
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
        appScope.launch {
            val result = updateSoundSourceVolume(id, volume)

            if (result == 0) {
                // A OKAY
                _soundSources.update {
                    it.plus(id to currentSource.copy(volume = volume))
                }
            }
        }
    }

    override fun close() {
        release()
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
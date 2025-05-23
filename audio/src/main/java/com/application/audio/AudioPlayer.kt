package com.application.audio

import android.content.Context
import androidx.lifecycle.ViewModel
import com.application.database.SoundSource
import com.application.database.SoundSourceData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AudioPlayer(
    private val viewModelScope: CloseableCoroutineScope = CloseableCoroutineScope()
) : ViewModel(viewModelScope) {

    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.NoResultYet)
    val playerState = _playerState.asStateFlow()

    private val _soundSources =
        MutableStateFlow<Map<String, SoundSource>>(emptyMap())

    val soundSources = _soundSources.asStateFlow()

    // region public methods

    fun initializeSources(context: Context) {
        val fileHelper = FileHelper()

        viewModelScope.launch {
            SoundSourceData.forEach { soundSource ->
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
                    _soundSources.update {
                        it.plus(soundSource.id to soundSource)
                    }
                }
            }
        }
    }

    fun setPlaybackEnabled(enabled: Boolean) {

        viewModelScope.launch {
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

    fun onStop() {
        setPlaybackEnabled(false)
    }

    fun setSoundSourceVolume(id: String, volume: Float) {
        val currentSource = _soundSources.value[id] ?: return
        viewModelScope.launch {
            val result = updateSoundSourceVolume(id, volume)

            if (result == 0) {
                // A OKAY
                _soundSources.update {
                    it.plus(id to currentSource.copy(volume = volume))
                }
            }
        }
    }

    override fun onCleared() {
        onStop()
        super.onCleared()
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
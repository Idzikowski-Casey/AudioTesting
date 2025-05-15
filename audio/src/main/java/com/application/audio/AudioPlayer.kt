package com.application.audio

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AudioPlayer(
    private val viewModelScope: CloseableCoroutineScope = CloseableCoroutineScope()
) : ViewModel(viewModelScope) {

    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.NoResultYet)
    val playerState = _playerState.asStateFlow()

    private val _soundSources = MutableStateFlow<Map<SoundSourceName, SoundSource>>(InitialState)

    init {
        viewModelScope.launch {
            _soundSources.collect {
                for (soundSource in it.values) {
                    updateSoundSourceVolume(soundSource.name.id, soundSource.volume)
                }
            }
        }
    }

    val soundSources = _soundSources.asStateFlow()

    // region public methods

    fun initializeSources() {
        viewModelScope.launch {
            initializeSoundSources()
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

    fun mutateSoundSource(soundSource: SoundSource) {
        _soundSources.update {
            it.plus(soundSource.name to soundSource)
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
    private external fun initializeSoundSources(): Int
    private external fun updateSoundSourceVolume(id: Int, volume: Float)

    // end region

    companion object {
        // Used to load the 'audio' library on application startup.
        init {
            System.loadLibrary("audio")
        }
    }
}
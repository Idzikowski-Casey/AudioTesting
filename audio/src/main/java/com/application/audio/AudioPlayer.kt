package com.application.audio

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AudioPlayer (
    private val viewModelScope: CloseableCoroutineScope = CloseableCoroutineScope()
) : ViewModel(viewModelScope) {

    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.NoResultYet)
    val playerState = _playerState.asStateFlow()

    private val _soundSources = MutableStateFlow<Map<SoundSourceName, SoundSource>>(
        mapOf(
            SoundSourceName.WHITE_NOISE to SoundSource(
                name = SoundSourceName.WHITE_NOISE,
                volume = 0.5f
            ),
            SoundSourceName.SINE_WAVE to SoundSource(
                name = SoundSourceName.SINE_WAVE,
                volume = 0.5f
            ),
        )
    )

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

sealed interface PlayerState {
    object NoResultYet : PlayerState
    object Started : PlayerState
    object Stopped : PlayerState
    data class Unknown(val resultCode: Int) : PlayerState
}

data class SoundSource(
    val name: SoundSourceName,
    val volume: Float
)

enum class SoundSourceName(val displayName: String, val id: Int) {
    SINE_WAVE("Sine Wave", 0),
    WHITE_NOISE("White Noise", 1),
    BROWN_NOISE("Brown Noise", 2),
    RAIN("Rain", 3),
    FIRE("Fire", 4)
}
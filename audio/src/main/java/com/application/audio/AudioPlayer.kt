package com.application.audio

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AudioPlayer {

    val coroutineScope = CoroutineScope(Dispatchers.Default + Job())
    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.NoResultYet)
    val playerState = _playerState.asStateFlow()

    // region public methods

    fun setPlaybackEnabled(enabled: Boolean) {

        coroutineScope.launch {
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

    // endregion

    // region native methods

    private external fun startPlayer(): Int
    private external fun stopPlayer(): Int

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
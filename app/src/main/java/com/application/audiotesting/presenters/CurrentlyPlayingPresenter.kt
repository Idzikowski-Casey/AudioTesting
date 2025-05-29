package com.application.audiotesting.presenters

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.application.audio.AudioPlayer
import com.application.audio.PlayerState
import com.application.audiotesting.data.AudioSliderData
import com.application.audiotesting.data.CurrentlyPlayingData
import com.application.audiotesting.data.PlayPauseData
import com.application.audiotesting.data.ViewDataModel
import javax.inject.Inject

class CurrentlyPlayingPresenter @Inject constructor(
    private val audioPlayer: AudioPlayer
) : Presenter {

    @Composable
    override fun present(): List<ViewDataModel> {
        val playerState by audioPlayer.playerState.collectAsState()
        val sources by audioPlayer.soundSources.collectAsState()

        val currentlyPlaying = sources.filter { it.value.volume > 0f }

        val icon = if (playerState == PlayerState.Started) {
            Icons.Filled.Pause
        } else {
            Icons.Filled.PlayArrow
        }

        return listOf(
            CurrentlyPlayingData(
                sounds = currentlyPlaying.map { (key, value) ->
                    AudioSliderData(
                        name = value.name,
                        volume = value.volume,
                        onValueChange = {
                            audioPlayer.setSoundSourceVolume(key, it)
                        }
                    )
                },
                playPauseData = PlayPauseData(
                    icon = icon,
                    onClick = {
                        audioPlayer.togglePlayBack()
                    }
                )
            )
        )
    }
}
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

/**
 * A Presenter class for the currently playing ribbon and expandable view.
 * The is part of the base scaffold and will appear on every page.
 */
class CurrentlyPlayingPresenter @Inject constructor(
    private val audioPlayer: AudioPlayer
) : Presenter {

    @Composable
    override fun present(): List<ViewDataModel> {
        val playerState by audioPlayer.playerState.collectAsState()
        val sources by audioPlayer.soundSources.collectAsState()
        val currentlyPlaying by audioPlayer.currentlyPlayingSources.collectAsState()

        val icon = if (playerState == PlayerState.Started) {
            Icons.Filled.Pause
        } else {
            Icons.Filled.PlayArrow
        }

        /**
         * Loop through the currently playing sources and get the SoundSource
         * object for metadata
         */
        val currentSources = currentlyPlaying.fold<String, List<AudioSliderData>>(
            emptyList(),
            { acc, key ->
                val source = sources[key]
                if (source != null) {
                    acc + AudioSliderData(
                        name = source.name,
                        volume = source.volume,
                        onValueChange = { volume ->
                            audioPlayer.setSoundSourceVolume(key, volume)
                        })
                } else {
                    acc
                }
            })


        return listOf(
            CurrentlyPlayingData(
                sounds = currentSources,
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
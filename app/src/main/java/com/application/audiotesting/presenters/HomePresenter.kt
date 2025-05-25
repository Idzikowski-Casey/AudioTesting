package com.application.audiotesting.presenters

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.application.audio.AudioPlayer
import com.application.audio.PlayerState
import com.application.audiotesting.data.AudioSliderData
import com.application.audiotesting.data.HeaderData
import com.application.audiotesting.data.PlayPauseData
import com.application.audiotesting.data.ViewDataModel
import com.application.database.FavoritesData
import com.application.database.SoundSource
import javax.inject.Inject

class HomePresenter @Inject constructor(private val audioPlayer: AudioPlayer) : Presenter {

    @Composable
    override fun present(): List<ViewDataModel> {
        val playerState by audioPlayer.playerState.collectAsState()
        val soundSources by audioPlayer.soundSources.collectAsState()

        val favorites = getFavorites(soundSources)

        val icon = if (playerState == PlayerState.Started) {
            Icons.Filled.Pause
        } else {
            Icons.Filled.PlayArrow
        }

        return listOf(
            HeaderData("Audio Testing with Oboe"),
            PlayPauseData(
                icon = icon,
                onClick = { audioPlayer.togglePlayBack() }
            )
        ) + favorites
    }

    @Composable
    fun getFavorites(soundSources: Map<String, SoundSource>): List<AudioSliderData> {
        return soundSources
            .filter { it.key in FavoritesData }
            .map { entry ->
                val id = entry.value.id
                val name = entry.value.name
                val volume = entry.value.volume

                val onValueChange = remember(id) {
                    { newVolume: Float -> audioPlayer.setSoundSourceVolume(id, newVolume) }
                }

                AudioSliderData(
                    name = name,
                    volume = volume,
                    onValueChange = onValueChange
                )
            }
    }
}
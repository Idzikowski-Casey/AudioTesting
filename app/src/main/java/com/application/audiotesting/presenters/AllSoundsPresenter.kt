package com.application.audiotesting.presenters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.application.audio.AudioPlayer
import com.application.audiotesting.data.SoundSliderData
import com.application.audiotesting.data.ViewDataModel
import javax.inject.Inject

class AllSoundsPresenter @Inject constructor(
    private val audioPlayer: AudioPlayer
) : Presenter {

    @Composable
    override fun present(): List<ViewDataModel> {

        val soundSources by audioPlayer.soundSources.collectAsState()

        return soundSources.map {
            SoundSliderData(
                name = it.value.name,
                volume = it.value.volume,
                onValueChange = { volume ->
                    audioPlayer.setSoundSourceVolume(it.key, volume)
                }
            )
        }
    }
}
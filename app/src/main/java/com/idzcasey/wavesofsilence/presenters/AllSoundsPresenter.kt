package com.idzcasey.wavesofsilence.presenters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.idzcasey.wavesofsilence.audio.AudioPlayer
import com.idzcasey.wavesofsilence.data.AudioSliderData
import com.idzcasey.wavesofsilence.data.ViewDataModel
import javax.inject.Inject

class AllSoundsPresenter @Inject constructor(
    private val audioPlayer: AudioPlayer
) : Presenter {

    @Composable
    override fun present(): List<ViewDataModel> {

        val soundSources by audioPlayer.soundSources.collectAsState()

        return soundSources.map {
            AudioSliderData(
                name = it.value.name,
                volume = it.value.volume,
                onValueChange = { volume ->
                    audioPlayer.setSoundSourceVolume(it.key, volume)
                }
            )
        }
    }
}
package com.idzcasey.wavesofsilence.presenters

import androidx.compose.runtime.Composable
import com.idzcasey.wavesofsilence.audio.AudioPlayer
import com.idzcasey.wavesofsilence.data.HeaderData
import com.idzcasey.wavesofsilence.data.ViewDataModel

class MixesPresenter(private val audioPlayer: AudioPlayer) : Presenter {

    @Composable
    override fun present(): List<ViewDataModel> {

        return listOf(HeaderData("Mixes"))
    }
}
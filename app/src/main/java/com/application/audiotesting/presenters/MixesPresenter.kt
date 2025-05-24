package com.application.audiotesting.presenters

import androidx.compose.runtime.Composable
import com.application.audio.AudioPlayer
import com.application.audiotesting.data.HeaderData
import com.application.audiotesting.data.ViewDataModel

class MixesPresenter(private val audioPlayer: AudioPlayer) : Presenter {

    @Composable
    override fun present(): List<ViewDataModel> {

        return listOf(HeaderData("Mixes"))
    }

}
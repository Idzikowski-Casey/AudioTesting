package com.idzcasey.wavesofsilence.presenters

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.ScreenModel
import com.idzcasey.wavesofsilence.data.ViewDataModel

interface Presenter : ScreenModel {

    @Composable
    fun present(): List<ViewDataModel>
}
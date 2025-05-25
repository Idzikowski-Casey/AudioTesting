package com.application.audiotesting.presenters

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.ScreenModel
import com.application.audiotesting.data.ViewDataModel

interface Presenter : ScreenModel {

    @Composable
    fun present(): List<ViewDataModel>
}
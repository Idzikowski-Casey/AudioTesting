package com.application.audiotesting.presenters

import androidx.compose.runtime.Composable
import com.application.audiotesting.data.ViewDataModel

interface Presenter {

    @Composable
    fun present(): List<ViewDataModel>
}
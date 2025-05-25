package com.application.audiotesting.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.application.audiotesting.presenters.AllSoundsPresenter

class AllSoundsScreen : Screen {
    @Composable
    override fun Content() {
        BaseScreen<AllSoundsPresenter>()
    }
}

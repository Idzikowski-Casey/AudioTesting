package com.application.audiotesting.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.application.audiotesting.presenters.HomePresenter

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        BaseScreen<HomePresenter>()
    }
}
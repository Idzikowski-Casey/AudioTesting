package com.idzcasey.wavesofsilence.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.idzcasey.wavesofsilence.presenters.AllSoundsPresenter

class AllSoundsScreen : Screen {
    @Composable
    override fun Content() {
        BaseScreen<AllSoundsPresenter>()
    }
}

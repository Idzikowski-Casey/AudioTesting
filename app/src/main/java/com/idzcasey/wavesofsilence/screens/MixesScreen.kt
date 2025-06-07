package com.idzcasey.wavesofsilence.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.idzcasey.wavesofsilence.presenters.MixesPresenter

class MixesScreen: Screen {

    @Composable
    override fun Content() {
        BaseScreen<MixesPresenter>()
    }
}
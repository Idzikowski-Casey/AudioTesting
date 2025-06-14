package com.idzcasey.wavesofsilence.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.idzcasey.wavesofsilence.screens.LibraryScreen

object LibraryTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Library"
            val icon = rememberVectorPainter(Icons.Default.LibraryMusic)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(LibraryScreen())
    }
}
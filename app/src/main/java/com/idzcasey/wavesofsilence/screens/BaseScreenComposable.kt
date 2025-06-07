package com.idzcasey.wavesofsilence.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import com.idzcasey.wavesofsilence.LocalBottomBarHeight
import com.idzcasey.wavesofsilence.data.ViewDataModel
import com.idzcasey.wavesofsilence.presenters.Presenter

/**
 * A Base Screen Content Composable to be used
 * by default for each voyager screen.
 */
@Composable
fun BaseScreenDefaultContent(items: List<ViewDataModel>) {
    LazyColumn(
        Modifier.fillMaxSize().padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = items,
            key = { it.stableKey() }
        ) { item ->
            item.Render(item)
        }

        // add a spacer to allow for all content on the screen
        // to be scrollable to.
        item {
            Spacer(Modifier.height(LocalBottomBarHeight.current))
        }
    }
}

/**
 * A Composable wrapper to abstract boilerplate code of getting the screen model
 */
@Composable
inline fun <reified T: Presenter> Screen.BaseScreen(
    noinline content: @Composable (List<ViewDataModel>) -> Unit = { BaseScreenDefaultContent(it) }
) {
    val presenter = getScreenModel<T>()
    val items = presenter.present()
    content(items)
}
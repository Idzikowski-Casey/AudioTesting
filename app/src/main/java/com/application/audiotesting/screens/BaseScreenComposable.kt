package com.application.audiotesting.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.hilt.getScreenModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.application.audiotesting.data.ViewDataModel
import com.application.audiotesting.presenters.Presenter

/**
 * A Base Screen Content Composable to be used
 * by default for each voyager screen.
 */
@Composable
fun BaseScreenDefaultContent(items: List<ViewDataModel>) {
    LazyColumn(
        Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = items,
            key = { it.stableKey() }
        ) { item ->
            item.Render(item)
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
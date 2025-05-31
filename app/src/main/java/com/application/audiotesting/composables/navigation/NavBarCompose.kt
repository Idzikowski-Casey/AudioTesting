package com.application.audiotesting.composables.navigation

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import com.application.audiotesting.tabs.HomeTab
import com.application.audiotesting.tabs.LibraryTab
import com.application.audiotesting.tabs.SettingsTab
import com.application.audiotesting.ui.theme.AudioTestingTheme

@Composable
fun NavBarCompose(tabs: List<Tab>) {
    CompositionLocalProvider(
        LocalContentColor provides Color.Unspecified
    ) {
        Surface(
            color = Color.Transparent,
            tonalElevation = 0.dp,
            shadowElevation = 0.dp
        ) {
            NavigationBar(
                containerColor = Color.Transparent,
                tonalElevation = 0.dp
            ) {
                tabs.map {
                    TabNavigationItem(tab = it)
                }
            }
        }
    }
}

@Preview
@Composable
private fun NavBarPreview() {
    val navItems = listOf(
        HomeTab,
        LibraryTab,
        SettingsTab
    )

    AudioTestingTheme {
        NavBarCompose(tabs = navItems)
    }
}
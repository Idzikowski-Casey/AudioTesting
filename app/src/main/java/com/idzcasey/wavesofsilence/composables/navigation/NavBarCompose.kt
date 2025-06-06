package com.idzcasey.wavesofsilence.composables.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import com.idzcasey.wavesofsilence.tabs.HomeTab
import com.idzcasey.wavesofsilence.tabs.LibraryTab
import com.idzcasey.wavesofsilence.tabs.SettingsTab
import com.idzcasey.wavesofsilence.ui.theme.AudioTestingTheme

@Composable
fun NavBarCompose(tabs: List<Tab>) {
    NavigationBar(
        containerColor = Color.White.copy(alpha = 0.5f),
        tonalElevation = 0.dp
    ) {
        tabs.map {
            TabNavigationItem(tab = it)
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
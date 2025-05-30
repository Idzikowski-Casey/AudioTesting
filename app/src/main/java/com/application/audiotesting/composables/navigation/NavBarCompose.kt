package com.application.audiotesting.composables.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import com.application.audiotesting.tabs.HomeTab
import com.application.audiotesting.tabs.LibraryTab
import com.application.audiotesting.tabs.SettingsTab

@Immutable
data class NavBarItem(
    val contentDescription: String,
    val icon: ImageVector,
    val selected: Boolean,
    val onClick: () -> Unit
)

@Composable
fun NavBarCompose(tabs: List<Tab>) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                .selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        tabs.map {
            TabNavigationItem(tab = it)
        }
    }
}

@Preview
@Composable
fun NavBarPreview() {
    val navItems = listOf(
        HomeTab,
        LibraryTab,
        SettingsTab
    )

    NavBarCompose(tabs = navItems)
}
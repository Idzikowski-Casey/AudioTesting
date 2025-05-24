package com.application.audiotesting.composables.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Immutable
data class NavBarItem(
    val contentDescription: String,
    val icon: ImageVector,
    val selected: Boolean,
    val onClick: () -> Unit
)

@Composable
fun NavBarCompose(navItems: List<NavBarItem>) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                .selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        navItems.map {
            NavigationBarItem(
                selected = it.selected,
                onClick = it.onClick,
                icon = {
                    Icon(
                        it.icon,
                        contentDescription = it.contentDescription
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun NavBarPreview() {
    val navItems = listOf(
        NavBarItem(
            contentDescription = "Home",
            icon = Icons.Filled.Home,
            selected = true,
            onClick = {}
        ),
        NavBarItem(
            contentDescription = "Library",
            icon = Icons.Filled.LibraryMusic,
            selected = false,
            onClick = {}
        ),
        NavBarItem(
            contentDescription = "Settings",
            icon = Icons.Filled.Settings,
            selected = false,
            onClick = {}
        ),
    )

    NavBarCompose(navItems = navItems)
}
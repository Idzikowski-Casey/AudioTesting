package com.application.audiotesting

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.application.audio.AudioPlayer
import com.application.audiotesting.composables.navigation.NavBarItem
import com.application.audiotesting.data.AppState
import com.application.audiotesting.data.Page
import com.application.audiotesting.data.ViewDataModel
import com.application.audiotesting.presenters.HomePresenter
import com.application.audiotesting.presenters.LibraryPresenter
import kotlinx.coroutines.flow.MutableStateFlow

class AppViewModel(
    private val audioPlayer: AudioPlayer,
) : ViewModel(audioPlayer) {

    private val appPage = MutableStateFlow<Page>(Page.HOME)
    private val isLoading = MutableStateFlow<Boolean>(false)

    private val libraryPresenter = LibraryPresenter(audioPlayer)

    @Composable
    fun getAppState(): AppState {
        val isLoading by isLoading.collectAsState()
        val page by appPage.collectAsState()

        val items: List<ViewDataModel> = when (page) {
            Page.HOME -> {
                HomePresenter(audioPlayer).present()
            }
            Page.LIBRARY -> {
                libraryPresenter.present()
            }
            Page.SETTINGS -> {
                listOf()
            }
            else -> listOf()
        }

        return AppState(
            isLoading = isLoading,
            items = items
        )
    }

    @Composable
    fun getNavBarItems(): List<NavBarItem> {
        val page by appPage.collectAsState()

        return listOf(
            NavBarItem(
                contentDescription = "Home",
                icon = Icons.Filled.Home,
                selected = page == Page.HOME,
                onClick = {
                    appPage.value = Page.HOME
                }
            ),
            NavBarItem(
                contentDescription = "Library",
                icon = Icons.Filled.LibraryMusic,
                selected = page == Page.LIBRARY,
                onClick = {
                    appPage.value = Page.LIBRARY
                }
            ),
            NavBarItem(
                contentDescription = "Settings",
                icon = Icons.Filled.Settings,
                selected = page == Page.SETTINGS,
                onClick = {
                    appPage.value = Page.SETTINGS
                }
            ),
        )
    }
}

class AppViewModelFactory(private val audioPlayer: AudioPlayer) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppViewModel(audioPlayer) as T
    }
}
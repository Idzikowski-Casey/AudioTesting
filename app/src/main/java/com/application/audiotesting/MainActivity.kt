package com.application.audiotesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.application.audio.AudioPlayer
import com.application.audiotesting.composables.navigation.NavBarCompose
import com.application.audiotesting.presenters.CurrentlyPlayingPresenter
import com.application.audiotesting.tabs.HomeTab
import com.application.audiotesting.tabs.LibraryTab
import com.application.audiotesting.tabs.SettingsTab
import com.application.audiotesting.ui.theme.AudioTestingTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var audioPlayer: AudioPlayer

    @Inject
    lateinit var currentlyPlayingPresenter: CurrentlyPlayingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        audioPlayer.initializeSources()

        setContent {
            TabNavigator(HomeTab) {
                AudioTestingTheme {
                    var bottomBarHeightDp by remember { mutableStateOf(0.dp) }

                    Scaffold(
                        containerColor = Color.Transparent,
                        modifier = Modifier.fillMaxSize(),
                        content = { innerPadding ->
                            Column(
                                modifier = Modifier
                                    .padding(
                                        top = innerPadding.calculateTopPadding(),
                                        start = innerPadding.calculateStartPadding(layoutDirection = LayoutDirection.Rtl),
                                        end = innerPadding.calculateEndPadding(layoutDirection = LayoutDirection.Rtl)
                                    )
                                    .fillMaxSize()
                            ) {
                                CompositionLocalProvider(LocalBottomBarHeight provides bottomBarHeightDp) {
                                    CurrentTab()
                                }
                            }
                        },
                        bottomBar = {
                            // TODO clean up UI
                            val localDensity = LocalDensity.current

                            Column(
                                Modifier
                                    .background(color = Color.Transparent)
                                    .zIndex(1f)
                                    .onGloballyPositioned { coordinates ->
                                        // Get height in pixels
                                        val heightPx = coordinates.size.height
                                        // Convert to dp using LocalDensity
                                        with(localDensity) {
                                            bottomBarHeightDp = heightPx.toDp()
                                        }
                                    }) {
                                val currentlyPlayingData =
                                    currentlyPlayingPresenter.present().firstOrNull()
                                currentlyPlayingData?.Render(currentlyPlayingData)
                                NavBarCompose(
                                    listOf(
                                        HomeTab,
                                        LibraryTab,
                                        SettingsTab
                                    )
                                )
                            }
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        // TODO this is called on rotations too
        audioPlayer.release()
        super.onDestroy()
    }
}

val LocalBottomBarHeight = compositionLocalOf { 0.dp }

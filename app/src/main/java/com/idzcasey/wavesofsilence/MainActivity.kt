package com.idzcasey.wavesofsilence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.idzcasey.wavesofsilence.composables.navigation.NavBarCompose
import com.idzcasey.wavesofsilence.presenters.CurrentlyPlayingPresenter
import com.idzcasey.wavesofsilence.tabs.HomeTab
import com.idzcasey.wavesofsilence.tabs.LibraryTab
import com.idzcasey.wavesofsilence.tabs.SettingsTab
import com.idzcasey.wavesofsilence.ui.theme.AudioTestingTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var currentlyPlayingPresenter: CurrentlyPlayingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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
                                        start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                                        end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
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
}

val LocalBottomBarHeight = compositionLocalOf { 0.dp }

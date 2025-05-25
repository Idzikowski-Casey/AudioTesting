package com.application.audiotesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.application.audio.AudioPlayer
import com.application.audiotesting.composables.navigation.NavBarCompose
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        audioPlayer.initializeSources()

        setContent {
            TabNavigator(HomeTab) {
                AudioTestingTheme {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        content = { innerPadding ->
                            Column(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize()
                            ) {
                                CurrentTab()
                                // TODO add a PlayerRibbon
                            }
                        },
                        bottomBar = {
                            NavBarCompose(
                                listOf(
                                    HomeTab,
                                    LibraryTab,
                                    SettingsTab
                                )
                            )
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        audioPlayer.release()
        super.onDestroy()
    }
}
package com.application.audiotesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.application.audio.AudioPlayer
import com.application.audio.PlayerState
import com.application.audio.SoundSource
import com.application.audio.SoundSourceName
import com.application.audiotesting.composables.AudioSlider
import com.application.audiotesting.composables.AudioSliderData
import com.application.audiotesting.ui.theme.AudioTestingTheme

class MainActivity : ComponentActivity() {
    private lateinit var audioPlayer: AudioPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val audioPlayer: AudioPlayer by viewModels()

        audioPlayer.initializeSources()

        setContent {
            AudioTestingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Audio Testing with Oboe",
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Row {
                            val playerState = audioPlayer.playerState.collectAsState()

                            val icon = if (playerState.value == PlayerState.Started) {
                                Icons.Filled.Pause
                            } else {
                                Icons.Filled.PlayArrow
                            }

                            IconButton(
                                onClick = {
                                    if (playerState.value == PlayerState.Started) {
                                        audioPlayer.setPlaybackEnabled(false)
                                    } else {
                                        audioPlayer.setPlaybackEnabled(true)
                                    }
                                }
                            ) {
                                Icon(
                                    icon, modifier = Modifier.size(48.dp),
                                    contentDescription = "Play"
                                )
                            }
                        }
                        Column(
                            Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            val soundSources by audioPlayer.soundSources.collectAsState()

                            soundSources.forEach { (name, source) ->
                                AudioSlider(
                                    AudioSliderData(
                                        name = name.displayName,
                                        volume = source.volume,
                                        onValueChange = { value ->
                                            audioPlayer.mutateSoundSource(
                                                source.copy(volume = value)
                                            )
                                        }
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
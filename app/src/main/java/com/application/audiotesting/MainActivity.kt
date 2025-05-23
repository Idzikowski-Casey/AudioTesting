package com.application.audiotesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.application.audio.AudioPlayer
import com.application.audio.PlayerState
import com.application.audiotesting.composables.AudioSlider
import com.application.audiotesting.composables.AudioSliderData
import com.application.audiotesting.ui.theme.AudioTestingTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val audioPlayer: AudioPlayer by viewModels()

        audioPlayer.initializeSources(this.applicationContext)

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
                            modifier = Modifier.padding(
                                start = 32.dp,
                                end = 32.dp,
                                top = 32.dp,
                                bottom = 0.dp
                            ),
                            text = "Audio Testing with Oboe",
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center
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

                        val soundSources by audioPlayer.soundSources.collectAsState()


                        LazyColumn(
                            Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(
                                items = soundSources.values.toList(),
                                key = { it.id }
                            ) { source ->
                                AudioSlider(
                                    AudioSliderData(
                                        name = source.name,
                                        volume = source.volume,
                                        onValueChange = { value ->
                                            audioPlayer.setSoundSourceVolume(
                                                source.id, value
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
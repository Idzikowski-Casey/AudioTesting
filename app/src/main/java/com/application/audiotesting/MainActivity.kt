package com.application.audiotesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.application.audio.AudioPlayer
import com.application.audio.PlayerState
import com.application.audiotesting.ui.theme.AudioTestingTheme

class MainActivity : ComponentActivity() {
    private lateinit var audioPlayer: AudioPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        audioPlayer = AudioPlayer()

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

                            Button(onClick = {
                                audioPlayer.setPlaybackEnabled(true)
                            }, enabled = playerState.value != PlayerState.Started) {
                                Text(text = "Start")
                            }
                            Button(
                                onClick = {
                                    audioPlayer.setPlaybackEnabled(false)
                                },
                                enabled = playerState.value != PlayerState.Stopped
                            ) {
                                Text(text = "Stop")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onStop() {
        if(::audioPlayer.isInitialized){
            audioPlayer.onStop()
        }
        super.onStop()
    }
}
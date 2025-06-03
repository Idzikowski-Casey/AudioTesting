package com.application.audiotesting.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.application.audiotesting.data.AudioSliderData
import com.application.audiotesting.data.CurrentlyPlayingData
import com.application.audiotesting.data.PlayPauseData
import com.application.audiotesting.ui.theme.AudioTestingTheme

@Composable
fun CurrentlyPlaying(data: CurrentlyPlayingData, modifier: Modifier = Modifier) {

    var isExpanded by remember { mutableStateOf(false) }

    Card(modifier = modifier.clickable { isExpanded = !isExpanded }) {
        AnimatedVisibility(isExpanded) {
            CurrentlyPlayingExpanded(data)
        }
        CurrentlyPlayingRow(data)
    }
}

@Composable
private fun CurrentlyPlayingExpanded(
    data: CurrentlyPlayingData,
    modifier: Modifier = Modifier
) {
    Column {
        data.sounds.forEach {
            AudioSlider(data = it, modifier = modifier)
        }
    }
}

@Composable
private fun CurrentlyPlayingRow(
    data: CurrentlyPlayingData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CurrentlyPlayingTitles(
            data.sounds.map { it.name },
            modifier = Modifier.weight(0.5f)
        )
        CurrentlyPlayingIcon(
            modifier = Modifier.weight(.1f),
            icon = data.playPauseData.icon,
            onClick = data.playPauseData.onClick
        )
    }
}

@Composable
fun CurrentlyPlayingTitles(
    items: List<String>,
    modifier: Modifier = Modifier,
) {
    val fullText = items.joinToString("   •   ")

    Row(
        modifier = modifier.basicMarquee(iterations = Int.MAX_VALUE, velocity = 40.dp)
    ) {
        Text(
            text = fullText,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            softWrap = false
        )
    }
}


@Composable
private fun CurrentlyPlayingIcon(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) { Icon(icon, contentDescription = "Play") }
}


@Preview
@Composable
private fun CurrentlyPlayingCollapsedPreview() {
    val data = CurrentlyPlayingData(
        sounds = listOf(
            AudioSliderData(
                name = "Camp Fire",
                volume = 0.5f,
                onValueChange = {}
            ),
            AudioSliderData(
                name = "Rain on Leaves",
                volume = 0.5f,
                onValueChange = {}
            ),
            AudioSliderData(
                name = "Birds in Rainforest",
                volume = 0.5f,
                onValueChange = {}
            ),
            AudioSliderData(
                name = "Frogs on Summer Night",
                volume = 0.5f,
                onValueChange = {}
            )
        ),
        playPauseData = PlayPauseData(
            icon = Icons.Default.PlayArrow,
            onClick = {}
        )
    )

    AudioTestingTheme {
        CurrentlyPlaying(data)
    }
}
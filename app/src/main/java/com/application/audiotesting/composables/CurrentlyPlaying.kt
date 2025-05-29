package com.application.audiotesting.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CurrentlyPlaying() {

    val sounds = listOf(
        "sound1",
        "sound2",
        "sound3",
        "sound4",
        "sound5"
    )

    Card {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CurrentlyPlayingTitles(sounds, modifier = Modifier.weight(0.5f))
            CurrentlyPlayingIcon(
                modifier = Modifier.weight(.1f),
                icon = Icons.Filled.PlayArrow,
                onClick = {})
        }
    }
}

@Composable
fun CurrentlyPlayingTitles(
    items: List<String>,
    modifier: Modifier = Modifier,
) {
    val fullText = items.joinToString("   •   ")

    Row(
        modifier = modifier.basicMarquee(velocity = 40.dp)
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
fun CurrentlyPlayingPreview() {
    CurrentlyPlaying()
}
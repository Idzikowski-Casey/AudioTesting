package com.idzcasey.wavesofsilence.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
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
import com.idzcasey.wavesofsilence.data.MixesRowData
import com.idzcasey.wavesofsilence.data.PlayPauseData

@Composable
fun MixesRow(data: MixesRowData, modifier: Modifier = Modifier) {

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(8.dp).weight(0.5f),
            text = data.name,
            style = MaterialTheme.typography.titleMedium
        )
        MixesRowPlayingIcon(
            modifier = Modifier.weight(.1f),
            icon = data.playPauseData.icon,
            onClick = data.playPauseData.onClick
        )
    }
}


@Composable
private fun MixesRowPlayingIcon(
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
fun PreviewMixesRow() {
    var icon by remember { mutableStateOf(Icons.Default.PlayArrow) }

    val data = MixesRowData(
        playPauseData = PlayPauseData(
            icon = icon,
            onClick = {
                if(icon == Icons.Default.PlayArrow) {
                    icon = Icons.Default.Pause
                } else {
                    icon = Icons.Default.PlayArrow
                }
            }
        ),
        name = "Rain Forest Vibes"
    )

    MixesRow(data)
}
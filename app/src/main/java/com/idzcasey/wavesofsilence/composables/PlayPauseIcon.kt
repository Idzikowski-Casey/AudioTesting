package com.idzcasey.wavesofsilence.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.idzcasey.wavesofsilence.data.PlayPauseData

@Composable
fun PlayPauseIcon(data: PlayPauseData, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        IconButton(
            onClick = data.onClick
        ) {
            Icon(
                data.icon, modifier = Modifier.size(48.dp),
                contentDescription = "Play"
            )
        }
    }
}
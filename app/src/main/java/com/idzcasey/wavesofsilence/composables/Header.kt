package com.idzcasey.wavesofsilence.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.idzcasey.wavesofsilence.data.HeaderData

@Composable
fun HeaderComposable(data: HeaderData, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(
            start = 32.dp,
            end = 32.dp,
            top = 32.dp,
            bottom = 0.dp
        ),
        text = data.text,
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center
    )
}
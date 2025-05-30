package com.application.audiotesting.composables.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.application.audiotesting.data.SoundSliderData

@Composable
fun SoundSlider(data: SoundSliderData, modifier: Modifier = Modifier) {
    Row(
        modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(data.name, style = MaterialTheme.typography.titleMedium)
        Slider(
            value = data.volume,
            onValueChange = {
                data.onValueChange(it)
            },
            valueRange = 0f..1f,
            modifier = modifier.semantics {
                contentDescription = "adjust volume for ${data.name}"
            }
        )
    }
}

@Preview
@Composable
fun SoundSliderPreview() {
    val data = SoundSliderData(
        name = "Test",
        volume = 0.5f,
        onValueChange = {}
    )

    SoundSlider(data)
}
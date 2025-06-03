package com.application.audiotesting.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.application.audiotesting.data.AudioSliderData
import com.application.audiotesting.ui.theme.AudioTestingTheme

/**
 * A View that displays a title and a slider to adjust particular noise source
 * volume
 */
@Composable
fun AudioSlider(data: AudioSliderData, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }

    Column {
        Text(
            modifier = Modifier.padding(8.dp),
            text = data.name,
            style = MaterialTheme.typography.titleMedium
        )
        Slider(
            value = data.volume,
            onValueChange = {
                data.onValueChange(it)
            },
            valueRange = 0f..1f,
            modifier = modifier.semantics {
                contentDescription = "adjust volume for ${data.name}"
            },
            interactionSource = interactionSource
        )
    }
}

@Preview
@Composable
fun AudioSliderPreview() {
    val data = AudioSliderData(
        name = "Test",
        volume = 0.5f,
        onValueChange = {}
    )
    AudioTestingTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        AudioSlider(data)
    }
}
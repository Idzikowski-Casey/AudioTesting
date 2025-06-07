package com.idzcasey.wavesofsilence.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.idzcasey.wavesofsilence.composables.slider.SliderThumb
import com.idzcasey.wavesofsilence.data.AudioSliderData
import com.idzcasey.wavesofsilence.data.AudioSliderStyle
import com.idzcasey.wavesofsilence.ui.theme.AudioTestingTheme

/**
 * A View that displays a title and a slider to adjust particular noise source
 * volume
 */
@Composable
fun AudioSlider(data: AudioSliderData, modifier: Modifier = Modifier) {
    when (data.style) {
        AudioSliderStyle.COMPACT_ROW -> AudioSliderCompact(data, modifier)
        AudioSliderStyle.EXPANDED_GRID -> AudioSliderExpanded(data, modifier)
    }
}

@Composable
fun AudioSliderExpanded(data: AudioSliderData, modifier: Modifier = Modifier) {
    Column(
        modifier.widthIn(max = 200.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        data.icon?.let {
            Box(
                modifier = Modifier
                    .size(90.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = it,
                    contentDescription = data.name,
                    modifier = Modifier
                        .fillMaxSize(),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Text(
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp),
            text = data.name,
            style = MaterialTheme.typography.titleMedium
        )
        SliderBar(data)
    }
}

@Composable
private fun AudioSliderCompact(data: AudioSliderData, modifier: Modifier = Modifier) {
    Row(
        modifier
            .height(50.dp)
            .fillMaxWidth()
    ) {
        data.icon?.let {
            Box(
                modifier = Modifier
                    .size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = it,
                    contentDescription = data.name,
                    modifier = Modifier
                        .fillMaxSize(),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Column(
            Modifier
                .weight(1f)
                .padding(start = 8.dp, top = 8.dp)
        ) {
            Text(
                modifier = Modifier,
                text = data.name,
                style = MaterialTheme.typography.titleSmall
            )
            SliderBar(data)
        }
        Box(
            modifier = Modifier
                .size(70.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                modifier = Modifier,
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    modifier = Modifier.size(23.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SliderBar(data: AudioSliderData, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }

    Slider(
        value = data.volume,
        onValueChange = {
            data.onValueChange(it)
        },
        valueRange = 0f..1f,
        modifier = modifier.semantics {
            contentDescription = "adjust volume for ${data.name}"
        },
        thumb = {
            SliderThumb(
                interactionSource = interactionSource
            )
        },
        track = { sliderState ->
            SliderDefaults.Track(
                colors = SliderDefaults.colors(),
                sliderState = sliderState,
                modifier = Modifier.height(4.dp)
            )
        },
        interactionSource = interactionSource
    )
}


@Preview
@Composable
fun CompactAudioSliderPreview() {
    val data = AudioSliderData(
        name = "Rainforest",
        volume = 0f,
        onValueChange = {},
        icon = Icons.AutoMirrored.Filled.VolumeUp
    )
    AudioTestingTheme(
        darkTheme = false
    ) {
        AudioSlider(data)
    }
}

@Preview
@Composable
fun ExpandedAudioSliderPreview() {
    val data = AudioSliderData(
        name = "Rainforest",
        volume = 0f,
        onValueChange = {},
        icon = Icons.Default.Adjust,
        style = AudioSliderStyle.EXPANDED_GRID
    )
    AudioTestingTheme(
        darkTheme = false
    ) {
        AudioSlider(data)
    }
}
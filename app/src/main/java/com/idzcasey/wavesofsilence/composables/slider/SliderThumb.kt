package com.idzcasey.wavesofsilence.composables.slider

import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SliderDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

private val ThumbWidth = 15.0.dp
private val ThumbHeight = 15.0.dp
private val ThumbSize = DpSize(ThumbWidth, ThumbHeight)

@Composable
fun SliderThumb(
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
    thumbSize: DpSize = ThumbSize
) {

    Spacer(
        modifier
            .size(thumbSize)
            .hoverable(interactionSource = interactionSource)
            .background(colors().thumbColor, CircleShape)
    )
}

@Preview
@Composable
private fun SliderThumbPreview() {
    val interactionSource = remember { MutableInteractionSource() }

    SliderThumb(interactionSource)
}
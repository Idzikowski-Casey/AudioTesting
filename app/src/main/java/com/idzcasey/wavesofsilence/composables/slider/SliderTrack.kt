package com.idzcasey.wavesofsilence.composables.slider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderTrack(sliderState: SliderState, modifier: Modifier = Modifier) {

    // Calculate fraction of the slider that is active
    val fraction by remember {
        derivedStateOf {
            (sliderState.value - sliderState.valueRange.start) / (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
        }
    }

    Box(Modifier.fillMaxWidth()) {
        Box(
            Modifier
                .fillMaxWidth(fraction)
                .align(Alignment.CenterStart)
                .height(6.dp)
                .padding(end = 16.dp)
                .background(Color.Yellow, CircleShape)
        )
        Box(
            Modifier
                .fillMaxWidth(1f - fraction)
                .align(Alignment.CenterEnd)
                .height(1.dp)
                .padding(start = 16.dp)
                .background(Color.White, CircleShape)
        )
    }
}
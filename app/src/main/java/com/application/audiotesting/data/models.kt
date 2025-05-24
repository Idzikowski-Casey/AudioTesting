package com.application.audiotesting.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.application.audiotesting.composables.home.AudioSlider
import com.application.audiotesting.composables.home.HeaderComposable
import com.application.audiotesting.composables.home.PlayPauseIcon
import com.application.audiotesting.composables.library.LibraryCategory
import com.application.audiotesting.composables.library.SoundSlider

interface ViewDataModel {
    fun stableKey(): String

    @Composable
    fun Render(data: ViewDataModel, modifier: Modifier = Modifier)
}

@Immutable
data class AudioSliderData(
    val name: String,
    val volume: Float,
    val onValueChange: (Float) -> Unit
) : ViewDataModel {
    override fun stableKey(): String = "AudioSlider_$name"

    @Composable
    override fun Render(data: ViewDataModel, modifier: Modifier) {
        AudioSlider(data as AudioSliderData, modifier = modifier)
    }
}

@Immutable
data class HeaderData(
    val text: String
) : ViewDataModel {
    override fun stableKey(): String = "Header_$text"

    @Composable
    override fun Render(data: ViewDataModel, modifier: Modifier) {
        HeaderComposable(data as HeaderData, modifier = modifier)
    }
}

@Immutable
data class PlayPauseData(
    val icon: ImageVector,
    val onClick: () -> Unit
) : ViewDataModel {

    override fun stableKey(): String = "PlayPause"

    @Composable
    override fun Render(data: ViewDataModel, modifier: Modifier) {
        PlayPauseIcon(data as PlayPauseData, modifier)
    }
}

@Immutable
data class LibraryCategoryData(
    val name: String,
    val onClick: () -> Unit,
    val icon: ImageVector
) : ViewDataModel {
    override fun stableKey(): String = "LibraryCategory_$name"

    @Composable
    override fun Render(data: ViewDataModel, modifier: Modifier) {
        LibraryCategory(data as LibraryCategoryData, modifier)
    }
}

@Immutable
data class SoundSliderData(
    val name: String,
    val volume: Float,
    val onValueChange: (Float) -> Unit
) : ViewDataModel {
    override fun stableKey(): String = "Slider_$name"

    @Composable
    override fun Render(data: ViewDataModel, modifier: Modifier) {
        SoundSlider(data as SoundSliderData, modifier = modifier)
    }
}
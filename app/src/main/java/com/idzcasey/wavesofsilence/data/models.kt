package com.idzcasey.wavesofsilence.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.idzcasey.wavesofsilence.composables.AudioSlider
import com.idzcasey.wavesofsilence.composables.CurrentlyPlaying
import com.idzcasey.wavesofsilence.composables.HeaderComposable
import com.idzcasey.wavesofsilence.composables.PlayPauseIcon
import com.idzcasey.wavesofsilence.composables.LibraryCategory
import com.idzcasey.wavesofsilence.composables.MixesRow
import com.idzcasey.wavesofsilence.presenters.MixesPresenter

interface ViewDataModel {
    fun stableKey(): String

    @Composable
    fun Render(data: ViewDataModel, modifier: Modifier = Modifier)
}

enum class AudioSliderStyle {
    COMPACT_ROW,
    EXPANDED_GRID
}

@Immutable
data class AudioSliderData(
    val name: String,
    val volume: Float,
    val onValueChange: (Float) -> Unit,
    val style: AudioSliderStyle = AudioSliderStyle.COMPACT_ROW,
    val icon: ImageVector? = null
) : ViewDataModel {
    override fun stableKey(): String = "AudioSlider_$name"

    @Composable
    override fun Render(data: ViewDataModel, modifier: Modifier) {
        AudioSlider(data as AudioSliderData, modifier = modifier)
    }
}

@Immutable
data class HeaderData(
    val text: String = String()
) : ViewDataModel {
    override fun stableKey(): String = "Header_$text"

    @Composable
    override fun Render(data: ViewDataModel, modifier: Modifier) {
        HeaderComposable(data as HeaderData, modifier = modifier)
    }
}

@Immutable
data class PlayPauseData(
    val icon: ImageVector = Icons.Default.PlayArrow,
    val onClick: () -> Unit = {}
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
data class CurrentlyPlayingData(
    val title: String? = null,
    val sounds: List<AudioSliderData> = emptyList(),
    val playPauseData: PlayPauseData = PlayPauseData()
) : ViewDataModel {
    override fun stableKey(): String = "CurrentlyPlayingBar"

    @Composable
    override fun Render(data: ViewDataModel, modifier: Modifier) {
        CurrentlyPlaying(data as CurrentlyPlayingData, modifier)
    }
}

@Immutable
data class MixesRowData(
    val playPauseData: PlayPauseData,
    val name: String = String()
) : ViewDataModel {
    override fun stableKey(): String = "MixesRow $name"

    @Composable
    override fun Render(data: ViewDataModel, modifier: Modifier) {
        MixesRow(data as MixesRowData, modifier)
    }
}
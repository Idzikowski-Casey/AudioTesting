package com.application.audiotesting.presenters

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.application.audio.AudioPlayer
import com.application.audiotesting.data.LibraryCategoryData
import com.application.audiotesting.data.ViewDataModel
import kotlinx.coroutines.flow.MutableStateFlow

class LibraryPresenter(private val audioPlayer: AudioPlayer) : Presenter {

    private val selectedPage = MutableStateFlow<LibraryPage>(LibraryPage.NONE)

    @Composable
    override fun present(): List<ViewDataModel> {
        val selectedPage by selectedPage.collectAsState()

        return when (selectedPage) {
            LibraryPage.NONE -> getLibraryCategories()
            LibraryPage.ALL_SOUNDS -> AllSoundsPresenter(audioPlayer).present()
            LibraryPage.MIXES -> listOf()
        }
    }

    @Composable
    fun getLibraryCategories(): List<ViewDataModel> {
        return listOf(
            LibraryCategoryData(
                name = "All Sounds",
                onClick = {
                    selectedPage.value = LibraryPage.ALL_SOUNDS
                },
                icon = Icons.Filled.LibraryMusic
            ),
            LibraryCategoryData(
                name = "Mixes",
                onClick = {
                    selectedPage.value = LibraryPage.MIXES
                },
                icon = Icons.Filled.Tune
            )
        )
    }
}

enum class LibraryPage {
    NONE,
    ALL_SOUNDS,
    MIXES
}
package com.application.audiotesting.presenters

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.application.audiotesting.data.LibraryCategoryData
import com.application.audiotesting.data.ViewDataModel
import com.application.audiotesting.screens.AllSoundsScreen
import com.application.audiotesting.screens.MixesScreen
import javax.inject.Inject

class LibraryPresenter @Inject constructor() : Presenter {

    @Composable
    override fun present(): List<ViewDataModel> {
        return getLibraryCategories()
    }

    @Composable
    fun getLibraryCategories(): List<ViewDataModel> {
        val navigator = LocalNavigator.currentOrThrow

        return listOf(
            LibraryCategoryData(
                name = "All Sounds",
                onClick = {
                    navigator.push(AllSoundsScreen())
                },
                icon = Icons.Filled.LibraryMusic
            ),
            LibraryCategoryData(
                name = "Mixes",
                onClick = {
                    navigator.push(MixesScreen())
                },
                icon = Icons.Filled.Tune
            )
        )
    }
}
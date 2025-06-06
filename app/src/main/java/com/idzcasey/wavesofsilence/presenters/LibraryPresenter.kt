package com.idzcasey.wavesofsilence.presenters

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.idzcasey.wavesofsilence.data.LibraryCategoryData
import com.idzcasey.wavesofsilence.data.ViewDataModel
import com.idzcasey.wavesofsilence.screens.AllSoundsScreen
import com.idzcasey.wavesofsilence.screens.MixesScreen
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
package com.application.audiotesting.data

data class AppState(
    val isLoading: Boolean = false,
    val items: List<ViewDataModel> = emptyList()
)

enum class Page {
    HOME,
    LIBRARY,
    MIXES,
    ALL_SOUNDS,
    SETTINGS
}
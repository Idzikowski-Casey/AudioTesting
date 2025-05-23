package com.application.audio

sealed interface PlayerState {
    object NoResultYet : PlayerState
    object Started : PlayerState
    object Stopped : PlayerState
    data class Unknown(val resultCode: Int) : PlayerState
}
package com.application.audio

sealed interface PlayerState {
    object NoResultYet : PlayerState
    object Started : PlayerState
    object Stopped : PlayerState
    data class Unknown(val resultCode: Int) : PlayerState
}

data class SoundSource(
    val name: SoundSourceName,
    val volume: Float
)

enum class SoundSourceName(val displayName: String, val id: Int) {
    SINE_WAVE("Sine Wave", 0),
    WHITE_NOISE("White Noise", 1),
    WIND("Wind", 2),
    RAIN("Rain", 3),
    CAMPFIRE("Campfire", 4)
}

val InitialState = mapOf(
    SoundSourceName.WHITE_NOISE to SoundSource(
        name = SoundSourceName.WHITE_NOISE,
        volume = 0.0f
    ),
    SoundSourceName.WIND to SoundSource(
        name = SoundSourceName.WIND,
        volume = 0.0f
    ),
    SoundSourceName.RAIN to SoundSource(
        name = SoundSourceName.RAIN,
        volume = 0.0f
    ),
    SoundSourceName.CAMPFIRE to SoundSource(
        name = SoundSourceName.CAMPFIRE,
        volume = 0.0f
    ),
    SoundSourceName.SINE_WAVE to SoundSource(
        name = SoundSourceName.SINE_WAVE,
        volume = 0.0f
    ),
)
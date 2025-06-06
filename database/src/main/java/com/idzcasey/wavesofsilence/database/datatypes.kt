package com.idzcasey.wavesofsilence.database

data class SoundSourceMix(
    val id: String,
    val name: String,
    val soundSources: List<SoundSource>
)

data class SoundSource(
    val id: String,
    val name: String,
    val type: SoundSourceName,
    val volume: Float,
    val filename: String? = null
)

enum class SoundSourceName(val displayName: String, val id: Int) {
    SINE_WAVE("Sine Wave", 0),
    WHITE_NOISE("White Noise", 1),
    WIND("Wind", 2),
    RAIN("Rain", 3),
    CAMPFIRE("Campfire", 4),
    GENERIC("Generic Buffer", 5)
}
package com.idzcasey.wavesofsilence.database

val Mixes: List<SoundSourceMix> = listOf(
    SoundSourceMix(
        id = "MIXRAINFOREST",
        name = "Rainforest vibes",
        soundSources = listOf(
            SoundSource(
                id = "8529RAINONLEAVES",
                name = "Rain on Leaves",
                type = SoundSourceName.GENERIC,
                volume = 0.35f,
                filename = "rainonleaves.wav"
            ),
            SoundSource(
                id = "8529GOLDENLEMUR",
                name = "Golden Lemur",
                type = SoundSourceName.GENERIC,
                volume = 0.5f,
                filename = "glemur.wav"
            ),
            SoundSource(
                id = "8529RAINFOREST",
                name = "Rainforest",
                type = SoundSourceName.GENERIC,
                volume = 0.5f,
                filename = "rainforest.wav"
            ),
        )
    ),
    SoundSourceMix(
        id = "MIXWHITENOISE",
        name = "White Noise Mix",
        soundSources = listOf(
            SoundSource(
                id = "8529WHITE",
                name = SoundSourceName.WHITE_NOISE.displayName,
                type = SoundSourceName.WHITE_NOISE,
                volume = 0.25f,
                filename = null
            ),
            SoundSource(
                id = "8529RAIN",
                name = SoundSourceName.RAIN.displayName,
                type = SoundSourceName.RAIN,
                volume = 0.5f,
                filename = null
            ),
            SoundSource(
                id = "8529CAMPFIRE",
                name = SoundSourceName.CAMPFIRE.displayName,
                type = SoundSourceName.CAMPFIRE,
                volume = 0.75f,
                filename = null
            )
        )
    )
)
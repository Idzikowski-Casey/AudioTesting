//
// Created by Casey Idzikowski on 5/12/25.
//


#pragma once

#include <vector>
#include <cmath>
#include <cstdlib>
#include <cstdint>
#include <algorithm>
#include "SoundSource.h"
#include "SoundDefinitions.h"

// region SoundSources

class SineWaveSoundSource : public SoundSource {
public:

    using SoundSource::SoundSource;

    void addSoundToBuffer(float* audioData, int numSamples) override {
        static float frequency = 440.0f;     // A4 tone
        const float phaseIncrement = (2.0f * kPi * frequency) / kSampleRate;
        static float phase = 0.0f;

        for (int i = 0; i < numSamples; ++i) {
            float sample = volume * sinf(phase);
            audioData[i] += sample;
            phase += phaseIncrement;
            if (phase > 2.0f * kPi) phase -= 2.0f * kPi;  // keep phase bounded
        }
    }

    SoundDefinitions::SoundSourceType getSoundSourceType() override {
        return SineWaveSourceType;
    }

private:
    static constexpr SoundDefinitions::SoundSourceType SineWaveSourceType = SoundDefinitions::SoundSourceType::SINE_WAVE;
    static constexpr float kPi = 3.14159265f;
    static constexpr float kSampleRate = 48000.0f;
};

class WhiteNoiseSoundSource : public SoundSource {
public:
    using SoundSource::SoundSource;

    void addSoundToBuffer(float* audioData, int numSamples) override {
        for (int i = 0; i < numSamples; i++) {
            audioData[i] += makeWhiteNoise() * volume;
        }
    }

    SoundDefinitions::SoundSourceType getSoundSourceType() override {
        return WhiteNoiseSourceType;
    }

private:
    /**
     * Helper function to generate a white noise sample.
     * @return A random float between -0.6 and 0.6
     */
    float makeWhiteNoise() {
        return static_cast<float>(drand48() * 2.0 - 1.0);
    }

    static constexpr SoundDefinitions::SoundSourceType WhiteNoiseSourceType = SoundDefinitions::SoundSourceType::WHITE_NOISE;
};

// endregion
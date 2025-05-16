//
// Created by Casey Idzikowski on 5/16/25.
//

#include "SoundSource.h"

/**
 * A class for generating Samples that are created using
 * a Sine wave.
 *
 * Requires a phase to keep track of the current position relative to
 * the buffer. Similar to what needs to be done with pre-loaded buffers.
 */
class SineWaveSoundSource : public SoundSource {
public:

    using SoundSource::SoundSource;

    void addSoundToBuffer(float *audioData, int numSamples) override {
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
//
// Created by Casey Idzikowski on 5/16/25.
//

#include "SoundSource.h"
#include "SoundDefinitions.h"
#include "SoundHelpers.cpp"

class WindSoundSource : public SoundSource {
public:
    using SoundSource::SoundSource;

    void addSoundToBuffer(float *audioData, int numSamples) override {
        for (int i = 0; i < numSamples; i++) {
            audioData[i] += makeWind() * volume;
        }
    }

    SoundDefinitions::SoundSourceType getSoundSourceType() override {
        return WindSourceType;
    }

private:

    float makeWind() {
        return lowPassFilter.process(CommonSounds::whiteNoise() * 0.4f);
    }

    LowPassFilter lowPassFilter{0.05f};

    static constexpr SoundDefinitions::SoundSourceType WindSourceType = SoundDefinitions::SoundSourceType::WIND;

};
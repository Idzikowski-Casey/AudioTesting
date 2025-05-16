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
#include "SoundHelpers.cpp"

/**
 * A CampFire Sound Source. Mimics campfire sounds using crackle bursts with a soft white noise base.
 *
 */
class CampfireSoundSource : public SoundSource {
public:
    using SoundSource::SoundSource;

    void addSoundToBuffer(float *audioData, int numSamples) override {
        for (int i = 0; i < numSamples; i++) {
            audioData[i] += makeCampFire() * volume;
        }
    }

    SoundDefinitions::SoundSourceType getSoundSourceType() override {
        return CampFireSourceType;
    }

private:

    float makeCampFire() {
        float base = CommonSounds::whiteNoise() * 0.03f;

        if(--crackleCountdown <= 0) {
            crackleCountdown = rand() % 1000 + 400;
            crackleBurst = (CommonSounds::whiteNoise() + 1.0f) * 0.5f;
        }
        float crackle = crackleBurst * CommonSounds::whiteNoise();
        crackleBurst *= 0.97f; // crackle burst decay

        return CommonSounds::clampf(base + crackle, -1.0f, 1.0f);
    }

    static constexpr SoundDefinitions::SoundSourceType CampFireSourceType = SoundDefinitions::SoundSourceType::CAMPFIRE;

    int crackleCountdown = 1000;
    float crackleBurst = 0.0f;
};
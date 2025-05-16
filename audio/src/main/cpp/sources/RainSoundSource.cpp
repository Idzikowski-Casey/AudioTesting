//
// Created by Casey Idzikowski on 5/16/25.
//

#include "SoundSource.h"
#include "SoundDefinitions.h"
#include "SoundHelpers.cpp"

/**
 * Rain sound source generator
 */
class RainSoundSource : public SoundSource {
public:
    using SoundSource::SoundSource;

    void addSoundToBuffer(float *audioData, int numSamples) override {
        for (int i = 0; i < numSamples; i++) {
            audioData[i] += makeRain() * volume;
        }
    }

    SoundDefinitions::SoundSourceType getSoundSourceType() override {
        return RainSourceType;
    }

private:

    /**
     * Rain noise is random droplets on a backgroun of soft static (white noise).
     * @return Rain noise that is clamped
     */
    static float makeRain() {
        float hiss = CommonSounds::whiteNoise() * 0.2f;
        float droplet = (rand() % 1600 == 0) ? 1.0f : 0.0f;
        return CommonSounds::clampf(hiss + droplet, -1.0f, 1.0f);
    }

    static constexpr SoundDefinitions::SoundSourceType RainSourceType = SoundDefinitions::SoundSourceType::RAIN;
};

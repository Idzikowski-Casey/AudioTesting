//
// Created by Casey Idzikowski on 5/18/25.
//

#include <utility>
#include <vector>
#include "SoundDefinitions.h"
#include "CampfireSoundSource.cpp"
#include "SineWaveSoundSource.cpp"
#include "WhiteNoiseSoundSource.cpp"
#include "WindSoundSource.cpp"
#include "RainSoundSource.cpp"
#include "GenericBufferSoundSource.cpp"

class GenericBufferSoundSourceFactory {
public:
    GenericBufferSoundSourceFactory() = default;

    static std::shared_ptr<GenericBufferSoundSource> createSoundSource(
            std::shared_ptr<const std::vector<float>> buffer,
            float volume,
            std::string displayName
    ) {
        return std::make_shared<GenericBufferSoundSource>(buffer, volume, displayName);
    }

    static std::shared_ptr<SoundSource> createSoundSource(
            int type,
            float volume,
            std::string displayName
    ) {
        auto soundTypeOptional = SoundDefinitions::fromInt(type);

        if (!soundTypeOptional.has_value()) {
            std::cerr << "Invalid SoundSourceType: " << type << std::endl;
            return nullptr;
        }

        switch (*soundTypeOptional) {
            case SoundDefinitions::SoundSourceType::WHITE_NOISE:
                return std::make_shared<WhiteNoiseSoundSource>(volume, displayName);
            case SoundDefinitions::SoundSourceType::SINE_WAVE:
                return std::make_shared<SineWaveSoundSource>(volume, displayName);
            case SoundDefinitions::SoundSourceType::WIND:
                return std::make_shared<WindSoundSource>(volume, displayName);
            case SoundDefinitions::SoundSourceType::RAIN:
                return std::make_shared<RainSoundSource>(volume, displayName);
            case SoundDefinitions::SoundSourceType::CAMPFIRE:
                return std::make_shared<CampfireSoundSource>(volume, displayName);
            default:
                return nullptr;
        }
    }
};
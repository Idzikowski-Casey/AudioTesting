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

/**
 * A factory class for creating sound sources. Can create either a generic buffer sound source from
 * a predefined buffer or a runtime sound source given an integer value that maps to a SoundSource
 * enum.
 */
class GenericSoundSourceFactory {
public:
    GenericSoundSourceFactory() = default;

    /**
     * @brief Create a generic buffer sound source from a predefined buffer.
     * @param buffer The buffer to be used for the sound source.
     * @param volume The initial volume of the sound source.
     * @param displayName the display name of the sound source.
     * @return std::shared_ptr<GenericBufferSoundSource>
     */
    static std::shared_ptr<SoundSource> createSoundSource(
            std::shared_ptr<const std::vector<float>> buffer,
            float volume,
            std::string displayName
    ) {
        return std::make_shared<GenericBufferSoundSource>(buffer, volume, displayName);
    }

    /**
     * @brief Create a runtime sound source from an integer value.
     * @param type the integer value that maps to a SoundSource enum.
     * @param volume the initial volume of the soundsource
     * @param displayName the display name of the sound source
     * @return std::shared_ptr<SoundSource>
     */
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
//
// Created by Casey Idzikowski on 5/14/25.
//

#ifndef AUDIOTESTING_SOUNDDEFINITIONS_H
#define AUDIOTESTING_SOUNDDEFINITIONS_H

#include <optional>

namespace SoundDefinitions {

    /**
     * An enum class for
     * each type of soundSource
     * available for Playback
     */
    enum class SoundSourceType {
        SINE_WAVE = 0,
        WHITE_NOISE = 1,
        WIND = 2,
        RAIN = 3,
        CAMPFIRE = 4,
        GENERIC_BUFFER = 5
    };

    // Convert from int to enum safely
    inline std::optional<SoundSourceType> fromInt(int value) {
        switch (static_cast<SoundSourceType>(value)) {
            case SoundSourceType::WHITE_NOISE:
            case SoundSourceType::SINE_WAVE:
            case SoundSourceType::WIND:
            case SoundSourceType::RAIN:
            case SoundSourceType::CAMPFIRE:
            case SoundSourceType::GENERIC_BUFFER:
                return static_cast<SoundSourceType>(value);
            default:
                return std::nullopt;
        }
    }
}

#endif //AUDIOTESTING_SOUNDDEFINITIONS_H

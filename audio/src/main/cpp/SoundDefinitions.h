//
// Created by Casey Idzikowski on 5/14/25.
//

#ifndef AUDIOTESTING_SOUNDDEFINITIONS_H
#define AUDIOTESTING_SOUNDDEFINITIONS_H

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
        CAMPFIRE = 4
    };
}

#endif //AUDIOTESTING_SOUNDDEFINITIONS_H

#include <utility>

//
// Created by Casey Idzikowski on 5/13/25.
//

#ifndef AUDIOTESTING_SOUNDSOURCE_H
#define AUDIOTESTING_SOUNDSOURCE_H
#include "SoundDefinitions.h"


class SoundSource {
public:
    float volume;
    std::string displayName;

    /**
     * Method to write samples to a buffer.
     * @param audioData the buffer to add samples to
     * @param numSamples the number of samples required to add to the buffer
     */
    virtual void addSoundToBuffer(float* audioData, int numSamples) = 0;

    /**
     * Every SoundSource has an associated SoundSourceType
     * that is used as identification of the sound source
     * @return
     */
    virtual SoundDefinitions::SoundSourceType getSoundSourceType() = 0;

    SoundSource(float volume, std::string displayName) {
        this->volume = volume;
        this->displayName = std::move(displayName);
    };

    virtual ~SoundSource() = default;
};




#endif //AUDIOTESTING_SOUNDSOURCE_H

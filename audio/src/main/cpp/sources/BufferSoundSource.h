//
// Created by Casey Idzikowski on 5/18/25.
//

#ifndef AUDIOTESTING_BUFFERSOUNDSOURCE_H
#define AUDIOTESTING_BUFFERSOUNDSOURCE_H

#include "SoundSource.h"
#include <string>
#include <utility>
#include <vector>

/**
 * @brief A base class for SoundSources that will come with a pre-defined buffer of samples to
 * be panned through and added to the Audio Stream. Buffer is passed in the constructor.
 */
class BufferSoundSource : public SoundSource {
public:

    /**
     * @brief Constructor for BufferSoundSource - will call constructor of SoundSource.
     * @param buffer The pre-defined buffer of samples to be added to the Audio Stream.
     * @param volume The volume of the SoundSource.
     * @param displayName
     */
    BufferSoundSource(
            std::shared_ptr<const std::vector<float>> buffer,
            float volume,
            std::string displayName)
            : SoundSource(volume, displayName), buffer(std::move(buffer)) {}

protected:
    std::shared_ptr<const std::vector<float>> buffer;
};

#endif //AUDIOTESTING_BUFFERSOUNDSOURCE_H

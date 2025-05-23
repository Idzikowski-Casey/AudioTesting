//
// Created by Casey Idzikowski on 5/18/25.
//


#include "BufferSoundSource.h"
#include <vector>

/**
 * @brief An implementation class for the [BufferSoundSource]
 */
class GenericBufferSoundSource: public BufferSoundSource {
    using BufferSoundSource::BufferSoundSource;

    void addSoundToBuffer(float *audioData, int numSamples) override {
        for (int i = 0; i < numSamples; i++) {
            // if end of buffer is reached reset Index
            if (readIndex >= buffer->size()) {
                readIndex = 0;
            }

            // get buffer value and increment readIndex
            float sample = (*buffer)[readIndex++] * volume;

            audioData[i] += sample;
        }
    }

    SoundDefinitions::SoundSourceType getSoundSourceType() override {
        return SoundDefinitions::SoundSourceType::GENERIC_BUFFER;
    }

private:
    size_t readIndex = 0;
};
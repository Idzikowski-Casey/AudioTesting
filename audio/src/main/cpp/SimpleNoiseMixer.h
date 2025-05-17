//
// Created by Casey Idzikowski on 5/11/25.
//

#ifndef AUDIOTESTING_SIMPLENOISEMIXER_H
#define AUDIOTESTING_SIMPLENOISEMIXER_H

#include "oboe/Oboe.h"
#include "sources/SoundSource.h"
#include "map"

/**
 * A class interface for simple audio mixing using Oboe
 */
class SimpleNoiseMixer {
public:

    // Oboe functions

    /**
     * Open the Oboe stream. Creates an AudioStream
     * object.
     *
     * @return result code of type [oboe::Result]
     */
    oboe::Result open();

    /**
     * Start pushing sound data to the Oboe stream
     * @return result code of type [oboe::Result]
     */
    oboe::Result start();

    /**
     * Stop pushing sound data to the Oboe stream
     * @return result code of type [oboe::Result]
     */
    oboe::Result stop();

    /**
     * Close the Oboe stream
     * @return result code of type [oboe::Result]
     */
    oboe::Result close();

    // region Sound Sources

    void addSoundSource(std::shared_ptr<SoundSource> soundSource);

    void updateSoundSourceVolume(SoundDefinitions::SoundSourceType type, float volume);

    void removeSoundSource(SoundDefinitions::SoundSourceType type);

    // endregion

private:
    class OboeDataCallback : public oboe::AudioStreamCallback {

    public:
        OboeDataCallback(SimpleNoiseMixer* parent) : mParent(parent) {}

        oboe::DataCallbackResult onAudioReady(
                oboe::AudioStream *oboeStream,
                void *audioData,
                int32_t numFrames) override;

    private:
        SimpleNoiseMixer* mParent;
    };

    class OboeErrorCallback : public oboe::AudioStreamErrorCallback {
    public:
        OboeErrorCallback(SimpleNoiseMixer* parent) : mParent(parent) {}

        virtual ~OboeErrorCallback() {

        }

        void onErrorAfterClose(oboe::AudioStream *oboeStream, oboe::Result error) override;

    private:
        SimpleNoiseMixer* mParent;
    };

    std::shared_ptr<oboe::AudioStream> mStream;
    std::shared_ptr<OboeDataCallback> mDataCallback;
    std::shared_ptr<OboeErrorCallback> mErrorCallback;

    static constexpr int kChannelCount = 2;

    // Sound Sources
    std::unordered_map<SoundDefinitions::SoundSourceType, std::shared_ptr<SoundSource>> soundSources;

};

#endif //AUDIOTESTING_SIMPLENOISEMIXER_H

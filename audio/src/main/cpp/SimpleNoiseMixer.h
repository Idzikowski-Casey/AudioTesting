//
// Created by Casey Idzikowski on 5/11/25.
//

#ifndef AUDIOTESTING_SIMPLENOISEMIXER_H
#define AUDIOTESTING_SIMPLENOISEMIXER_H

#include "oboe/Oboe.h"

/**
 * A class interface for simple audio mixing using Oboe
 */
class SimpleNoiseMixer {
public:
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

private:
    class OboeDataCallback : public oboe::AudioStreamCallback {
    public:
        oboe::DataCallbackResult onAudioReady(
                oboe::AudioStream *oboeStream,
                void *audioData,
                int32_t numFrames) override;
    };

    class OboeErrorCallback : public oboe::AudioStreamErrorCallback {
    public:
        OboeErrorCallback(SimpleNoiseMixer *parent) : mParent(parent) {}

        virtual ~OboeErrorCallback() {

        }

        void onErrorAfterClose(oboe::AudioStream *oboeStream, oboe::Result error) override;

    private:
        SimpleNoiseMixer *mParent;
    };

    std::shared_ptr<oboe::AudioStream> mStream;
    std::shared_ptr<OboeDataCallback> mDataCallback;
    std::shared_ptr<OboeErrorCallback> mErrorCallback;

    static constexpr int kChannelCount = 2;
};

#endif //AUDIOTESTING_SIMPLENOISEMIXER_H

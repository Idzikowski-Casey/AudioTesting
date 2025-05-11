//
// Created by Casey Idzikowski on 5/11/25.
//

#include <cstdlib>

static const char *TAG = "SimpleNoiseMixer";

#include <android/log.h>

#include "SimpleNoiseMixer.h"

using namespace oboe;

// region SimpleNoiseMixer public methods

oboe::Result SimpleNoiseMixer::open() {

    mDataCallback = std::make_shared<OboeDataCallback>();
    mErrorCallback = std::make_shared<OboeErrorCallback>(this);

    AudioStreamBuilder builder;
    return builder.setSharingMode(SharingMode::Exclusive)
            ->setPerformanceMode(PerformanceMode::LowLatency)
            ->setFormat(AudioFormat::Float)
            ->setChannelCount(kChannelCount)
            ->setDataCallback(mDataCallback)
            ->setErrorCallback(mErrorCallback)
            ->openStream(mStream);
}

oboe::Result SimpleNoiseMixer::start() {
    return mStream->requestStart();
}

oboe::Result SimpleNoiseMixer::stop() {
    return mStream->requestStop();
}

oboe::Result SimpleNoiseMixer::close() {
    return mStream->close();
}

// endregion

// region private callbacks

DataCallbackResult
SimpleNoiseMixer::OboeDataCallback::onAudioReady(
        AudioStream *audioStream,
        void *audioData,
        int32_t numFrames) {

    // cast to float as we specified float in AudioStreamBuilder
    auto *output = static_cast<float *>(audioData);

    int numSamples = numFrames * kChannelCount;

    for (int i = 0; i < numSamples; i++) {
        // drand48() returns a random number between 0.0 and 1.0.
        // Center and scale it to a reasonable value.
        *output++ = static_cast<float>((drand48() - 0.5) * 0.6);
    }
    return DataCallbackResult::Continue;
}

void SimpleNoiseMixer::OboeErrorCallback::onErrorAfterClose(
        oboe::AudioStream *oboeStream,
        oboe::Result error) {
    __android_log_print(ANDROID_LOG_INFO, TAG,
                        "%s() - error = %s",
                        __func__,
                        oboe::convertToText(error)
    );
    // Try to open and start a new stream after a disconnect.
    if (mParent->open() == Result::OK) {
        mParent->start();
    }
}

// endregion
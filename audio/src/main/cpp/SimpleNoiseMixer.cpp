//
// Created by Casey Idzikowski on 5/11/25.
//

#include <cmath>
#include <cstdlib>

static const char *TAG = "SimpleNoiseMixer";

#include <android/log.h>

#include "SimpleNoiseMixer.h"
#include "sounds.cpp"

using namespace oboe;

// region SimpleNoiseMixer public methods


oboe::Result SimpleNoiseMixer::open() {

    mDataCallback = std::make_shared<OboeDataCallback>(this);
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

void SimpleNoiseMixer::addSoundSource(std::shared_ptr<SoundSource> soundSource) {
    soundSources[soundSource->getSoundSourceType()] = std::move(soundSource);
}

void SimpleNoiseMixer::updateSoundSourceVolume(SoundDefinitions::SoundSourceType id, float volume) {
    // if sound source exists in map
    auto soundSource = soundSources.find(id);
    if (soundSource != soundSources.end()) {
        soundSource->second->volume = volume;
    }
}

void SimpleNoiseMixer::removeSoundSource(SoundDefinitions::SoundSourceType id) {
    auto soundSource = soundSources.find(id);
    if (soundSource != soundSources.end()) {
        soundSources.erase(id);
    }
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

    std::fill(output, output + numSamples, 0.0f);

    if(!mParent) {
        return DataCallbackResult::Stop;
    }

    // loop through all soundSources provided and add their samples to the output buffer
    for (const auto& [type, soundPtr] : mParent->soundSources) {
        if (soundPtr) {
            soundPtr->addSoundToBuffer(output, numSamples);
        }
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
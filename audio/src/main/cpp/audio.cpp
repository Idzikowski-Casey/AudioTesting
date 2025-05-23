#include <jni.h>
#include <string>

static const char *TAG = "AudioPlayer";

#include "SimpleNoiseMixer.h"
#include "sources/GenericSoundSourceFactory.cpp"
#include "JniHelpers.cpp"
#include <android/log.h>

#ifdef __cplusplus
extern "C" {
#endif

using namespace oboe;

// The SimpleNoiseMixer for the app
static SimpleNoiseMixer mixer;

/**
 * @brief A native method that attemps to open the oboe stream and request the start of the stream.
 *
 * @param env
 * @param thiz
 * @return Oboe::Result
 */
JNIEXPORT jint JNICALL
Java_com_application_audio_AudioPlayer_startPlayer(JNIEnv *env, jobject thiz) {
    __android_log_print(ANDROID_LOG_INFO, TAG, "%s", __func__);

    Result result = mixer.open();
    if (result == Result::OK) {
        result = mixer.start();
    }

    return static_cast<jint>(result);
}

/**
 * @brief A native method that attempts to stop and close the oboe stream.
 *
 * @param env
 * @param thiz
 * @return Oboe::Result
 */
JNIEXPORT jint JNICALL
Java_com_application_audio_AudioPlayer_stopPlayer(JNIEnv *env, jobject thiz) {
    __android_log_print(ANDROID_LOG_INFO, TAG, "%s", __func__);

    Result result1 = mixer.stop();
    Result result2 = mixer.close();

    return static_cast<jint>((result1 != Result::OK) ? result1 : result2);
}

/**
 * @brief Adds a sound source to the set in soundMixer. Creates a runtime soundSource that is created
 * inside C++ code. Buffer is inline created.
 * @param env
 * @param thiz
 * @param id
 * @param type
 * @param volume
 */
JNIEXPORT jint JNICALL
Java_com_application_audio_AudioPlayer_addSoundSource(JNIEnv *env, jobject thiz, jstring id,
                                                      jint type, jfloat volume,
                                                      jstring displayName) {

    auto idString = jstringToStdString(env, id);
    auto displayNameString = jstringToStdString(env, displayName);

    auto soundSource = GenericSoundSourceFactory::createSoundSource(
            type, static_cast<float>(volume), displayNameString);

    if (soundSource) {
        auto result = mixer.addSoundSource(idString, soundSource);
        return static_cast<jint>(result);
    }

    // means nullptr
    return static_cast<jint>(Result::ErrorInvalidState);
}


/**
 * Adds an additional generic buffer sound source to the set in soundMixer. Pre-defined buffer is
 * provided and panned through at runtime.
 * @param env
 * @param thiz
 * @param id
 * @param volume
 * @param buffer
 */
JNIEXPORT jint JNICALL
Java_com_application_audio_AudioPlayer_addGenericBufferSoundSource(JNIEnv *env, jobject thiz,
                                                                   jstring id,
                                                                   jfloat volume,
                                                                   jfloatArray buffer,
                                                                   jstring displayName) {

    auto sharedBuffer = jfloatArrayToSharedVector(env, buffer);
    auto displayNameString = jstringToStdString(env, displayName);

    // if buffer is nullptr or is empty return error
    if (!sharedBuffer || sharedBuffer->empty()) {
        return static_cast<jint>(Result::ErrorInvalidState);
    }

    auto genericBufferSoundSource = GenericSoundSourceFactory::createSoundSource(
            sharedBuffer, static_cast<float>(volume),
            displayNameString);

    auto idString = jstringToStdString(env, id);

    auto result = mixer.addSoundSource(idString, genericBufferSoundSource);

    return static_cast<jint>(result);
}

/**
 * A native method that updates the volume of a sound source.
 *
 * @param env
 * @param thiz
 * @param id The id of the sound source to update.
 * @param volume The new volume of the sound source.
 */
JNIEXPORT jint JNICALL
Java_com_application_audio_AudioPlayer_updateSoundSourceVolume(JNIEnv *env, jobject thiz,
                                                               jstring id,
                                                               jfloat volume) {
    std::string idString = jstringToStdString(env, id);
    auto result = mixer.updateSoundSourceVolume(idString, volume);

    return static_cast<jint>(result);
}
#ifdef __cplusplus
}
#endif
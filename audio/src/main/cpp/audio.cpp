#include <jni.h>
#include <string>

static const char *TAG = "AudioPlayer";

#include "SimpleNoiseMixer.h"
#include "sounds.cpp"
#include <android/log.h>

#ifdef __cplusplus
extern "C" {
#endif

using namespace oboe;

static SimpleNoiseMixer mixer;

JNIEXPORT jint JNICALL
Java_com_application_audio_AudioPlayer_startPlayer(JNIEnv *env, jobject thiz) {
    __android_log_print(ANDROID_LOG_INFO, TAG, "%s", __func__);

    Result result = mixer.open();
    if (result == Result::OK) {
        result = mixer.start();
    }

    return static_cast<jint>(result);
}


JNIEXPORT jint JNICALL
Java_com_application_audio_AudioPlayer_stopPlayer(JNIEnv *env, jobject thiz) {
    __android_log_print(ANDROID_LOG_INFO, TAG, "%s", __func__);

    Result result1 = mixer.stop();
    Result result2 = mixer.close();

    return static_cast<jint>((result1 != Result::OK) ? result1 : result2);
}

#ifdef __cplusplus
}
#endif
extern "C"
JNIEXPORT jint JNICALL
Java_com_application_audio_AudioPlayer_initializeSoundSources(JNIEnv *env, jobject thiz) {
    WhiteNoiseSoundSource whiteNoiseSoundSource = WhiteNoiseSoundSource(0.5f, "White Noise");
    SineWaveSoundSource sineWaveSoundSource = SineWaveSoundSource(0.5f, "Sine Wave");

    mixer.addSoundSource(std::make_shared<WhiteNoiseSoundSource>(whiteNoiseSoundSource));
    mixer.addSoundSource(std::make_shared<SineWaveSoundSource>(sineWaveSoundSource));

    return static_cast<jint>(Result::OK);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_application_audio_AudioPlayer_updateSoundSourceVolume(JNIEnv *env, jobject thiz, jint id,
                                                               jfloat volume) {
    mixer.updateSoundSourceVolume(static_cast<SoundDefinitions::SoundSourceType>(id), volume);
}
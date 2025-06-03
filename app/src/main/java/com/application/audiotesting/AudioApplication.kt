package com.application.audiotesting

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.application.audio.AudioPlayer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Required boiler plate to create a hilt application
 */
@HiltAndroidApp
class AudioApplication: Application() {

    @Inject
    lateinit var audioPlayer: AudioPlayer

    override fun onCreate() {
        super.onCreate()
        audioPlayer.initializeSources()

        ProcessLifecycleOwner.get().lifecycle.addObserver(
            AppLifecycleObserver(audioPlayer)
        )
    }
}

class AppLifecycleObserver(
    private val audioPlayer: AudioPlayer
) : DefaultLifecycleObserver {

    override fun onDestroy(owner: LifecycleOwner) {
        audioPlayer.release()
    }
}
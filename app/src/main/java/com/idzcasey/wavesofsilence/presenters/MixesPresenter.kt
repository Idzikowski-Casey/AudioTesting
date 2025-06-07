package com.idzcasey.wavesofsilence.presenters

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.idzcasey.wavesofsilence.audio.AudioPlayer
import com.idzcasey.wavesofsilence.audio.PlayerState
import com.idzcasey.wavesofsilence.data.HeaderData
import com.idzcasey.wavesofsilence.data.MixesRowData
import com.idzcasey.wavesofsilence.data.PlayPauseData
import com.idzcasey.wavesofsilence.data.ViewDataModel
import com.idzcasey.wavesofsilence.database.SoundSourceMix
import javax.inject.Inject


class MixesPresenter @Inject constructor(private val audioPlayer: AudioPlayer) : Presenter {

    @Composable
    override fun present(): List<ViewDataModel> {

        return listOf(
            HeaderData("Mixes")
        ) + getMixesData()
    }

    @Composable
    private fun getMixesData(): List<MixesRowData> {
        val mixes = audioPlayer.mixes
        val currentlyPlayingMix by audioPlayer.currentlyPlayingMix.collectAsState()
        val playerState by audioPlayer.playerState.collectAsState()

        return mixes.map {

            val icon =
                if (currentlyPlayingMix == it.key && playerState == PlayerState.Started) {
                    Icons.Default.Pause
                } else {
                    Icons.Default.PlayArrow
                }

            MixesRowData(
                playPauseData = PlayPauseData(
                    icon = icon,
                    onClick = {
                        onClick(it.key, currentlyPlayingMix)
                    }
                ),
                name = it.value.name
            )
        }
    }

    private fun onClick(id: String, currentMixId: String?) {
        if (currentMixId == id && audioPlayer.playerState.value == PlayerState.Started) {
            audioPlayer.stopMix(id)
        } else {
            audioPlayer.startMix(id)
        }
    }
}
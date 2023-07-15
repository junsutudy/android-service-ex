package app.junsu.androidserviceex.music

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import app.junsu.androidserviceex.R

class MusicService : Service() {
    private val broadcastManager by lazy { LocalBroadcastManager.getInstance(this) }
    private lateinit var player: MediaPlayer
    private var musicPosition: Int = 0

    private val musics = listOf(
        R.raw.music_fine,
    )

    override fun onCreate() {
        super.onCreate()
        // todo
        player = MediaPlayer.create(
            this,
            R.raw.music_fine,
        )
        playMusic()
    }

    override fun onBind(intent: Intent?) = null

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMusic()
    }

    private fun playMusic() = player.start()

    private fun stopMusic() = player.run {
        if (this.isPlaying) {
            stop()
        }
    }

    private fun resumeMusic() = player.run {
        seekTo(musicPosition)
        start()
    }

    private fun pauseMusic() = player.run {
        if (this.isPlaying) {
            musicPosition = this.currentPosition
            pause()
        }
    }
}

const val NAME_INTENT_FILTER = "music-intent-filter"
const val INTENT_EXTRA_MESSAGE = "message"

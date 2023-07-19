package app.junsu.androidserviceex.music

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import app.junsu.androidserviceex.R

class MusicService : Service() {
    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "notification-channel-music-player"
    }

    private val broadcastManager by lazy { LocalBroadcastManager.getInstance(this) }
    private val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
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

        showPlayerNotification()
    }

    private fun showPlayerNotification() {

        val notificationLayout = RemoteViews(packageName, R.layout.layout_music_notification_player)

        // todo
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "Music Player",
            NotificationManager.IMPORTANCE_DEFAULT,
        )

        notificationManager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_media_play)
            // todo
            .setContentTitle("MUSIC TITLE")
            .setContentText("Music playing")
            .setCustomBigContentView(notificationLayout)

        val notificationId = 1000
        startForeground(notificationId, builder.build())
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
        // todo
        stopForeground(true)
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

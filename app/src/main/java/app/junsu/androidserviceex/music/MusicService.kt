package app.junsu.androidserviceex.music

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.media.MediaPlayer
import android.media.session.MediaSession
import android.widget.RemoteViews
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import app.junsu.androidserviceex.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

// todo
private val musicState = MutableStateFlow(false)

class MusicService : Service() {
    class MBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(
            context: Context?,
            intent: Intent?,
        ) {
            println("INTENTINTENT")
            val action = intent?.action
            action?.let {
                println()
                when (action) {
                    ACTION_PLAY -> musicState.value = true
                    ACTION_STOP -> musicState.value = false
                }
            }
        }
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "notification-channel-music-player"

        public const val ACTION_PLAY = "app.junsu.intent.action.ACTION_PLAY"
        public const val ACTION_STOP = "app.junsu.intent.action.ACTION_STOP"
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
        observeMusicState()
    }

    private fun observeMusicState() {
        CoroutineScope(Dispatchers.Main).launch {
            musicState.collect {
                println("MUSICMUSIC ${musicState.value}")
                if (musicState.value) {
                    playMusic()
                } else {
                    pauseMusic()
                }
            }
        }
    }

    private fun showPlayerNotification() {
        val ms = MediaSession(
            this,
            NOTIFICATION_CHANNEL_ID,
        )
        val notificationLayout = RemoteViews(packageName, R.layout.layout_music_notification_player)

        // todo
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "Music Player",
            NotificationManager.IMPORTANCE_DEFAULT,
        )

        notificationManager.createNotificationChannel(channel)

        val builder = Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_media_play)
            // todo
            .setContentTitle("MUSIC TITLE").setContentText("Music playing").setStyle(
                Notification.MediaStyle().setMediaSession(ms.sessionToken),
            ).setCustomContentView(notificationLayout).setOngoing(true)

        val actions = listOf(
            Notification.Action.Builder(
                Icon.createWithResource(
                    this,
                    android.R.drawable.ic_media_pause,
                ),
                "STOP",
                PendingIntent.getBroadcast(
                    this@MusicService,
                    0,
                    Intent(this@MusicService, MBroadcastReceiver::class.java).apply {
                        action = ACTION_STOP
                    },
                    PendingIntent.FLAG_IMMUTABLE
                ),
            ).build(),
            Notification.Action.Builder(
                Icon.createWithResource(
                    this,
                    android.R.drawable.ic_media_play,
                ),
                "PLAY",
                PendingIntent.getBroadcast(
                    this@MusicService,
                    0,
                    Intent(this@MusicService, MBroadcastReceiver::class.java).apply {
                        action = ACTION_PLAY
                    },
                    PendingIntent.FLAG_IMMUTABLE
                ),
            ).build(),
        )
        builder.setActions(*actions.toTypedArray())
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

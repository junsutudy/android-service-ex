package app.junsu.androidserviceex.music

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat.Action
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.media.app.NotificationCompat

class MusicService2 : Service() {
    private val hasNotificationPermission: Boolean
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        } else {
            // todo
            true
        }

    override fun onBind(intent: Intent?) = null

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        displayMediaController()
        return START_NOT_STICKY
    }

    @SuppressLint("MissingPermission")
    private fun displayMediaController() {
        val mediaSession = MediaSessionCompat(
            this,
            TAG_MEDIA_SESSION,
        ).apply {
            setMetadata(
                MediaMetadataCompat.Builder().putString(
                    METADATA_KEY_TITLE,
                    // todo
                    "title",
                ).putString(
                    METADATA_KEY_ARTIST,
                    // todo
                    "artist",
                ).putLong(
                    METADATA_KEY_DURATION,
                    12345L,
                ).putString(
                    METADATA_KEY_ALBUM_IMAGE_URL,
                    "https://avatars.githubusercontent.com/u/101160207?v=4",
                ).build(),
            )
        }
        val builder = androidx.core.app.NotificationCompat.Builder(
            this,
            ID_CHANNEL_NOTIFICATION,
        ).setStyle(
            NotificationCompat.MediaStyle().setMediaSession(mediaSession.sessionToken)
                .setShowActionsInCompactView(0, 2)
        ).addAction(
            Action(
                IconCompat.createWithResource(
                    this@MusicService2,
                    R.drawable.ic_media_rew,
                ),
                ACTION_RESUME,
                PendingIntent.getBroadcast(
                    this@MusicService2,
                    0,
                    Intent(
                        this@MusicService2,
                        MusicServiceReceiver::class.java,
                    ),
                    PendingIntent.FLAG_UPDATE_CURRENT,
                ),
            ),
        )

        if (!hasNotificationPermission) {
            return
        }
        NotificationManagerCompat.from(this).notify(
            0,
            builder.build(),
        )
    }

    companion object {
        private const val TAG_MEDIA_SESSION = "app.junsu.musicservice.MEDIA_SESSION"

        private const val ID_CHANNEL_NOTIFICATION = "app.junsu.musicservice.CH_ID_NOTIFICATION"

        const val ACTION_RESUME = "app.junsu.intent.action.ACTION_RESUME"
        const val ACTION_PAUSE = "app.junsu.intent.action.ACTION_PAUSE"

        private const val METADATA_KEY_TITLE = "app.junsu.METADATA_KEY_TITLE"
        private const val METADATA_KEY_ARTIST = "app.junsu.METADATA_KEY_TITLE"
        private const val METADATA_KEY_DURATION = "app.junsu.METADATA_KEY_DURATION"
        private const val METADATA_KEY_ALBUM_IMAGE_URL = "app.junsu.METADATA_KEY_ALBUM_IMAGE_URL"
    }
}

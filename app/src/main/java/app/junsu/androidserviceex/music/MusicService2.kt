package app.junsu.androidserviceex.music

import android.R
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat.Action
import androidx.core.graphics.drawable.IconCompat
import androidx.media.app.NotificationCompat

class MusicService2 : Service() {
    override fun onBind(intent: Intent?) = null

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
        val mediaStyle = androidx.core.app.NotificationCompat.Builder(
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

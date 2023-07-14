package app.junsu.androidserviceex.music

import android.app.Service
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import app.junsu.androidserviceex.R

class MusicService : Service() {
    private val broadcastManager by lazy { LocalBroadcastManager.getInstance(this) }

    private val musics = listOf(
        R.raw.music_fine,
    )

    override fun onCreate() {
        super.onCreate()
        println("SERVICE CREATED")
    }

    override fun onBind(intent: Intent?) = null

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        println("SERVICE STARTED")
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        println("SERVICE STOPPED")
    }
}

const val NAME_INTENT_FILTER = "music-intent-filter"
const val INTENT_EXTRA_MESSAGE = "message"

package app.junsu.androidserviceex.music

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MusicActivity : AppCompatActivity() {/*
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(
            context: Context?,
            intent: Intent?,
        ) {
            Toast.makeText(
                this@MusicActivity,
                "message received, $intent",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }
    private val broadcastManager by lazy { LocalBroadcastManager.getInstance(this) }

    override fun onResume() {
        super.onResume()
        registerMusicReceiver()
    }

    private fun registerMusicReceiver(
        isUnregister: Boolean = false,
    ) {
        if (!isUnregister) {
            broadcastManager.registerReceiver(
                broadcastReceiver,
                IntentFilter(NAME_INTENT_FILTER),
            )
        } else {
            broadcastManager.unregisterReceiver(broadcastReceiver)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(applicationContext, MusicService::class.java))
    }

    override fun onPause() {
        super.onPause()
        registerMusicReceiver(isUnregister = true)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(applicationContext, MusicService::class.java))
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(
            Intent(
                applicationContext,
                MusicService2::class.java,
            ),
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(
            Intent(
                applicationContext,
                MusicService2::class.java,
            ),
        )
    }
}

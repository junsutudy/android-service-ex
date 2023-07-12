package app.junsu.androidserviceex

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TestService : Service() {

    /** called when service first created */
    override fun onCreate() {
        super.onCreate()
    }

    /** called when service started with [startService] */
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        super.onStartCommand(intent, flags, startId)

        return START_STICKY
    }

    /** called when service started with [bindService] */
    override fun onBind(intent: Intent?): IBinder? {
        return null // no binding provided when returns null
    }

    /** called when service unbound with [unbindService] */
    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    /** called when service destroyed */
    override fun onDestroy() {
        super.onDestroy()
    }
}

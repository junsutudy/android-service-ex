package app.junsu.androidserviceex

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Process
import android.util.Log

class TestService : Service() {
    private val TAG = this@TestService.javaClass.simpleName

    private lateinit var serviceLooper: Looper
    private lateinit var serviceHandler: ServiceHandler

    private inner class ServiceHandler(
        looper: Looper,
    ) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.w(TAG, "handleMessage: handling message $msg")
            Thread.sleep(5000L)
            stopSelf(msg.arg1)
        }
    }

    /** called when service first created */
    override fun onCreate() {
        super.onCreate()
        Log.w(TAG, "onCreate : service created")

        HandlerThread(
            "Thread-TestService",
            Process.THREAD_PRIORITY_BACKGROUND,
        ).apply {
            start()

            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    /** called when service started with [startService] */
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        super.onStartCommand(intent, flags, startId)
        Log.e(TAG, "onStartCommand : service started without binding")

        serviceHandler.obtainMessage().also { msg ->
            msg.arg1 = startId
            serviceHandler.sendMessage(msg)
        }

        return START_STICKY
    }

    /** called when service started with [bindService] */
    override fun onBind(intent: Intent?): IBinder? {
        Log.e(TAG, "onBind : service started with binding")

        return null // no binding provided when returns null
    }

    /** called when service unbound with [unbindService] */
    override fun onUnbind(intent: Intent?): Boolean {
        Log.e(TAG, "onUnbind : service unbound")

        return super.onUnbind(intent)
    }

    /** called when service destroyed */
    override fun onDestroy() {
        Log.e(TAG, "onDestroy : service destroyed")

        super.onDestroy()
    }
}

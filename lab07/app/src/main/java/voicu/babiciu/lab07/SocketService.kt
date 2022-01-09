package voicu.babiciu.lab07

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class SocketService : Service() {

    companion object {
        const val LOG_TAG = "ServerService"
    }
private lateinit var socketServer:ServerSocketThread;
    override fun onBind(intent: Intent?): IBinder {
        return myBinder
    }

    private val myBinder: IBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): SocketService {
            return this@SocketService
        }
        // Return object of SocketService class which can be used
        // to access all the public methods of this class
    }

    override fun onCreate() {
        super.onCreate()
        Log.v(LOG_TAG, "in onCreate")
        socketServer = ServerSocketThread{
            Log.e(LOG_TAG, it)
            val intent = Intent()
            intent.action = MainActivity.FILTER_ACTION_KEY
            intent.putExtra("message", it)
            LocalBroadcastManager.getInstance(applicationContext)
                .sendBroadcast(intent)
        }
        socketServer.start()

    }

    override fun onRebind(intent: Intent) {
        Log.v(LOG_TAG, "in onRebind")
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.v(LOG_TAG, "in onUnbind")
        socketServer.close()
        return true
    }
}
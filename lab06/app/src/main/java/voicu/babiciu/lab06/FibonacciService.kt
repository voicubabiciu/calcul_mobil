package voicu.babiciu.lab06


import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import android.os.SystemClock
import android.widget.Toast


class FibonacciService : JobIntentService() {
    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, FibonacciService::class.java, 2, intent)
        }
    }
    override fun onHandleWork(intent: Intent) {
        val message = intent.getStringExtra("message")
        intent.action = MainActivity.FILTER_ACTION_KEY

        val start = SystemClock.uptimeMillis()
        val position: Int = message!!.toInt()
        var first: Long = 1
        var second: Long = 1
        var result: Long = if (position == 0) 0 else 1
        if (position >= 3) {
            for (i in 3..position) {
                result = second + first
                first = second
                second = result
            }
        }

        val end = SystemClock.uptimeMillis()
        val resultMessage =
            "Result: $result elapsed ${end - start} milliseconds"
        LocalBroadcastManager.getInstance(applicationContext)
            .sendBroadcast(intent.putExtra("broadcastMessage", resultMessage))
    }

    override fun onStartCommand(
        intent: Intent?, flags: Int, startId: Int
    ): Int {
        Toast.makeText(
            applicationContext,
            "Intent SERVICE CREATED",
            Toast.LENGTH_SHORT
        ).show()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(
            applicationContext,
            "Intent SERVICE DESTROYED",
            Toast.LENGTH_SHORT
        ).show()
    }
}
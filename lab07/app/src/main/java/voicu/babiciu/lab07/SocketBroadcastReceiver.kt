package voicu.babiciu.lab07

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.TextView

class SocketBroadcastReceiver(private val textView: TextView) :
    BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val content =
            "${intent!!.getStringExtra("message")}\n${textView.text}"

        textView.text = content
    }
}
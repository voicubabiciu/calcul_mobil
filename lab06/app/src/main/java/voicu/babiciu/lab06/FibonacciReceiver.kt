package voicu.babiciu.lab06

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.EditText
import android.widget.TextView

class FibonacciReceiver(
    private val editText: EditText,
    private val textView: TextView
) :
    BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent!!.getStringExtra("broadcastMessage")
        val content = textView.text.toString() + "\n" + message;
        textView.text = content
    }
}
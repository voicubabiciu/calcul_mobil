package voicu.babiciu.lab06

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import voicu.babiciu.lab06.databinding.ActivityMainBinding
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import android.content.IntentFilter


class MainActivity : AppCompatActivity() {
    companion object {
        const val FILTER_ACTION_KEY = "999"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var fibonacciReceiver: FibonacciReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.inputText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.button.performClick();
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.button.setOnClickListener {
            val message: String = binding.inputText.text.toString()
            val intent = Intent()
            intent.putExtra("message", message)
            FibonacciService.enqueueWork(this, intent)
        }
    }

    override fun onStart() {
        setReceiver()
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(fibonacciReceiver)
        super.onStop()
    }

    private fun setReceiver() {
        fibonacciReceiver =
            FibonacciReceiver(binding.textView)
        val intentFilter = IntentFilter()
        intentFilter.addAction(FILTER_ACTION_KEY)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(fibonacciReceiver, intentFilter)
    }
}
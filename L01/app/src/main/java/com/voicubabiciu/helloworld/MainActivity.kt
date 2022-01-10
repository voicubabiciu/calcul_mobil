package com.voicubabiciu.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.voicubabiciu.helloworld.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var counter: Int = 0
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCount.setOnClickListener {
            val content = "You clicked me ${counter++} times"
            binding.textView.text = content
        }
    }
}
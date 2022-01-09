package com.voicubabiciu.l02

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.voicubabiciu.l02.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.hasExtra("VALUE")){
            binding.tilValue.editText?.setText(intent.getStringExtra("VALUE"))
        }
        binding.btnSave.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("VALUE", binding.tilValue.editText?.text.toString())
            setResult(Activity.RESULT_OK, intent )
            finish()
        }
    }
}
package com.voicubabiciu.l02

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.voicubabiciu.l02.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val getValue = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            it.data?.let { result ->
                if(result.hasExtra("VALUE")){
                    binding.tvValue.text = result.getStringExtra("VALUE")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("VALUE", binding.tvValue.text.toString())
            getValue.launch(intent)
        }
    }
}
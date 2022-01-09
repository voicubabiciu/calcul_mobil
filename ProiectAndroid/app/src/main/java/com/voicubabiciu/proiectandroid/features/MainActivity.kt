package com.voicubabiciu.proiectandroid.features

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import com.voicubabiciu.proiectandroid.databinding.ActivityMainBinding
import com.voicubabiciu.proiectandroid.features.home.HomeActivity

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.auth.addAuthStateListener {
            it.currentUser?.let { user ->
                if (!user.isEmailVerified) {
                    Snackbar.make(
                        binding.root,
                        "Please verify your account",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    Firebase.auth.signOut()
                } else {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }

}
package com.voicubabiciu.proiectandroid

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseOptions

import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.e("TODO_APP", "ON CREATE")
        Firebase.initialize(applicationContext)
        val options = FirebaseOptions.Builder()
            .setProjectId("devcrew-1530638117236")
            .setApplicationId("1:129313665587:android:2d6e1d0d023efbd83b2946")
            .setApiKey("AIzaSyAPECHCMnYL8U_EbSKCeT3xxJTWg4G4Hew")
            // .setDatabaseUrl(...)
            // .setStorageBucket(...)
            .build()
        Firebase.initialize(applicationContext, options,"AuthApp")
    }
}
package com.ancafarcas.proiectandroid

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
            .setProjectId("todo-e2826")
            .setApplicationId("1:466497826159:android:8cc6f9c550864bbb38cc9a")
            .setApiKey("AIzaSyAmDwCho7JJHmvpkGjd7VCKBK0iZPoNn8M")
            // .setDatabaseUrl(...)
            // .setStorageBucket(...)
            .build()
        Firebase.initialize(applicationContext, options,"AuthApp")
    }
}
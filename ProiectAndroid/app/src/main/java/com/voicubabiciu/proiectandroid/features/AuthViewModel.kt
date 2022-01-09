package com.voicubabiciu.proiectandroid.features

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthViewModel : ViewModel() {

    val loggedIn = MutableLiveData(false)
    fun init(){

    }
}
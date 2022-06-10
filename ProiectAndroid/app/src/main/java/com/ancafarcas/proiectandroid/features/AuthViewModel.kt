package com.ancafarcas.proiectandroid.features

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    val loggedIn = MutableLiveData(false)
    fun init(){

    }
}
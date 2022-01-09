package com.voicubabiciu.proiectandroid.features.login

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voicubabiciu.proiectandroid.common.BusinessException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) :
    ViewModel() {
    val loginModel = MutableLiveData(LoginModel())
    val emailError = MutableLiveData<String?>("")
    val passwordError = MutableLiveData<String?>("")
    val loginEnabled = MutableLiveData(false)
    val loading = MutableLiveData(View.GONE)
    val showButtons = MutableLiveData(View.VISIBLE)
    val loginError = MutableLiveData<String>()
    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(View.VISIBLE)
            showButtons.postValue(View.INVISIBLE)
            try {
                loginModel.value?.let {
                    repository.login(it)

                }

            } catch (error: BusinessException){
                loginError.postValue(error.message)
            }
            loading.postValue(View.GONE)
            showButtons.postValue(View.VISIBLE)
        }
    }

    fun validateModel() {
        loginEnabled.postValue(
            (emailError.value == null && passwordError.value
                    == null)
        )
    }

    fun emailChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isEmpty()) {
            emailError.postValue("Field required")
        } else {
            emailError.postValue(null)
        }
    }

    fun passwordChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isEmpty()) {
            passwordError.postValue("Field required")
        } else {
            passwordError.postValue(null)
        }

    }

}
package com.voicubabiciu.proiectandroid.features.register

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.voicubabiciu.proiectandroid.common.BusinessException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
) : ViewModel() {

    val registerModel = MutableLiveData(RegisterModel())
    val registerError = MutableLiveData<String>()
    val firstNameError = MutableLiveData("")
    val lastNameError = MutableLiveData("")
    val emailError = MutableLiveData("")
    val passwordError = MutableLiveData("")
    val passwordConfirmationError = MutableLiveData("")
    val registerSuccess = MutableLiveData<String>()
    val registerEnabled = MutableLiveData(false)
    val loading = MutableLiveData(View.GONE)
    val showRegisterButton = MutableLiveData(View.VISIBLE)
    fun register() {

        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(View.VISIBLE)
            showRegisterButton.postValue(View.INVISIBLE)

            try {
                registerModel.value?.let {
                    repository.register(it)
                    registerSuccess.postValue(
                        "An verification email was sent" +
                                " to ${it.email}"
                    )

                }
            } catch (ex: BusinessException) {
                registerError.postValue(ex.message ?: "")
            }
            loading.postValue(View.GONE)
            showRegisterButton.postValue(View.VISIBLE)


        }

    }

    fun validateModel() {
        registerEnabled.postValue(
            firstNameError.value == null &&
                    lastNameError.value == null &&
                    emailError.value == null &&
                    passwordError.value == null &&
                    passwordConfirmationError.value == null
        )
    }

    fun firstNameChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isEmpty()) {
            firstNameError.postValue("Field required")
        } else {
            firstNameError.postValue(null)
        }
    }

    fun lastNameChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isEmpty()) {
            lastNameError.postValue("Field required")
        } else {
            lastNameError.postValue(null)
        }
    }

    fun emailChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isEmpty()) {
            emailError.postValue("Field required")
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
            emailError.postValue("Invalid email")
        } else {
            emailError.postValue(null)
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
            .matcher(password).matches()
    }

    fun passwordChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isEmpty()) {
            passwordError.postValue("Field required")
        } else if (!isPasswordValid(s.toString())) {
            passwordError.postValue("Minimum eight characters, at least one letter and one number")
        } else if (!registerModel.value?.passwordConfirmation.isNullOrEmpty()) {
            if (s.toString() != registerModel.value?.passwordConfirmation) {
                passwordError.postValue("Passwords does not match")
                passwordConfirmationError.postValue("Passwords does not match")
            } else {
                passwordError.postValue(null)
                passwordConfirmationError.postValue(null)
            }
        } else {
            passwordError.postValue(null)
        }

    }

    fun passwordConfirmationChanged(
        s: CharSequence, start: Int, before: Int,
        count: Int
    ) {
        if (s.isEmpty()) {
            passwordConfirmationError.postValue("Field required")
        } else if (!isPasswordValid(s.toString())) {
            passwordConfirmationError.postValue("Minimum eight characters, at least one letter and one number")
        } else if (!registerModel.value?.password.isNullOrEmpty()) {
            if (s.toString() != registerModel.value?.password) {
                passwordError.postValue("Passwords does not match")
                passwordConfirmationError.postValue("Passwords does not match")
            } else {
                passwordError.postValue(null)
                passwordConfirmationError.postValue(null)
            }
        } else {
            passwordConfirmationError.postValue(null)
        }

    }
}
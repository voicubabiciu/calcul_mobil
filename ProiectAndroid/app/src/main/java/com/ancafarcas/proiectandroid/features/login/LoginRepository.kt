package com.ancafarcas.proiectandroid.features.login

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ancafarcas.proiectandroid.common.BusinessException
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class LoginRepository @Inject constructor() {
    suspend fun login(loginModel: LoginModel) {
        try {
            Firebase.auth.signInWithEmailAndPassword(
                loginModel
                    .email,
                loginModel.password
            ).await()
        } catch (ex: Exception) {
            throw BusinessException(ex.message ?: "Unknown error")
        }
    }
}
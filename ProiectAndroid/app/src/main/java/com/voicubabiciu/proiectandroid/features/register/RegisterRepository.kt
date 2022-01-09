package com.voicubabiciu.proiectandroid.features.register

import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.ktx.initialize
import com.voicubabiciu.proiectandroid.common.BusinessException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterRepository @Inject constructor() {

    suspend fun register(registerModel: RegisterModel) {
        try {
            val secondary = Firebase.app("AuthApp")
            val result = Firebase.auth(secondary).createUserWithEmailAndPassword(
                registerModel
                    .email,
                registerModel.password
            ).await()
            Firebase.auth(secondary).signOut()
            result.user?.updateProfile(userProfileChangeRequest {
                displayName =
                    "${registerModel.firsName} ${registerModel.lastName}"
            })?.await()

            result.user?.let {
                it.sendEmailVerification().await()
                val user = hashMapOf(
                    "firstName" to registerModel.firsName,
                    "lastName" to registerModel.lastName,
                    "email" to it.email,
                )
                Firebase.firestore(secondary).collection("users")
                    .document(it.uid).set(user).await()

            }
        } catch (ex: Exception) {
            throw BusinessException(ex.message ?: "Unknown error")
        }
    }
}
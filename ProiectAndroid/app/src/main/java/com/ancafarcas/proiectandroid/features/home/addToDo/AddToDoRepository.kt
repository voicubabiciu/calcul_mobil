package com.ancafarcas.proiectandroid.features.home.addToDo

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ancafarcas.proiectandroid.common.BusinessException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AddToDoRepository @Inject constructor() {

    suspend fun addToDo(todo: ToDoModel) {
        try {
            Firebase.auth.currentUser?.let { user ->
                val doc = Firebase.firestore.collection("users").document(user
                .uid).collection("todos").document()
                val data = todo.apply { id =  doc.id}
                doc.set(data).await()
            }
        } catch (ex: Exception) {
            throw BusinessException(ex.message ?: "Unknown error")
        }
    }
}
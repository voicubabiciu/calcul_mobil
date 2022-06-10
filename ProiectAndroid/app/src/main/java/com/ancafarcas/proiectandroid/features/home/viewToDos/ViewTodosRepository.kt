package com.ancafarcas.proiectandroid.features.home.viewToDos

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ancafarcas.proiectandroid.common.BusinessException
import com.ancafarcas.proiectandroid.features.home.addToDo.ToDoModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ViewTodosRepository @Inject constructor() {
    suspend fun toggleState(todo: ToDoModel) {
        try {
            Firebase.auth.currentUser?.let { user ->
                Firebase.firestore.collection("users").document(
                    user
                        .uid
                ).collection("todos").document(todo.id)
                    .update("done", !todo.isDone).await()
            }
        } catch (ex: Exception) {
            throw BusinessException(ex.message ?: "Unknown error")
        }
    }
}
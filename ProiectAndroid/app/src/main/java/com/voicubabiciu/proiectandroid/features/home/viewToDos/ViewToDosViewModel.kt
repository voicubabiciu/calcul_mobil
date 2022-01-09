package com.voicubabiciu.proiectandroid.features.home.viewToDos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.voicubabiciu.proiectandroid.features.home.addToDo.ToDoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewToDosViewModel @Inject constructor(private val repository:
ViewTodosRepository) : ViewModel() {

    val todos = TodosQueryLiveData(Firebase.firestore.collection("users")
        .document(Firebase.auth.currentUser?.uid?:"").collection("todos"))

    fun toggleState(todo: ToDoModel){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.toggleState(todo)
            }
        }
    }
}
package com.voicubabiciu.proiectandroid.features.home.addToDo

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voicubabiciu.proiectandroid.common.BusinessException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddToDoViewModel @Inject constructor(private val repository: AddToDoRepository) :
    ViewModel() {
    val todoModel = MutableLiveData(ToDoModel())
    val titleError = MutableLiveData("")
    val detailsError = MutableLiveData("")
    val stateError = MutableLiveData<String>()
    val stateSuccess = MutableLiveData<Unit>()
    val loading = MutableLiveData(View.GONE)
    val showAddButton = MutableLiveData(View.VISIBLE)
    val addEnabled = MutableLiveData(false)


    fun checkForm(){
        addEnabled.postValue(titleError.value == null && detailsError.value == null)
    }
    fun titleChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isEmpty()) {
            titleError.postValue("Field required")
        } else {
            titleError.postValue(null)
        }
    }

    fun detailsChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isEmpty()) {
            detailsError.postValue("Field required")
        } else {
            detailsError.postValue(null)
        }
    }

    fun addToDo() {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(View.VISIBLE)
            showAddButton.postValue(View.GONE)
            todoModel.value?.let {
                val todo = it.apply { dateTime = Date() }
                try{
                    repository.addToDo(todo)
                    stateSuccess.postValue(Unit)
                } catch (ex: BusinessException){
                    stateError.postValue(ex.message)
                }
            }
            loading.postValue(View.GONE)
            showAddButton.postValue(View.VISIBLE)
        }

    }
}
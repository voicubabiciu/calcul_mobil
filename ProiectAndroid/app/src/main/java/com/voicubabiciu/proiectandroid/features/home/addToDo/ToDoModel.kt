package com.voicubabiciu.proiectandroid.features.home.addToDo

import java.util.*

data class ToDoModel(
    var id: String = "",
    var title: String = "",
    var details: String = "",
    var isDone: Boolean = false,
    var dateTime: Date = Date()
)

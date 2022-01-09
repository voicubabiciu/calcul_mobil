package voicu.babiciu.lab3

import java.io.Serializable

data class BookModel(
    val id: Int,
    val title: String, val author: String,
    var description: String,
    val cover: String,
) : Serializable

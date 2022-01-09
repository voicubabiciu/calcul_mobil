package voicu.babiciu.lab05

import java.io.Serializable
import java.net.URL

data class ItemModel(
    val title: String, val details: String, val urlImage: URL
    = URL("https://nucbm.github.io/joker.png")
) : Serializable

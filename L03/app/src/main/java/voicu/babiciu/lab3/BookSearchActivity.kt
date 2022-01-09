package voicu.babiciu.lab3

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import voicu.babiciu.lab3.databinding.ActivityBookSearchBinding

class BookSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookSearchBinding
    private val bookPageLauncher = registerForActivityResult(
        ActivityResultContracts
            .StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                val book = it.getSerializableExtra("EXTRA_BOOK") as
                        BookModel
                books = books.map { b -> if (b.id == book.id) book else b }
            }

            // Handle the Intent
        }
    }

    private var books: List<BookModel> = listOf(
        BookModel(
            0,
            "book1", "author1", "Description book1",
            "https://upload.wikimedia.org/wikipedia/commons/e/e5/Java_Programming_Cover.jpg"
        ),
        BookModel(
            1,
            "book2", "author2", "Description book2",
            "https://images-na.ssl-images-amazon.com/images/I/41oBJp9leBL.jpg"
        ),
        BookModel(
            2,
            "book3", "author3", "Description book3",
            "https://images-na.ssl-images-amazon.com/images/I/41LYRW20XtL.jpg"
        ),
        BookModel(
            3,
            "book4", "author4", "Description book4",
            ""
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initEvents()
    }

    private fun initEvents() {
        binding.searchButton.setOnClickListener {
            binding.bookTitleField.editText?.let {
                if (it.text.isNullOrEmpty()) {
                    return@let
                }
                try {
                    val searchedBook = books.first { book ->
                        book.title == it.text.toString()
                    }
                    val intent = Intent(
                        this,
                        BookActivity().javaClass
                    )
                    intent.putExtra("EXTRA_BOOK", searchedBook)
                    bookPageLauncher.launch(intent)
                } catch (ex: NoSuchElementException) {
                    Toast.makeText(
                        this@BookSearchActivity, "No book found", Toast
                            .LENGTH_SHORT
                    ).show()
                }


            }
        }
    }
}
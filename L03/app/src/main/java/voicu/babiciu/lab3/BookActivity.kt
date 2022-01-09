package voicu.babiciu.lab3

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import voicu.babiciu.lab3.databinding.ActivityBookBinding
import java.util.concurrent.Executors

class BookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookBinding
    private lateinit var book: BookModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        book = intent.getSerializableExtra("EXTRA_BOOK") as BookModel
        binding.descriptionField.editText?.setText(book.description)
        initEvents()
        loadImage()
    }

    private fun initEvents() {
        binding.saveButton.setOnClickListener {
            binding.descriptionField.editText?.let {
                val intent = Intent()
                intent.putExtra("EXTRA_BOOK", book.copy(description = it.text
                    .toString() ))
                setResult(RESULT_OK, intent)
                finish()
            }

        }
    }
    private fun loadImage(){
        Glide.with(this)
            .load(book.cover)
            .into(binding.bookCover)
    }
}
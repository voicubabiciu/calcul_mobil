package voicu.babiciu.lab10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import voicu.babiciu.lab10.databinding.ActivityMainBinding

import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageException
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.net.SocketException
import java.util.*


class MainActivity : AppCompatActivity() {


    private val storage = Firebase.storage

    // Create a storage reference from our app
    private val storageRef = storage.reference

    private lateinit var binding: ActivityMainBinding
    private val scope = CoroutineScope(Dispatchers.IO)


    private val openGalleryLauncher = registerForActivityResult(
        ActivityResultContracts
            .GetContent()
    ) {
        binding.progress.visibility = View.VISIBLE
        binding.image.visibility = View.INVISIBLE
        val date = DateFormat.format("dd.MM.yyyy hh:mm", Date())
        val ref = storageRef.child("images/${date}.jpg")
        val res = scope.async {
            ref.putFile(it).await()
        }
        scope.launch(Dispatchers.Main) {
            try {
                res.await()

                Toast.makeText(
                    this@MainActivity, "Image uploaded",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (ex: Exception) {
                Toast.makeText(
                    this@MainActivity, "Error uploading",
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                binding.progress.visibility = View.GONE
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initEvents()
    }

    private fun initEvents() {
        binding.btnUpload.setOnClickListener {
            launchGallery()
        }
        binding.btnDownload.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            binding.image.setImageURI(null)
            binding.image.visibility = View.INVISIBLE
            val date = DateFormat.format("dd.MM.yyyy hh:mm", Date())
            val ref = storageRef.child("images/${date}.jpg")
            val url = scope.async {
                var url = ""
                try {
                    url = ref.downloadUrl.await().toString()

                } catch (socketEx: SocketException) {
                    Log.e("SocketException", socketEx.message.toString())
                } catch (storageEx: StorageException) {
                    Log.e("StorageException", storageEx.message.toString())
                }
                url

            }
            scope.launch(Dispatchers.Main) {
                val downloadUri = url.await()
                binding.progress.visibility = View.GONE
                if (downloadUri.isNullOrEmpty()) {
                    Toast.makeText(
                        this@MainActivity, "Image not found",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    binding.image.visibility = View.VISIBLE
                    Glide.with(this@MainActivity)
                        .load(downloadUri)
                        .into(binding.image)
                }


            }
        }
    }

    private fun launchGallery() {
        openGalleryLauncher.launch("image/*")
    }
}
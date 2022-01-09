package voicu.babiciu.lab05

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.URL
import java.util.*

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.motion.utils.ViewState
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import voicu.babiciu.lab05.databinding.CardViewDesignBinding
import kotlin.random.Random


class ItemsAdapter(
    private val context: Context,
    private val itemList: List<ItemModel>
) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {
    private val scope = CoroutineScope(Dispatchers.IO)

    // create new views
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        CardViewDesignBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    )


    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // sets the text to the textview from our itemHolder class
        holder.titleView.text = itemList[position].title
        holder.loading.visibility = View.VISIBLE
        holder.image.visibility = View.INVISIBLE
        val result: Deferred<Bitmap?> = scope.async {
            delay(Random.nextLong(1000, 5000))
            itemList[position].urlImage.toBitmap()
        }
        scope.launch(Dispatchers.Main) {
            val bitmap : Bitmap? = result.await()
            // if downloaded then saved it to internal storage
            bitmap?.apply {
                // get saved bitmap internal storage uri
                val savedUri : Uri? = saveToInternalStorage()

                // display saved bitmap to image view from internal storage
                holder.image.setImageURI(savedUri)
                holder.loading.visibility = View.INVISIBLE
                holder.image.visibility = View.VISIBLE
            }
        }
    }

    private fun Bitmap.saveToInternalStorage():Uri?{
        // get the context wrapper instance
        val wrapper = ContextWrapper(context)

        // initializing a new file
        // bellow line return a directory in internal storage
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)

        // create a file to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")

        return try {
            // get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // compress bitmap
            compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // flush the stream
            stream.flush()

            // close stream
            stream.close()

            // return the saved image uri
            Uri.parse(file.absolutePath)
        } catch (e: IOException){ // catch the exception
            e.printStackTrace()
            null
        }
    }


    // extension function to get / download bitmap from url
    private fun URL.toBitmap(): Bitmap?{
        return try {
            BitmapFactory.decodeStream(openStream())
        }catch (e: IOException){
            null
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int = itemList.size

    // Holds the views for adding it to image and text
    class ViewHolder(cardViewDesignBinding: CardViewDesignBinding) :
        RecyclerView.ViewHolder(cardViewDesignBinding.root) {
        val titleView: TextView = cardViewDesignBinding.title
        val image: ImageView = cardViewDesignBinding.image
        val loading: ProgressBar = cardViewDesignBinding.loading

    }
}

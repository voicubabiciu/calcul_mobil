package voicu.babiciu.lab04


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import voicu.babiciu.lab04.databinding.CardViewDesignBinding


class ItemsAdapter(
    private val itemList: List<ItemModel>, private val
    onItemClick: ((ItemModel) -> Unit)? = null
) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

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

        onItemClick?.let { itemClick ->
            holder.itemView.setOnClickListener {
                itemClick.invoke(
                    itemList[position]
                )
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int = itemList.size

    // Holds the views for adding it to image and text
    class ViewHolder(cardViewDesignBinding: CardViewDesignBinding) :
        RecyclerView.ViewHolder(cardViewDesignBinding.root) {
        val titleView: TextView = cardViewDesignBinding.title
    }
}

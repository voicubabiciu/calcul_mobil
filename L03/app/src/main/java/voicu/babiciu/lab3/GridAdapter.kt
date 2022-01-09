package voicu.babiciu.lab3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import voicu.babiciu.lab3.databinding.GridItemBinding

class GridAdapter(
    context: Context,
    private val buttonsLabels: List<String>,
) : BaseAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var gridItemBinding: GridItemBinding

    override fun getCount(): Int = buttonsLabels.size

    override fun getItem(position: Int): String = buttonsLabels[position]


    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var  view = convertView;

        val holder: ItemViewHolder
        if (view == null) {
            gridItemBinding = GridItemBinding.inflate(inflater)
            view = gridItemBinding.root
            holder = ItemViewHolder()
            holder.button = gridItemBinding.button
            view.tag = holder
        } else {
            holder = view.tag as ItemViewHolder
        }
        holder.button?.let { it.text = buttonsLabels[position] }
        return view

    }

    internal class ItemViewHolder {
        var button: TextView? = null
    }


}
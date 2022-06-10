package com.ancafarcas.proiectandroid.features.home.viewToDos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ancafarcas.proiectandroid.databinding.ItemTodoBinding
import com.ancafarcas.proiectandroid.features.home.addToDo.ToDoModel
import android.text.Spanned

import android.text.style.StrikethroughSpan

import android.text.SpannableString


class TodosAdapter(
    private val itemList: MutableList<ToDoModel> = mutableListOf(),
    private val onItemClick: ((ToDoModel) -> Unit)? = null
) : RecyclerView.Adapter<TodosAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    )

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // sets the text to the textview from our itemHolder class
        holder.titleView.text = itemList[position].title
        holder.details.text = itemList[position].details
        holder.isDone.isChecked = itemList[position].isDone
        if (itemList[position].isDone) {
            val string= SpannableString(itemList[position].title)
            string.setSpan(
                StrikethroughSpan(),
                0,
                itemList[position].title.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            holder.titleView.text = string
        }
        onItemClick?.let { itemClick ->
            holder.isDone.setOnClickListener {
                itemClick.invoke(
                    itemList[position]
                )
            }
            holder.itemView.setOnClickListener {
                itemClick.invoke(
                    itemList[position]
                )
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int = itemList.size

    fun addToDo(toDoModel: ToDoModel) {
        if(itemList.firstOrNull { it.id ==toDoModel.id } == null){
            itemList.add(toDoModel)
            notifyItemInserted(itemList.size - 1)
        }
    }

    fun updateToDo(toDoModel: ToDoModel) {
        if (itemList.isNotEmpty()) {
            val todo: ToDoModel = itemList.first { todo ->
                todo.id == toDoModel.id
            }
            val index = itemList.indexOf(todo)
            itemList[index] = toDoModel
            notifyItemChanged(index)
        }

    }

    fun removeToDo(toDoModel: ToDoModel) {
        if (itemList.isNotEmpty()) {
            var index = 0
            itemList.filterIndexed { i, todo ->
                index = i
                todo.id == toDoModel.id
            }.first()
            itemList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    // Holds the views for adding it to image and text
    class ViewHolder(todoView: ItemTodoBinding) :
        RecyclerView.ViewHolder(todoView.root) {
        val titleView: TextView = todoView.title
        val details: TextView = todoView.details
        val isDone: CheckBox = todoView.isDone
    }
}

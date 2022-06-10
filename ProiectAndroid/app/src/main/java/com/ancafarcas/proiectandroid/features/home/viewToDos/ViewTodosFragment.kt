package com.ancafarcas.proiectandroid.features.home.viewToDos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentChange
import com.ancafarcas.proiectandroid.R
import com.ancafarcas.proiectandroid.databinding.FragmentViewTodosBinding
import com.ancafarcas.proiectandroid.features.home.addToDo.ToDoModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ViewTodosFragment : Fragment() {
    private lateinit var binding: FragmentViewTodosBinding
    private lateinit var navController: NavController
    private val viewModel: ViewToDosViewModel by viewModels()
    private val adapter: TodosAdapter =
        TodosAdapter(onItemClick = { viewModel.toggleState(it) })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewTodosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)
        binding.todos.layoutManager = LinearLayoutManager(requireActivity())
        binding.todos.adapter = adapter
        initLiveData()
        initEvents()
    }


    private fun initLiveData() {
        viewModel.todos.observe(viewLifecycleOwner, { result ->
            if (result.isSuccessful) {
                for (dc: DocumentChange in result.data().documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                            adapter.addToDo(dc.document.toObject(ToDoModel::class.java))
                        }
                        DocumentChange.Type.MODIFIED -> {
                            adapter.updateToDo(dc.document.toObject(ToDoModel::class.java))
                        }
                        DocumentChange.Type.REMOVED -> {
                            adapter.removeToDo(
                                dc.document.toObject(
                                    ToDoModel::class
                                        .java
                                )
                            )
                        }
                    }
                }

            } else {
                Snackbar.make(
                    binding.root, result.error().message ?: "Unknown" +
                    " message",
                    Snackbar
                        .LENGTH_SHORT
                )
                    .show()

            }
        })
    }

    private fun initEvents() {
        binding.btnAddTodo.setOnClickListener {
            navController.navigate(R.id.action_viewTodosFragment_to_addToDoFragment)

        }
    }
}
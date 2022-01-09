package com.voicubabiciu.proiectandroid.features.home.addToDo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsAnimationController
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.voicubabiciu.proiectandroid.R
import com.voicubabiciu.proiectandroid.databinding.FragmentAddToDoBinding
import com.voicubabiciu.proiectandroid.features.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddToDoFragment : Fragment() {

    private lateinit var binding: FragmentAddToDoBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: AddToDoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddToDoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AddToDoViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)
        initLiveData()
        initEvents()
    }

    private fun initEvents() {
        binding.btnAddTodo.setOnClickListener {
            binding.viewModel?.addToDo()
        }
    }

    private fun initLiveData() {
        binding.viewModel?.let {
            it.titleError.observe(viewLifecycleOwner, { err ->
                binding.title.error = err
                it.checkForm()
            })
            it.detailsError.observe(viewLifecycleOwner, { err ->
                binding.details.error = err
                it.checkForm()
            })
            it.stateError.observe(viewLifecycleOwner, { err ->
                Snackbar.make(binding.root, err, Snackbar.LENGTH_SHORT).show()
            })
            it.stateSuccess.observe(viewLifecycleOwner, {
                navController.navigateUp()
            })
        }
    }
}
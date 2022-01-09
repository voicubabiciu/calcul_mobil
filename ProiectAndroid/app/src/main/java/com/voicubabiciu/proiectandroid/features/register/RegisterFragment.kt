package com.voicubabiciu.proiectandroid.features.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import com.voicubabiciu.proiectandroid.databinding.FragmentRegisterBinding

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
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

    private fun initLiveData() {
        binding.viewModel?.let { it ->
            it.firstNameError.observe(viewLifecycleOwner, { err ->
                binding.firstName.error = err
                it.validateModel()
            })
            it.lastNameError.observe(viewLifecycleOwner, { err ->
                binding.lastName.error = err
                it.validateModel()
            })

            it.emailError.observe(viewLifecycleOwner, { err ->
                binding.email.error = err
                it.validateModel()
            })
            it.passwordError.observe(viewLifecycleOwner, { err ->
                binding.password.error = err
                it.validateModel()
            })
            it.passwordConfirmationError.observe(viewLifecycleOwner, { err ->
                binding.passwordConfirmation.error = err
                it.validateModel()
            })
            it.registerError.observe(viewLifecycleOwner, { err ->
                Snackbar.make(binding.root, err, Snackbar.LENGTH_SHORT).show()
            })
            it.registerSuccess.observe(viewLifecycleOwner, { message ->
                Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
                    .show()
                navController.popBackStack()
            })

        }

    }

    private fun initEvents() {
        binding.btnBack.setOnClickListener {
            navController.navigateUp()
        }
        binding.btnRegister.setOnClickListener {
            binding.viewModel?.let {
                it.register()

            }
        }
    }


}
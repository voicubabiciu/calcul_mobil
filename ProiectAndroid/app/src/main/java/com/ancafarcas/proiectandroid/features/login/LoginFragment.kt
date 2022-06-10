package com.ancafarcas.proiectandroid.features.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.ancafarcas.proiectandroid.R
import com.ancafarcas.proiectandroid.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)
        initEvents()
        initLiveData()

    }

    private fun initLiveData() {
        binding.viewModel?.let {
            it.emailError.observe(viewLifecycleOwner, { err ->
                binding.email.error = err
                it.validateModel()

            })
            it.passwordError.observe(viewLifecycleOwner, { err ->
                binding.password.error = err
                it.validateModel()

            })
            it.loginError.observe(viewLifecycleOwner, { err ->
                Snackbar.make(binding.root, err, Snackbar.LENGTH_SHORT).show()
            })
        }

    }

    private fun initEvents() {

        binding.login.setOnClickListener {
            viewModel.login()
        }
        binding.register.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)

        }
    }

}
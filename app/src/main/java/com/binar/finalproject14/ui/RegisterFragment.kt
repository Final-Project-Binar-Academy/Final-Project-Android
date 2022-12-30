package com.binar.finalproject14.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.R
import com.binar.finalproject14.data.api.response.BaseResponse
import com.binar.finalproject14.data.api.response.auth.AuthResponse
import com.binar.finalproject14.databinding.FragmentRegisterBinding
import com.binar.finalproject14.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener(){
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        viewModel.registerResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    processLogin(it.data)
                }
                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {}
            }
        }

        binding.btnRegister.setOnClickListener {
            val fname = binding.edtFirstName.text.toString()
            val lname = binding.edtLastName.text.toString()
            val email = binding.edtEmail.text.toString()
            val pwd = binding.etPassword.text.toString()
            val confPwd = binding.etConfirmPassword.text.toString()
            if (pwd == confPwd){
                viewModel.registerUser(fname, lname, email, pwd)
            }
        }
    }

    fun processLogin(data: AuthResponse?) {
        showToast("Success:" + data?.message)
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

    fun processError(msg: String?) {
        showToast("Error:" + msg)
    }

    fun showToast(msg: String) {
        Toast.makeText(
            requireContext(),
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

}
package com.binar.finalproject14.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.MainActivity
import com.binar.finalproject14.R
import com.binar.finalproject14.data.api.response.BaseResponse
import com.binar.finalproject14.data.api.response.auth.AuthResponse
import com.binar.finalproject14.databinding.FragmentLoginBinding
import com.binar.finalproject14.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val token = SessionManager.getToken(requireContext())
//        if (!token.isNullOrBlank()) {
//            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//        }

        (activity as MainActivity).binding.navHome.visibility = View.GONE

        viewModel.loginResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    processLogin(it.data)
                }
                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {
                }
            }
        }

        binding.forgetPass.setOnClickListener {
            throw RuntimeException("App Crashed")
        }



        binding.btnLogin.setOnClickListener {
            val mail = binding.email.text.toString()
            val pwd = binding.password.text.toString()
            if(mail.isEmpty()){
                binding.email.error = "Username harus terisi"
            }
            if(pwd.isEmpty()){
                binding.password.error = "Password harus terisi"
            }
            if(pwd.length < 8){
                binding.password.error = "Password minimal 8 karakter"
            }
            viewModel.loginUser(email = mail, pwd = pwd)
        }

        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }


    fun processLogin(data: AuthResponse?) {
        showToast("Success:" + data?.message)
        if (data?.data?.accessToken != null) {
            viewModel.saveIsLoginStatus(true)
            viewModel.saveUsername(data.data?.name.toString())
            viewModel.saveId(data.data?.id!!.toInt())
            viewModel.saveToken(data.data?.accessToken.toString())
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
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


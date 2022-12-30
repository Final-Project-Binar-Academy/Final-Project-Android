package com.binar.finalproject14.ui.splashscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentScreenStartBinding
import com.binar.finalproject14.databinding.FragmentSplashScreenBinding
import com.binar.finalproject14.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScreenStartFragment : Fragment() {
    private var _binding: FragmentScreenStartBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScreenStartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnSignUp.setOnClickListener(){
            findNavController().navigate(R.id.action_screenStartFragment_to_registerFragment)
        }
        binding.btnSignIn.setOnClickListener(){
            findNavController().navigate(R.id.action_screenStartFragment_to_loginFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onStart() {
        super.onStart()
        viewModel.getDataStoreIsLogin().observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(R.id.action_screenStartFragment_to_homeFragment)
            }
        }
    }
}
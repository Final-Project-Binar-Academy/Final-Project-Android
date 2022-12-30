package com.binar.finalproject14.ui.splashscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentScreenStartBinding
import com.binar.finalproject14.databinding.FragmentSplashScreenBinding


class ScreenStartFragment : Fragment() {
    private var _binding: FragmentScreenStartBinding? = null
    private val binding get() = _binding!!

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
}
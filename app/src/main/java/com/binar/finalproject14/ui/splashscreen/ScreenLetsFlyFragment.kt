package com.binar.finalproject14.ui.splashscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentScreenLetsFlyBinding
import com.binar.finalproject14.databinding.FragmentSplashScreenBinding

class ScreenLetsFlyFragment : Fragment() {
    private var _binding: FragmentScreenLetsFlyBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScreenLetsFlyBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnNext.setOnClickListener(){
            findNavController().navigate(R.id.action_screenLetsFlyFragment_to_screenStartFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
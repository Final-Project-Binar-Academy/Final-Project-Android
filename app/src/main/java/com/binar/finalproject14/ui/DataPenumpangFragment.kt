package com.binar.finalproject14.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentAboutBinding
import com.binar.finalproject14.databinding.FragmentDataPenumpangBinding
import com.binar.finalproject14.databinding.FragmentDetailBinding

class DataPenumpangFragment : Fragment() {
    private var _binding: FragmentDataPenumpangBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDataPenumpangBinding.inflate(inflater, container, false)
        return binding.root
    }

}
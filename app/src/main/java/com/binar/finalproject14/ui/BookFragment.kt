package com.binar.finalproject14.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.finalproject14.MainActivity
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentAboutBinding
import com.binar.finalproject14.databinding.FragmentBookBinding

class BookFragment : Fragment() {
    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).binding.navBook.visibility = View.VISIBLE
        (activity as MainActivity).binding.navHome.visibility = View.GONE
    }
}
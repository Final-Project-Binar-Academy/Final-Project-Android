package com.binar.finalproject14.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentAboutBinding
import com.binar.finalproject14.databinding.FragmentWhislistBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase


class WhislistFragment : Fragment() {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentWhislistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics

        _binding = FragmentWhislistBinding.inflate(inflater, container, false)
        return binding.root
    }

}
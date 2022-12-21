package com.binar.finalproject14.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.MainActivity
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentAboutBinding
import com.binar.finalproject14.databinding.FragmentTripUpcomingBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class TripUpcomingFragment : Fragment() {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentTripUpcomingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics

        _binding = FragmentTripUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as MainActivity).binding.navHome.visibility = View.VISIBLE

        binding.past.setOnClickListener{
            findNavController().navigate(R.id.action_tripUpcomingFragment_to_pastFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }
}
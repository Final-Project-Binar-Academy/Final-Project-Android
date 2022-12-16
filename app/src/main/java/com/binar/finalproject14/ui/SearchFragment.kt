package com.binar.finalproject14.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.binar.finalproject14.adapter.AirportAdapter
import com.binar.finalproject14.data.api.response.airport.DataAirport
import com.binar.finalproject14.databinding.FragmentSearchBinding
import com.binar.finalproject14.viewmodel.AirportViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class SearchFragment : Fragment() {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var airportViewModel: AirportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics

        airportViewModel = ViewModelProvider(this)[AirportViewModel::class.java]

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter: AirportAdapter by lazy {
            AirportAdapter {

            }
        }

        binding.apply {
            airportViewModel.getDataAirport()
            airportViewModel.getLiveDataAirport().observe(viewLifecycleOwner){
                if (it != null){
                    adapter.setData(it.data as List<DataAirport>)
                }
                else {

                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

}
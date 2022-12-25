package com.binar.finalproject14.ui

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.adapter.AirportAdapter
import com.binar.finalproject14.data.api.response.airport.DataAirport
import com.binar.finalproject14.databinding.FragmentListViewBinding
import com.binar.finalproject14.viewmodel.AirportViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class ListViewFragment : Fragment() {
    private var _binding: FragmentListViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var airportViewModel: AirportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        airportViewModel = ViewModelProvider(this)[AirportViewModel::class.java]

        airportViewModel.getCityAirport()
        airportViewModel.LiveDataCityAirport.observe(viewLifecycleOwner) {
            if (it != null) {
                val adapter = AirportAdapter(requireContext(), it as ArrayList<DataAirport>)
                binding.listView.adapter = adapter
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
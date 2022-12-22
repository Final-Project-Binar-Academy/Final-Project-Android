package com.binar.finalproject14.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject14.MainActivity
import com.binar.finalproject14.R
import com.binar.finalproject14.adapter.FlightAdapter
import com.binar.finalproject14.data.api.response.ticket.DataFlight
import com.binar.finalproject14.databinding.FragmentSearchBinding
import com.binar.finalproject14.viewmodel.FlightViewModel
import com.binar.finalproject14.viewmodel.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var flightViewModel: FlightViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as MainActivity).binding.navHome.visibility = View.VISIBLE

        flightViewModel = ViewModelProvider(this)[FlightViewModel::class.java]

        val adapter: FlightAdapter by lazy {
            FlightAdapter {

            }
        }
        binding.apply {
            flightViewModel.getDataFlight()
            flightViewModel.getLiveDataFlight().observe(viewLifecycleOwner){
                if (it != null){
                    adapter.setData(it.data as List<DataFlight>)
                }else{
                    Snackbar.make(binding.root, "Data Gagal Dimuat", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(requireContext(),
                                R.color.button
                            ))
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        .show()
                }
            }
            rvPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvPost.adapter = adapter
        }

        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }

}
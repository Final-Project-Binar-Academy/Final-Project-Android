package com.binar.finalproject14.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.finalproject14.R
import com.binar.finalproject14.adapter.AirportAdapter
import com.binar.finalproject14.data.api.response.airport.DataAirport
import com.binar.finalproject14.databinding.FragmentListViewBinding
import com.binar.finalproject14.viewmodel.AirportViewModel
import com.binar.finalproject14.viewmodel.CityViewModel
import com.binar.finalproject14.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListViewFragment : Fragment() {
    private var _binding: FragmentListViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var airportViewModel: AirportViewModel
    private lateinit var cityViewModel: CityViewModel
    private val args: ListViewFragmentArgs by navArgs()

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
        cityViewModel = ViewModelProvider(this)[CityViewModel::class.java]

        airportViewModel.getCityAirport()
        airportViewModel.LiveDataCityAirport.observe(viewLifecycleOwner) {
            if (it != null) {
                val adapter = AirportAdapter(requireContext(), it as ArrayList<DataAirport>)
                binding.listView.adapter = adapter

                binding.editText.addTextChangedListener(object : TextWatcher {
                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        // Call back the Adapter with current character to Filter
                        adapter.filter.filter(s.toString())
                    }

                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun afterTextChanged(s: Editable) {}
                })

                binding.listView.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                    val airport = adapter.getItem(position)

                    if (args.myArg == "departure"){
                        cityViewModel.getIsDeparture().observe(viewLifecycleOwner){
                            cityViewModel.removeDeparture()
                            cityViewModel.saveDeparture(airport?.city.toString(), airport?.cityCode.toString(), true)
                        }
                    }
                    if (args.myArg == "destination"){
                        cityViewModel.getIsDestination().observe(viewLifecycleOwner){
                            cityViewModel.removeDestination()
                            cityViewModel.saveDestination(airport?.city.toString(), airport?.cityCode.toString(), true)
                        }
                    }
                    Navigation.findNavController(view).navigate(R.id.action_listViewFragment_to_homeFragment)
                })
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
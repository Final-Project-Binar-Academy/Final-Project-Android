package com.binar.finalproject14.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentAboutBinding
import com.binar.finalproject14.databinding.FragmentDataPenumpangBinding
import com.binar.finalproject14.databinding.FragmentDetailBinding
import com.binar.finalproject14.viewmodel.FlightViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DataPenumpangFragment : Fragment() {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentDataPenumpangBinding? = null
    private val binding get() = _binding!!
    private lateinit var flightViewModel: FlightViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics

        _binding = FragmentDataPenumpangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        flightViewModel = ViewModelProvider(this)[FlightViewModel::class.java]
        getTipeTraveller()
        getTipeId()
        getTicket()
        getBirthday()
        binding.btnBooking.setOnClickListener{
            findNavController().navigate(R.id.action_dataPenumpangFragment_to_rincianPembayaranFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getBirthday() {val materialDateBuilder: MaterialDatePicker.Builder<*> =
        MaterialDatePicker.Builder.datePicker()

        materialDateBuilder.setTitleText("SELECT A DATE")
        val materialDatePicker = materialDateBuilder.build()

        binding.birth.setOnClickListener(
            View.OnClickListener {
                materialDatePicker.show(parentFragmentManager, "MATERIAL_DATE_PICKER")
            })
        materialDatePicker.addOnPositiveButtonClickListener {
            binding.birth.text = materialDatePicker.headerText
        }
    }

    private fun getTipeId() {
        val tipeId = resources.getStringArray(R.array.select_id)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, tipeId)
        binding.typeId.setAdapter(arrayAdapter)
    }

    private fun getTicket() {
        val id = arguments?.getInt("id")
        if (id != null){
            flightViewModel.getFlightDetail(id)
            flightViewModel.flightDetail.observe(viewLifecycleOwner) {
                binding.apply {
                    if (it != null) {
                        var simpleDateFormat = SimpleDateFormat("LLL dd")
                        var departure : Date? = it.data?.departureDate
                        var departure_date = simpleDateFormat.format(departure?.time).toString()

                        var arrival : Date? = it.data?.arrivalDate
                        var arrivalDate = simpleDateFormat.format(arrival?.time).toString()

                        binding.departureDate.text = departure_date
                        binding.departureTime.text = it.data?.departureTime.toString()
                        binding.codeIataFrom.text = it.data?.origin?.cityCode.toString()
                        binding.city1.text = it.data?.origin?.city.toString()
                        binding.kelas.text = it.data?.classX.toString()
                        binding.arrivalDate.text = arrivalDate
                        binding.arrivalTime.text = it.data?.arrivalTime.toString()
                        binding.codeIataTo.text = it.data?.destination?.cityCode.toString()
                        binding.city2.text = it.data?.destination?.city.toString()
                        binding.company.text = it.data?.airplane?.company?.companyName
                        binding.btnKelas.text = it.data?.classX
                        binding.price.text = it.data?.price.toString()
                    }
                }
            }
        }
    }

    private fun getTipeTraveller() {
        val tipeTraveller = resources.getStringArray(R.array.tipe_penumpang)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, tipeTraveller)
        binding.actvTipePenumpang.setAdapter(arrayAdapter)
    }

}
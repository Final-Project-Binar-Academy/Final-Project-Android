package com.binar.finalproject14.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.R
import com.binar.finalproject14.data.api.response.transaction.add.AddTransaction
import com.binar.finalproject14.databinding.FragmentAboutBinding
import com.binar.finalproject14.databinding.FragmentDataPenumpangBinding
import com.binar.finalproject14.databinding.FragmentDetailBinding
import com.binar.finalproject14.viewmodel.FlightViewModel
import com.binar.finalproject14.viewmodel.TransactionViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DataPenumpangFragment : Fragment() {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentDataPenumpangBinding? = null
    private val binding get() = _binding!!
    private lateinit var flightViewModel: FlightViewModel
    private lateinit var transactionViewModel : TransactionViewModel

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
        transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class.java]
        addAirport()
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
    private fun addAirport(){
        binding.btnBooking.setOnClickListener {
//            val tGo = arguments?.getInt("id")
            val tGo = binding.ticketGo.text.toString().toInt()
            val tBack = binding.ticketBack.text.toString().toInt()
            val fName = binding.firstname1.text.toString()
            val lName = binding.lastname1.text.toString()
            val nIK = binding.etPhone1.text.toString()
            val tripId = binding.numberID1.text.toString().toInt()
            val birth = binding.birth.text.toString()
            transactionViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                if (tGo != null) {
                    transactionViewModel.addTransaction(tGo, tBack, tripId, fName, lName, nIK, birth,"Bearer $it")
                }
                Log.d("tgo", tGo.toString())
            }
            Toast.makeText(requireContext(), "Add Success", Toast.LENGTH_SHORT).show()
            transactionViewModel.add.observe(viewLifecycleOwner) {
                val bundle = Bundle()
                val tripId = it?.data?.tripId.toString().toInt()
                bundle.putInt("tripId", tripId)
                findNavController().navigate(
                    R.id.action_dataPenumpangFragment_to_rincianPembayaranFragment,
                    bundle
                )
            }
        }
    }
}
package com.binar.finalproject14.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentTiketBinding
import com.binar.finalproject14.viewmodel.TransactionViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TiketFragment : Fragment() {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentTiketBinding? = null
    private val binding get() = _binding!!
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics
        transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class.java]

        _binding = FragmentTiketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        transactionViewModel.getDataStoreToken().observe(viewLifecycleOwner){
            transactionViewModel.getId().observe(viewLifecycleOwner){id ->
                transactionViewModel.getTransactionId(id, "Bearer $it")
            }
        }

        transactionViewModel.transaction.observe(viewLifecycleOwner){
            binding.cityCodeOrigin.text = it?.data?.go?.origin?.cityCode.toString()
            binding.cityOrigin.text = it?.data?.go?.origin?.city.toString()
            binding.cityCodeDestination.text = it?.data?.go?.destination?.cityCode.toString()
            binding.cityDestination.text = it?.data?.go?.destination?.city.toString()

            binding.kelas.text = it?.data?.go?.classX.toString()
            binding.txtPassenger.text = it?.data?.passenger?.firstName.toString()
            binding.codeFlight.text = it?.data?.go?.code.toString()
            binding.txtClass.text = it?.data?.go?.classX.toString()
            binding.cityCodeOrigin2.text = it?.data?.go?.origin?.cityCode.toString()
            binding.cityCodeDestination2.text = it?.data?.go?.destination?.cityCode.toString()
            binding.txtDate.text = it?.data?.go?.departureDate.toString()
            binding.txtAirplane.text = it?.data?.go?.airplane?.airplaneName.toString()
            binding.txtPrice.text = it?.data?.go?.price.toString()

            if (it?.data?.typeTrip?.type == "Round Trip") {
                binding.ticketBack.visibility = View.VISIBLE
                binding.kelasBack.text = it.data?.back?.classX.toString()
                binding.txtPassengerBack.text = it.data?.passenger?.firstName.toString()
                binding.codeFlightBack.text = it.data?.back?.code.toString()
                binding.txtClassBack.text = it.data?.back?.classX.toString()
                binding.cityCodeOriginBack.text = it.data?.back?.origin?.cityCode.toString()
                binding.cityCodeDestinationBack.text = it.data?.back?.destination?.cityCode.toString()
                binding.txtDateBack.text = it.data?.back?.departureDate.toString()
                binding.txtAirplaneBack.text = it.data?.back?.airplane?.airplaneName.toString()
                binding.txtPriceBack.text = it.data?.back?.price.toString()

            }
        }

        binding.back.setOnClickListener(){
            findNavController().navigate(R.id.action_tiketFragment_to_pastFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }


}
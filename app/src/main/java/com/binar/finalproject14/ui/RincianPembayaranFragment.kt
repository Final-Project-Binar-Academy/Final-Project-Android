package com.binar.finalproject14.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentRincianPembayaranBinding
import com.binar.finalproject14.viewmodel.NotifViewModel
import com.binar.finalproject14.viewmodel.PaymentViewModel
import com.binar.finalproject14.viewmodel.TransactionViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RincianPembayaranFragment : Fragment() {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentRincianPembayaranBinding? = null
    private val binding get() = _binding!!
    private var payment: Int = 0
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var notifViewModel: NotifViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics
        notifViewModel = ViewModelProvider(this)[NotifViewModel::class.java]
        transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class.java]
        paymentViewModel = ViewModelProvider(this)[PaymentViewModel::class.java]

        _binding = FragmentRincianPembayaranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        transactionTrip()
        paymentMethod()

        binding.btnNext.setOnClickListener {
            paymentViewModel.getDataStoreToken().observe(viewLifecycleOwner){
                paymentViewModel.getId().observe(viewLifecycleOwner){id ->
                    paymentViewModel.updatePayment(id, payment,"Bearer $it")
                    notifViewModel.getTotalNotif().observe(viewLifecycleOwner) {
                        val total = it+1
                        notifViewModel.saveNotif("payment", total)
                    }
                }
            }
            findNavController().navigate(R.id.action_rincianPembayaranFragment_to_prosesPembayaranFragment)
//            if (payment == 1) {
//                findNavController().navigate(R.id.action_rincianPembayaranFragment_to_payCardFragment)
//            } else if (payment == 2) {
//                findNavController().navigate(R.id.action_rincianPembayaranFragment_to_payEwalletFragment)
//            } else if (payment == 3) {
//                findNavController().navigate(R.id.action_rincianPembayaranFragment_to_payQrisFragment)
//            } else {
//                Snackbar.make(binding.root, "Pilih metode pembayaran anda", Snackbar.LENGTH_SHORT)
//                    .setBackgroundTint(
//                        ContextCompat.getColor(
//                            requireContext(),
//                            R.color.basic
//                        )
//                    )
//                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
//                    .show()
//            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun paymentMethod() {
        binding.radioGroup.setOnCheckedChangeListener { radioGroup, optionId ->
            run {
                when (optionId) {
                    R.id.bankBRI -> {
                        payment = 1
                    }
                    R.id.bankMandiri -> {
                        payment = 2
                    }
                    R.id.bankBCA -> {
                        payment = 3
                    }
                    R.id.bankBNI -> {
                        payment = 4
                    }
                    R.id.gopay -> {
                        payment = 5
                    }
                    R.id.dana -> {
                        payment = 6
                    }
                    R.id.ovo -> {
                        payment = 7
                    }
                }
            }
        }
    }

    private fun transactionTrip() {
        transactionViewModel.getDataStoreToken().observe(viewLifecycleOwner){
            val tripId = arguments?.getInt("tripId")
            Log.d("tripId", tripId.toString())
            transactionViewModel.getId().observe(viewLifecycleOwner){id ->
                transactionViewModel.getTransactionId(id, "Bearer $it")
            }
        }
        transactionViewModel.transaction.observe(viewLifecycleOwner) {
            binding.cityCodeOri.text = it?.data?.go?.origin?.cityCode.toString()
            binding.cityOri.text = it?.data?.go?.origin?.city.toString()
            binding.cityCodeDestination.text = it?.data?.go?.destination?.cityCode.toString()
            binding.cityDestination.text = it?.data?.go?.destination?.city.toString()
            binding.goDepartureTime.text = it?.data?.go?.departureTime.toString()
            binding.goDepartureDate.text = it?.data?.go?.departureDate.toString()
            binding.goArrivalTime.text = it?.data?.go?.arrivalTime.toString()
            binding.goArrivalDate.text = it?.data?.go?.arrivalDate.toString()
            binding.goClass.text = it?.data?.go?.classX.toString()
            binding.goPassenger.text =
                it?.data?.passenger?.firstName.toString() + " " + it?.data?.passenger?.lastName.toString()
            binding.goCompany.text = it?.data?.go?.airplane?.company?.companyName.toString()
            binding.priceFare.text = "IDR. " + it?.data?.totalPrice.toString()
            binding.totalFare.text = "IDR. " + it?.data?.totalPrice.toString()
        }
    }
}
package com.binar.finalproject14.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentAboutBinding
import com.binar.finalproject14.databinding.FragmentRincianPembayaranBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class RincianPembayaranFragment : Fragment() {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentRincianPembayaranBinding? = null
    private val binding get() = _binding!!
    private var payment: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics

        _binding = FragmentRincianPembayaranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        paymentMethod()

        binding.btnNext.setOnClickListener{
            if (payment == "bank"){
                findNavController().navigate(R.id.action_rincianPembayaranFragment_to_payCardFragment)
            }
            else if (payment == "ewallet"){
                findNavController().navigate(R.id.action_rincianPembayaranFragment_to_payEwalletFragment)
            }
            else if (payment == "qris"){
                findNavController().navigate(R.id.action_rincianPembayaranFragment_to_payQrisFragment)
            }
            else {
                Snackbar.make(binding.root, "Pilih metode pembayaran anda", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(
                        ContextCompat.getColor(requireContext(),
                        R.color.basic
                    ))
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .show()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun paymentMethod() {
        binding.radioGroup.setOnCheckedChangeListener { radioGroup, optionId ->
            run {
                when (optionId) {
                    R.id.bank -> {
                        payment = "bank"
                    }
                    R.id.ewallet -> {
                        payment = "ewallet"
                    }
                    R.id.qris -> {
                        payment = "qris"
                    }
                }
            }
        }
    }

}

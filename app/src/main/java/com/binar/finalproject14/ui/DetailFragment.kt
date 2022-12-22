package com.binar.finalproject14.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.binar.finalproject14.MainActivity
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentAboutBinding
import com.binar.finalproject14.databinding.FragmentDetailBinding
import com.binar.finalproject14.viewmodel.FlightViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDragHandleView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : DialogFragment() {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var flightViewModel: FlightViewModel

    override fun onStart() {
        super.onStart()
        dialog!!.window
            ?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        dialog!!.window?.setGravity(Gravity.BOTTOM)
        dialog!!.window
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        flightViewModel = ViewModelProvider(this)[FlightViewModel::class.java]

        val id = arguments?.getInt("id")

        if (id != null) {
            flightViewModel.getFlightDetail(id)
        }
        flightViewModel.flightDetail.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    codeDeparture.text = it.data?.origin?.cityCode.toString()
                    codeDestination.text = it.data?.destination?.cityCode.toString()
                    company.text = it.data?.airplane?.company?.companyName.toString()
                    airplane.text = it.data?.airplane?.airplaneName.toString()
                    kelas.text = it.data?.classX.toString()
                    ticketCode.text = it.data?.code.toString()
                    departureDate.text = it.data?.departureDate.toString()
                    departureTime.text = it.data?.departureTime.toString()
                    destinationDate.text = it.data?.arrivalDate.toString()
                    destinationTime.text = it.data?.arrivalTime.toString()
                    airportDeparture.text = it.data?.origin?.airportName.toString()
                    airportDestination.text = it.data?.destination?.airportName.toString()
                    price.text = it.data?.price.toString()
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

}
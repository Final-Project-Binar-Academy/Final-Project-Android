package com.binar.finalproject14.ui

import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.MainActivity
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentHomeBinding
import com.binar.finalproject14.viewmodel.HomeViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var viewModel : HomeViewModel
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var oneway: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        analytics = Firebase.analytics

        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activateOneway()

        binding.txtOneway.setOnClickListener{
            oneway = true
            activateOneway()
        }

        binding.txtRoundTrip.setOnClickListener{
            oneway = false
            activateRoundTrip()
        }

        (activity as MainActivity).binding.navHome.visibility = View.VISIBLE

        binding.btnSearch.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setUsername()
    }

    private fun activateOneway() {
        val materialDateBuilder: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()

        materialDateBuilder.setTitleText("SELECT A DATE")
        val materialDatePicker = materialDateBuilder.build()
        binding.btnDepartureDate.setOnClickListener(
            View.OnClickListener {
                materialDatePicker.show(parentFragmentManager, "MATERIAL_DATE_PICKER")
            })
        materialDatePicker.addOnPositiveButtonClickListener {
            binding.date1.text = materialDatePicker.headerText
        }
        binding.txtOneway.setTextColor(resources.getColor(R.color.basic))
        binding.txtReturn.visibility = View.INVISIBLE
        binding.date2.visibility = View.INVISIBLE
        binding.txtRoundTrip.setTextColor(Color.BLACK)

    }

    private fun activateRoundTrip() {
        val materialDateBuilder: MaterialDatePicker.Builder<*> =
        MaterialDatePicker.Builder.datePicker()

        materialDateBuilder.setTitleText("SELECT A DATE")
        val materialDatePicker = materialDateBuilder.build()

        binding.btnDestinationDate.setOnClickListener(
            View.OnClickListener {
                materialDatePicker.show(parentFragmentManager, "MATERIAL_DATE_PICKER")
            })
        materialDatePicker.addOnPositiveButtonClickListener {
            binding.date2.text = materialDatePicker.headerText
        }
        binding.txtOneway.setTextColor(Color.BLACK)
        binding.txtRoundTrip.setTextColor(resources.getColor(R.color.basic))
        binding.txtReturn.visibility = View.VISIBLE
        binding.date2.visibility = View.VISIBLE

    }

    private fun setUsername() {
        viewModel.getDataStoreUsername().observe(viewLifecycleOwner){
            binding.txtHi.text = "Hi, $it"
        }
    }

}
package com.binar.finalproject14.ui

import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject14.MainActivity
import com.binar.finalproject14.R
import com.binar.finalproject14.adapter.AirportAdapter
import com.binar.finalproject14.adapter.FlightAdapter
import com.binar.finalproject14.adapter.InfoAdapter
import com.binar.finalproject14.data.api.response.Article
import com.binar.finalproject14.data.api.response.airport.DataAirport
import com.binar.finalproject14.data.api.response.ticket.DataFlight
import com.binar.finalproject14.databinding.FragmentHomeBinding
import com.binar.finalproject14.viewmodel.*
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var viewModel : HomeViewModel
    private lateinit var profileViewModel : ProfileViewModel
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var oneway: Boolean = true
    private lateinit var infoViewModel: InfoViewModel
    private lateinit var airportViewModel: AirportViewModel
    private lateinit var cityViewModel: CityViewModel

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
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        infoViewModel = ViewModelProvider(this)[InfoViewModel::class.java]
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        airportViewModel = ViewModelProvider(this)[AirportViewModel::class.java]
        cityViewModel = ViewModelProvider(this)[CityViewModel::class.java]

        activateOneway()
        getInfo()
        getListView()

        binding.txtOneway.setOnClickListener{
            oneway = true
            activateOneway()
        }

        binding.txtRoundTrip.setOnClickListener{
            oneway = false
            activateRoundTrip()
        }

        binding.txtTraveller.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_travellerDialogFragment)
        }

        val kelas = resources.getStringArray(R.array.kelas)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, kelas)
        binding.autoCompleteTextView3.setAdapter(arrayAdapter)

        (activity as MainActivity).binding.navHome.visibility = View.VISIBLE

        binding.btnSearch.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        binding.btnDeparture.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToListViewFragment("departure"))
        }

        binding.btnDestination.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToListViewFragment("destination"))
        }

        setUsername()
        setImageProfile()
    }

    private fun getListView() {
        cityViewModel.getCityDeparture().observe(viewLifecycleOwner){
            if (it != null){
                binding.txtCityDeparture.text = it
            }
        }
        cityViewModel.getCityCodeDeparture().observe(viewLifecycleOwner){
            if (it != null){
                binding.txtCitycodeDeparture.text = it
            }
        }
        cityViewModel.getCityDestination().observe(viewLifecycleOwner){
            if (it != null){
                binding.txtCityDestination.text = it
            }
        }
        cityViewModel.getCityCodeDestination().observe(viewLifecycleOwner){
            if (it != null){
                binding.txtCitycodeDestination.text = it
            }
        }
    }

    private fun getInfo() {
        val adapter: InfoAdapter by lazy {
            InfoAdapter {

            }
        }

        binding.apply {
            infoViewModel.getDataInfo()
            infoViewModel.getLiveDataInfo().observe(viewLifecycleOwner){
                if (it != null){
                    adapter.setData(it.articles as List<Article>)
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
            rvImportant.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvImportant.adapter = adapter
        }
    }

    private fun setImageProfile() {
        profileViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            profileViewModel.getUserProfile("Bearer $it")
        }
        profileViewModel.user.observe(viewLifecycleOwner){
            Glide.with(requireContext())
                .load(it?.data?.avatar)
                .circleCrop()
                .into(binding.profileImage)
        }
    }

    private fun activateOneway() {
        val materialDateBuilder: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()

        materialDateBuilder.setTitleText("SELECT A DATE")
        val materialDatePicker = materialDateBuilder.build()
        binding.date1.setOnClickListener(
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

        binding.date2.setOnClickListener(
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
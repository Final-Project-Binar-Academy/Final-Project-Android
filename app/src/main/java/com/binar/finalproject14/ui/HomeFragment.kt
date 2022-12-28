package com.binar.finalproject14.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject14.MainActivity
import com.binar.finalproject14.R
import com.binar.finalproject14.adapter.InfoAdapter
import com.binar.finalproject14.data.api.response.Article
import com.binar.finalproject14.databinding.FragmentHomeBinding
import com.binar.finalproject14.viewmodel.*
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var viewModel : HomeViewModel
    private lateinit var profileViewModel : ProfileViewModel
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var oneway: Boolean = true
    private lateinit var departureDate: String
    private lateinit var returnDate: String
    private lateinit var infoViewModel: InfoViewModel
    private lateinit var airportViewModel: AirportViewModel
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        infoViewModel = ViewModelProvider(this)[InfoViewModel::class.java]
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        airportViewModel = ViewModelProvider(this)[AirportViewModel::class.java]
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        analytics = Firebase.analytics

        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).binding.navHome.visibility = View.VISIBLE

        activateOneway()
        getInfo()
        getListView()
        getKelas()
        getTraveller()

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

        binding.btnDeparture.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToListViewFragment("departure"))
        }

        binding.btnDestination.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToListViewFragment("destination"))
        }

        setUsername()
        setImageProfile()
    }


//        searchViewModel.getLiveDataSearch().observe(viewLifecycleOwner){
//            if (it != null){
//            }else{
//                Snackbar.make(binding.root, "Data Gagal Dimuat", Snackbar.LENGTH_SHORT)
//                    .setBackgroundTint(
//                        ContextCompat.getColor(requireContext(),
//                            R.color.button
//                        ))
//                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
//                    .show()
//            }
//        }

    private fun getTraveller() {
        val traveller = arguments?.getString("traveller")
        if (traveller != null) {
            binding.txtTraveller.text = traveller
        }

    }

    private fun getKelas() {
        val arrayClass = resources.getStringArray(R.array.kelas)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, arrayClass)
        binding.actvClass.setAdapter(arrayAdapter)
    }

    private fun getListView() {
        val bund = Bundle()
        searchViewModel.getCityDeparture().observe(viewLifecycleOwner){
            if (it != null){
                binding.txtCityDeparture.text = it
                bund.putString("departureCity", it)
            }
        }
        searchViewModel.getCityCodeDeparture().observe(viewLifecycleOwner){
            if (it != null){
                binding.txtCitycodeDeparture.text = it
            }
        }
        searchViewModel.getCityDestination().observe(viewLifecycleOwner){
            if (it != null){
                binding.txtCityDestination.text = it
                bund.putString("destinationCity", it)
            }
        }
        searchViewModel.getCityCodeDestination().observe(viewLifecycleOwner){
            if (it != null){
                binding.txtCitycodeDestination.text = it
            }
        }
        searchViewModel.getDepartureDate().observe(viewLifecycleOwner){
            if (it != null){
                binding.date1.text = it
                bund.putString("departureDate", it)
            }
        }
        searchViewModel.getReturnDate().observe(viewLifecycleOwner){
            if (it != null){
                binding.date2.text = it
                bund.putString("returnDate", it)
            }
        }
        binding.btnSearch.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment, bund)
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
            val simpleFormat = SimpleDateFormat("yyyy-MM-dd")
            departureDate = simpleFormat.format(Date(materialDatePicker.headerText))
            binding.date1.text = materialDatePicker.headerText
            searchViewModel.getIsDepartureDate().observe(viewLifecycleOwner){
                if (it != null){
                    searchViewModel.removeDepartureDate()
                }
                searchViewModel.saveDepartureDate(departureDate)
            }
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
            val simpleFormat = SimpleDateFormat("yyyy-MM-dd")
            returnDate = simpleFormat.format(Date(materialDatePicker.headerText))
            binding.date2.text = materialDatePicker.headerText
            searchViewModel.getIsReturnDate().observe(viewLifecycleOwner){
                if (it != null){
                    searchViewModel.removeReturnDate()
                }
                searchViewModel.saveReturnDate(returnDate)
            }
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
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject14.MainActivity
import com.binar.finalproject14.R
import com.binar.finalproject14.adapter.SearchBackAdapter
import com.binar.finalproject14.adapter.SearchGoAdapter
import com.binar.finalproject14.data.api.response.search.TicketBack
import com.binar.finalproject14.data.api.response.search.TicketGo
import com.binar.finalproject14.databinding.FragmentSearchBinding
import com.binar.finalproject14.viewmodel.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchGoAdapter.ListSearchGoInterface, SearchBackAdapter.ListSearchBackInterface {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var departureDate: String
    private lateinit var returnDate: String
    private lateinit var departureCity: String
    private lateinit var destinationCity: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as MainActivity).binding.navHome.visibility = View.VISIBLE

        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        getData()

        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun getData() {
        departureDate = "2022-12-25"
        departureCity = arguments?.getString("departureCity").toString()
        destinationCity = arguments?.getString("destinationCity").toString()
        returnDate = "2022-12-25"

        searchViewModel.getIsOneway().observe(viewLifecycleOwner) {
            if (it == true) {
                Log.d("tipe", it.toString())
                getOneway()
            } else {
                Log.d("tipe", it.toString())
                getRoundtrip()
            }
        }
    }

    fun getOneway(){
        binding.btnRoundtrip.visibility = View.INVISIBLE
        searchViewModel.getDepartureDate().observe(viewLifecycleOwner){
            binding.date.text = it
        }
        val adapter = SearchGoAdapter(this)
        binding.apply {
            searchViewModel.getDataSearch(departureDate, departureCity, destinationCity, returnDate)
            searchViewModel.getLiveDataSearch().observe(viewLifecycleOwner) {
                if (it != null) {
                    adapter.setData(it.ticketGo as List<TicketGo>)
                } else {
                    Snackbar.make(binding.root, "Data Gagal Dimuat", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.button
                            )
                        )
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        .show()
                }
            }
            rvPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvPost.adapter = adapter
        }
    }

    fun getTicketBack(){
        val adapter = SearchBackAdapter(this)

        binding.apply {
            searchViewModel.getDataSearch(
                departureDate,
                departureCity,
                destinationCity,
                returnDate
            )
            searchViewModel.getLiveDataSearch().observe(viewLifecycleOwner) {
                if (it != null) {
                    adapter.setData(it.ticketBack as List<TicketBack>)
                } else {
                    Snackbar.make(binding.root, "Data Gagal Dimuat", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.button
                            )
                        )
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        .show()
                }
            }
            rvPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvPost.adapter = adapter
        }
    }

    fun getRoundtrip(){
        binding.btnRoundtrip.visibility = View.VISIBLE

        val id_oneway = arguments?.getInt("id_oneway")
        Log.d("idoneway2", id_oneway.toString())
        if (id_oneway != 0){
            Log.d("oneway", id_oneway.toString())
            searchViewModel.getReturnDate().observe(viewLifecycleOwner){
                binding.date.text = it
            }
            getTicketBack()
        } else {
            searchViewModel.getDepartureDate().observe(viewLifecycleOwner) {
                binding.date.text = it
            }
            val adapter = SearchGoAdapter(this)
            binding.apply {
                searchViewModel.getDataSearch(
                    departureDate,
                    departureCity,
                    destinationCity,
                    returnDate
                )
                searchViewModel.getLiveDataSearch().observe(viewLifecycleOwner) {
                    if (it != null) {
                        adapter.setData(it.ticketGo as List<TicketGo>)
                    } else {
                        Snackbar.make(binding.root, "Data Gagal Dimuat", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.button
                                )
                            )
                            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                            .show()
                    }
                }
                rvPost.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rvPost.adapter = adapter
            }
        }

    }

    override fun onItemClickBack(id: Int) {
        var bund = Bundle()
        val id_go = arguments?.getInt("id_oneway")
        bund.putInt("id_ticket_back", id)
        if (id_go != null) {
            bund.putInt("id_ticket_go", id_go)
        }
        findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bund)
    }

    override fun onItemClickGo(id: Int) {
        var bund = Bundle()
        bund.putInt("id_ticket_go", id)
        bund.putString("departure_date", departureDate)
        bund.putString("departure_city", departureCity)
        bund.putString("destination_city", destinationCity)
        bund.putString("return_date", returnDate)

        findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bund)
    }

}
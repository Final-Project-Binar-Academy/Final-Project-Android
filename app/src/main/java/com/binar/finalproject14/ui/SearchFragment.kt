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
import com.binar.finalproject14.adapter.SearchGoAdapter
import com.binar.finalproject14.data.api.response.search.DataSearch
import com.binar.finalproject14.data.api.response.search.TicketGo
import com.binar.finalproject14.data.model.SearchData
import com.binar.finalproject14.data.model.WishlistData
import com.binar.finalproject14.databinding.FragmentSearchBinding
import com.binar.finalproject14.viewmodel.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchGoAdapter.ListSearchInterface {
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
        departureDate = arguments?.getString("departureDate").toString()
        departureCity = arguments?.getString("departureCity").toString()
        destinationCity = arguments?.getString("destinationCity").toString()
        returnDate = arguments?.getString("returnDate").toString()

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

    override fun onItemClick(id: Int) {
        var bund = Bundle()
        bund.putInt("id", id)
        findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bund)
    }

}
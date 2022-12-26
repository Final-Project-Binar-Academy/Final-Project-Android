package com.binar.finalproject14.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject14.MainActivity
import com.binar.finalproject14.R
import com.binar.finalproject14.adapter.TransactionAdapter
import com.binar.finalproject14.data.api.response.transaction.history.Data
import com.binar.finalproject14.data.api.service.filter.ApiClient
import com.binar.finalproject14.data.api.service.filter.ApiHelper
import com.binar.finalproject14.databinding.FragmentPastBinding
import com.binar.finalproject14.utils.UserDataStoreManager
import com.binar.finalproject14.viewmodel.TransactionFilterViewModel
import com.binar.finalproject14.viewmodel.factory.TransactionFilterViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class PastFragment : Fragment(), TransactionAdapter.ListTransactionInterface {
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var viewModel: TransactionFilterViewModel
    private lateinit var pref: UserDataStoreManager
    private var _binding: FragmentPastBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics
        pref = UserDataStoreManager(requireContext())
        viewModel = ViewModelProvider(
            this,TransactionFilterViewModelFactory(ApiHelper(ApiClient.instance),pref)
        )[TransactionFilterViewModel::class.java]
        _binding = FragmentPastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as MainActivity).binding.navHome.visibility = View.VISIBLE

        binding.upcoming.setOnClickListener {
            findNavController().navigate(R.id.action_pastFragment_to_tripUpcomingFragment)
        }
        rcyView("pending")
        super.onViewCreated(view, savedInstanceState)

    }

    fun rcyView(status: String) {
        val adapter: TransactionAdapter by lazy {
            TransactionAdapter {

            }
        }
        binding.apply {

            viewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                viewModel.getFilterTransaction("Bearer $it", status)
            }

            viewModel.getTransactionFilter().observe(viewLifecycleOwner) {
                if (it != null) {
                    adapter.setData(it.data as List<Data>)
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

    override fun onItemClick(TransactionDetail: Data) {
        TODO("Not yet implemented")
    }

}
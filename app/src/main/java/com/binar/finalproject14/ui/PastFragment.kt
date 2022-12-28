package com.binar.finalproject14.ui

import android.annotation.SuppressLint
import android.graphics.Typeface
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
import com.binar.finalproject14.data.utils.UserDataStoreManager
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

    private var isSuccess = false
    private var isPending = false
    private var isCanceled = false

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

        rcyView("success")
        binding.successTransaction.setOnClickListener {
            rcyView("success")
            binding.successTransaction.typeface = Typeface.DEFAULT_BOLD
            binding.pendingTransaction.typeface = Typeface.DEFAULT
            binding.cancelTransaction.typeface = Typeface.DEFAULT
        }
        binding.pendingTransaction.setOnClickListener {
            rcyView("pending")
            binding.pendingTransaction.typeface = Typeface.DEFAULT_BOLD
            binding.successTransaction.typeface = Typeface.DEFAULT
            binding.cancelTransaction.typeface = Typeface.DEFAULT
        }
        binding.cancelTransaction.setOnClickListener {
            rcyView("canceled")
            binding.cancelTransaction.typeface = Typeface.DEFAULT_BOLD
            binding.successTransaction.typeface = Typeface.DEFAULT
            binding.pendingTransaction.typeface = Typeface.DEFAULT
        }

        super.onViewCreated(view, savedInstanceState)

    }

    private fun rcyView(status: String) {
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
                    setImgOff()
                } else {
                    setImg()
                }
            }
            rvPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvPost.adapter = adapter
        }

    }

    fun setImg(){
        binding.noTicket.visibility = View.VISIBLE
        binding.rvPost.visibility = View.GONE
    }
    fun setImgOff(){
        binding.noTicket.visibility = View.GONE
        binding.rvPost.visibility = View.VISIBLE
    }

    override fun onItemClick(TransactionDetail: Data) {
        TODO("Not yet implemented")
    }

}
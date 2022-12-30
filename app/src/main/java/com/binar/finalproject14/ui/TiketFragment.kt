package com.binar.finalproject14.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject14.adapter.TicketBackAdapter
import com.binar.finalproject14.adapter.TicketGoAdapter
import com.binar.finalproject14.data.api.response.transaction.Data
import com.binar.finalproject14.databinding.FragmentTiketBinding
import com.binar.finalproject14.viewmodel.TransactionViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TiketFragment : Fragment(), TicketGoAdapter.ListTicketInterface, TicketBackAdapter.ListTicketInterface {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentTiketBinding? = null
    private val binding get() = _binding!!
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics
        transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class.java]

        _binding = FragmentTiketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        transactionViewModel.getDataStoreToken().observe(viewLifecycleOwner){
            transactionViewModel.getId().observe(viewLifecycleOwner){id ->
                transactionViewModel.getTransactionId(id, "Bearer $it")
            }
        }

        transactionViewModel.transaction.observe(viewLifecycleOwner){
            binding.cityCodeOrigin.text = it?.data?.go?.origin?.cityCode.toString()
            binding.cityOrigin.text = it?.data?.go?.origin?.city.toString()
            binding.cityCodeDestination.text = it?.data?.go?.destination?.cityCode.toString()
            binding.cityDestination.text = it?.data?.go?.destination?.city.toString()

            if (it?.data?.typeTrip?.type == "Round Trip"){
                val adapterBack = TicketBackAdapter(this)
                binding.apply {
                    adapterBack.setData(it.data as List<Data>)
                    rvPostBack.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    rvPostBack.adapter = adapterBack
                }
            }
            val adapter = TicketGoAdapter(this)
            binding.apply {
                if (it != null) {
                    adapter.setData(it.data as List<Data>)
                } else {

                }
                rvPostGo.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rvPostGo.adapter = adapter
            }

        }

        binding.back.setOnClickListener(){
//            findNavController().navigate(R.id.tic)
        }
        super.onViewCreated(view, savedInstanceState)
    }


}
package com.binar.finalproject14.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject14.R
import com.binar.finalproject14.adapter.NotificationAdapter
import com.binar.finalproject14.data.api.response.notification.DataNotif
import com.binar.finalproject14.databinding.FragmentNotificationBinding
import com.binar.finalproject14.viewmodel.NotifViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment(), NotificationAdapter.ListNotificationInterface {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private lateinit var notifViewModel: NotifViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics
        notifViewModel = ViewModelProvider(this)[NotifViewModel::class.java]

        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = NotificationAdapter(this)
        notifViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            notifViewModel.getDataNotification("Bearer $it")
        }
        binding.apply {
            notifViewModel.getLiveDataNotification().observe(viewLifecycleOwner) {
                if (it != null) {
                    adapter.setData(it.data as List<DataNotif>)
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
                rvPost.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rvPost.adapter = adapter
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun notif(id: Int) {

    }


}
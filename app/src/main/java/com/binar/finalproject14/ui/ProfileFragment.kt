package com.binar.finalproject14.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.MainActivity
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentProfileBinding
import com.binar.finalproject14.viewmodel.NotifViewModel
import com.binar.finalproject14.viewmodel.ProfileViewModel
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var viewModelNotif: NotifViewModel
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        viewModelNotif = ViewModelProvider(this)[NotifViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).binding.navHome.visibility = View.VISIBLE

        viewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            viewModel.getUserProfile("Bearer $it")
        }

        viewModel.user.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    username.text =
                        it.data?.firstName.toString() + " " + it.data?.lastName.toString()
                    etFirstName.setText(it.data?.firstName.toString())
                    etLastName.setText(it.data?.lastName.toString())
                    etAddress.setText(it.data?.address.toString())
                    etPhone.setText(it.data?.phoneNumber.toString())
                    Glide.with(requireContext())
                        .load(it.data?.avatar)
                        .circleCrop()
                        .into(binding.profileImage)

                }
            }
        }

        cekNotif()

//        var count: LiveData<Int> = viewModelNotif.getInitialCount()
//        count.observe(viewLifecycleOwner) {
//            if (it == 0) {
//                binding.notifCount.visibility = View.GONE
//            } else if (it > 0){
//                binding.apply {
//                    notifCount.text = it.toString()
//                    notifCount.visibility = View.VISIBLE
//                }
//            }else{
//                binding.notifCount.text=getString(R.string.overCount)
//            }
//        }

        binding.btnNotification.setOnClickListener(){
            viewModelNotif.removeNotif()
            findNavController().navigate(R.id.action_profileFragment_to_notificationFragment)
        }

        binding.btnEdit.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
            viewModelNotif.getCurrentCount()
        }

        logout()
    }

    private fun cekNotif(){
        viewModelNotif.getIsNotif().observe(viewLifecycleOwner){
            if (it == true){
                viewModelNotif.getDataStoreToken().observe(viewLifecycleOwner) {
                    viewModelNotif.getDataNotification("Bearer $it")
                }
                viewModelNotif.getTotalNotif().observe(viewLifecycleOwner){
                    binding.notifCount.text = it.toString()
                }
            } else {
                binding.notifCount.visibility = View.GONE
            }
        }
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            viewModel.removeIsLoginStatus()
            viewModel.removeId()
            viewModel.removeUsername()
            viewModel.removeToken()
            viewModel.getDataStoreIsLogin().observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
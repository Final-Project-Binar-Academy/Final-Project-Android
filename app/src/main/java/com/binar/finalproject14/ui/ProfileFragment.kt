package com.binar.finalproject14.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentAboutBinding
import com.binar.finalproject14.databinding.FragmentPastBinding
import com.binar.finalproject14.databinding.FragmentProfileBinding
import com.binar.finalproject14.viewmodel.ProfileViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        viewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            viewModel.getUserProfile("Bearer $it")
        }
        binding.btnUpdate.setOnClickListener {
            val fName = binding.etFirstName.text.toString().trim()
            val lName = binding.etLastName.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()

            viewModel.saveUsername(fName)
            viewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                viewModel.updateUser(fName, lName, address,phone, "Bearer $it")
            }
            Toast.makeText(requireContext(), "Update Success",Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }
        viewModel.user.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    etFirstName.setText(it.data?.firstName.toString())
                    etLastName.setText(it.data?.lastName.toString())
                    etAddress.setText(it.data?.address.toString())
                    etPhone.setText(it.data?.phoneNumber.toString())
                }
            }
        }
        logout()
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
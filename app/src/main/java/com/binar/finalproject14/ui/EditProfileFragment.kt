package com.binar.finalproject14.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.MainActivity
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentEditProfileBinding
import com.binar.finalproject14.databinding.FragmentProfileBinding
import com.binar.finalproject14.viewmodel.ProfileViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : DialogFragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        dialog!!.window
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics

        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).binding.navHome.visibility = View.VISIBLE

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
            Toast.makeText(requireContext(), "Update Success", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }
        viewModel.user.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    etFirstName.setText(it.firstName)
                    etLastName.setText(it.lastName)
                    etAddress.setText(it.address)
                    etPhone.setText(it.phoneNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
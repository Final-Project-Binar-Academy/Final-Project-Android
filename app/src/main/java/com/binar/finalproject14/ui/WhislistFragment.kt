package com.binar.finalproject14.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject14.MainActivity
import com.binar.finalproject14.R
import com.binar.finalproject14.adapter.WishlistAdapter
import com.binar.finalproject14.databinding.FragmentAboutBinding
import com.binar.finalproject14.databinding.FragmentWhislistBinding
import com.binar.finalproject14.viewmodel.WishlistViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WhislistFragment : Fragment() {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentWhislistBinding? = null
    private val binding get() = _binding!!
    private lateinit var wishlistViewModel: WishlistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics

        _binding = FragmentWhislistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as MainActivity).binding.navHome.visibility = View.VISIBLE
        wishlistViewModel = ViewModelProvider(this)[WishlistViewModel::class.java]

        wishlistViewModel.getAllWishlistFlight()
        wishlistViewModel.list_wishlist.observe(viewLifecycleOwner){
            if(it == null){
                Toast.makeText(requireContext(), "Belum ada ticket", Toast.LENGTH_SHORT).show()
            }
            else {
                binding.rvPost.layoutManager = LinearLayoutManager(requireContext())
                binding.rvPost.setHasFixedSize(false)
                binding.rvPost.adapter = WishlistAdapter(it)
            }
        }


        super.onViewCreated(view, savedInstanceState)
    }

}
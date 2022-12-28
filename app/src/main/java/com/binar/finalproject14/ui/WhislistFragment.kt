package com.binar.finalproject14.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject14.MainActivity
import com.binar.finalproject14.R
import com.binar.finalproject14.adapter.WishlistAdapter
import com.binar.finalproject14.data.model.WishlistData
import com.binar.finalproject14.databinding.FragmentWhislistBinding
import com.binar.finalproject14.viewmodel.WishlistViewModel
import com.google.android.material.snackbar.Snackbar
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
    private lateinit var data: WishlistData

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
                WishlistAdapter(it).notifyDataSetChanged()

                ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val deletedCourse: WishlistData =
                            it[viewHolder.adapterPosition]

                        data = WishlistData(deletedCourse.id_wishlist, deletedCourse.departure_date,
                        deletedCourse.departure_time, deletedCourse.codeIataFrom,
                        deletedCourse.city_origin, deletedCourse.duration,
                        deletedCourse.kelas, deletedCourse.arrival_date,
                        deletedCourse.arrival_time, deletedCourse.codeIataTo,
                        deletedCourse.city_destination, deletedCourse.company,
                        deletedCourse.price)

                        wishlistViewModel.removeWishlist(data)
                        wishlistViewModel.deleteWishlist.observe(viewLifecycleOwner) {
                            if (it != null) {
                                Snackbar.make(binding.root, "Ticket dihapus dari Wishlist", Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(
                                        androidx.core.content.ContextCompat.getColor(requireContext(),
                                            R.color.basic
                                        ))
                                    .setTextColor(androidx.core.content.ContextCompat.getColor(requireContext(), R.color.white))
                                    .show()
                            }
                        }
                        WishlistAdapter(it).notifyItemRemoved(viewHolder.adapterPosition)
                        WishlistAdapter(it).notifyItemChanged(viewHolder.adapterPosition)
                    }
                }).attachToRecyclerView(binding.rvPost)
            }
        }


        super.onViewCreated(view, savedInstanceState)
    }

}
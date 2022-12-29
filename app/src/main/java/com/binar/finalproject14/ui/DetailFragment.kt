package com.binar.finalproject14.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.R
import com.binar.finalproject14.data.model.WishlistData
import com.binar.finalproject14.databinding.FragmentDetailBinding
import com.binar.finalproject14.viewmodel.FlightViewModel
import com.binar.finalproject14.viewmodel.SearchViewModel
import com.binar.finalproject14.viewmodel.WishlistViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DetailFragment : DialogFragment() {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var isClicked = false
    private lateinit var flightViewModel: FlightViewModel
    private lateinit var wishlistViewModel: WishlistViewModel
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var data : WishlistData

    override fun onStart() {
        super.onStart()
        dialog!!.window
            ?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        dialog!!.window?.setGravity(Gravity.BOTTOM)
        dialog!!.window
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        flightViewModel = ViewModelProvider(this)[FlightViewModel::class.java]
        wishlistViewModel = ViewModelProvider(this)[WishlistViewModel::class.java]

        getDetail()
        cekWishlist()


        binding.btnWishlist.setOnClickListener {
            if (!isClicked) {
                isClicked = true
                addWishlist(data)
                binding.btnWishlist.setBackgroundColor(resources.getColor(R.color.wishlist))

            } else {
                isClicked = false
                deleteWishlist(data)
                binding.btnWishlist.setBackgroundColor(Color.TRANSPARENT)
            }
        }

        booking()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun booking(){
        binding.btnBook.setOnClickListener{
            searchViewModel.getIsOneway().observe(viewLifecycleOwner){
                val id_ticket_go = arguments?.getInt("id_ticket_go")
                val bund = Bundle()
                if (id_ticket_go != null) {
                    bund.putInt("id_oneway", id_ticket_go)
                }
                Log.d("idoneway", id_ticket_go.toString())
                if (it == true){
                    findNavController().navigate(R.id.action_detailFragment_to_dataPenumpangFragment, bund)
                } else {
                    val departureDate = arguments?.getString("departure_date")
                    val departureCity = arguments?.getString("departure_city")
                    val destinationCity = arguments?.getString("destination_city")
                    val returnDate = arguments?.getString("return_date")
                    val id_ticket_back = arguments?.getInt("id_ticket_back")
                    if (id_ticket_back != null){
                        bund.putInt("id_ticket_back", id_ticket_back)
                        findNavController().navigate(R.id.action_detailFragment_to_dataPenumpangFragment, bund)
                    } else {
                        bund.putString("departure_date", departureDate)
                        bund.putString("departure_city", departureCity)
                        bund.putString("destination_city", destinationCity)
                        bund.putString("return_date", returnDate)
                        findNavController().navigate(R.id.searchFragment, bund)
                    }
                }
            }
        }
    }

    private fun cekWishlist() {
        val id = arguments?.getInt("id_ticket_go")
        if (id != null) {
            wishlistViewModel.isWishlist(id)
            wishlistViewModel.wishlist.observe(viewLifecycleOwner) {
                if (it != null) {
                    if (it) {
                        isClicked = true
                        binding.btnWishlist.setBackgroundColor(resources.getColor(R.color.wishlist))
                    } else {
                        isClicked = false
                        binding.btnWishlist.setBackgroundColor(Color.TRANSPARENT)
                    }
                }
            }
        }
    }

    private fun getDetail() {
        val id = arguments?.getInt("id_ticket_go")

        if (id != null) {
            flightViewModel.getFlightDetail(id)
            flightViewModel.flightDetail.observe(viewLifecycleOwner) {
                binding.apply {
                    if (it != null) {
                        var simpleDateFormat = SimpleDateFormat("LLL dd")
                        var departure : Date? = it.data?.departureDate
                        var departure_date = simpleDateFormat.format(departure?.time).toString()

                        var arrival : Date? = it.data?.arrivalDate
                        var arrivalDate = simpleDateFormat.format(arrival?.time).toString()

                        codeDeparture.text = it.data?.origin?.cityCode.toString()
                        codeDestination.text = it.data?.destination?.cityCode.toString()
                        company.text = it.data?.airplane?.company?.companyName.toString()
                        airplane.text = it.data?.airplane?.airplaneName.toString()
                        kelas.text = it.data?.classX.toString()
                        ticketCode.text = "Ticket Code : " + it.data?.code.toString()
                        departureDate.text = departure_date
                        departureTime.text = it.data?.departureTime.toString()
                        destinationDate.text = arrivalDate
                        destinationTime.text = it.data?.arrivalTime.toString()
                        airportDeparture.text = it.data?.origin?.airportName.toString()
                        airportDestination.text = it.data?.destination?.airportName.toString()
                        price.text = it.data?.price.toString()
                        var duration = 0
                        data =
                            it.data?.id?.let { it1 ->
                                WishlistData(
                                    it1, departure_date,
                                    it.data?.departureTime.toString(), it.data?.origin?.cityCode.toString(),
                                    it.data?.origin?.city.toString(), duration.toString(), it.data?.classX.toString(),
                                    arrivalDate, it.data?.arrivalTime.toString(),
                                    it.data?.destination?.cityCode.toString(), it.data?.destination?.city.toString(),
                                    it.data?.airplane?.company?.companyName.toString(), it.data?.price.toString())
                            }!!
                    }
                }
            }
        }
    }

    private fun addWishlist(wishlistFlight: WishlistData) {
        wishlistViewModel.addWishlist(wishlistFlight)
        wishlistViewModel.wishlistFlight.observe(viewLifecycleOwner) {
            if (it != null) {
                Snackbar.make(binding.root, "Ticket ditambahkan ke Wishlist", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(
                        ContextCompat.getColor(requireContext(),
                        R.color.basic
                    ))
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .show()
            }
        }
    }

    private fun deleteWishlist(favFlight: WishlistData) {
        wishlistViewModel.removeWishlist(favFlight)
        wishlistViewModel.deleteWishlist.observe(viewLifecycleOwner) {
            if (it != null) {
                Snackbar.make(binding.root, "Ticket dihapus dari Wishlist", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(
                        ContextCompat.getColor(requireContext(),
                        R.color.basic
                    ))
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .show()
            }
        }
    }

}
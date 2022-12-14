package com.binar.finalproject14.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentRincianPembayaranBinding
import com.binar.finalproject14.viewmodel.NotifViewModel
import com.binar.finalproject14.viewmodel.PaymentViewModel
import com.binar.finalproject14.viewmodel.TransactionViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RincianPembayaranFragment : Fragment() {
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentRincianPembayaranBinding? = null
    private val binding get() = _binding!!
    private var payment: Int = 0
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var notifViewModel: NotifViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics
        notifViewModel = ViewModelProvider(this)[NotifViewModel::class.java]
        transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class.java]
        paymentViewModel = ViewModelProvider(this)[PaymentViewModel::class.java]

        _binding = FragmentRincianPembayaranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        transactionTrip()
        paymentMethod()
        binding.btnNext.setOnClickListener {
            paymentViewModel.getDataStoreToken().observe(viewLifecycleOwner){
                paymentViewModel.getId().observe(viewLifecycleOwner){id ->
                    paymentViewModel.updatePayment(id, payment,"Bearer $it")
                    notifViewModel.saveNotif("payment")
                    makeStatusNotification("Transaksi anda berhasil. Selamat menikmati penerbangan anda", requireContext())

                }
            }
            findNavController().navigate(R.id.action_rincianPembayaranFragment_to_prosesPembayaranFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    val CHANNEL_ID = "PAYMENT"
    val CHANNEL_NAME = "Payment Successfull"
    val CHANNEL_DESCRIPTION = "PAYMENT NOTIFICATION"

    fun makeStatusNotification(message: String, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            channel.description = CHANNEL_DESCRIPTION

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }

        // Create the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(CHANNEL_NAME)
            .setContentText(message)
            .setSmallIcon(R.drawable.logo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        // Show the notification
        NotificationManagerCompat.from(context).notify(1, builder.build())
    }


    private fun paymentMethod() {
        binding.radioGroup.setOnCheckedChangeListener { radioGroup, optionId ->
            run {
                when (optionId) {
                    R.id.bankBRI -> {
                        payment = 1
                    }
                    R.id.bankMandiri -> {
                        payment = 2
                    }
                    R.id.bankBCA -> {
                        payment = 3
                    }
                    R.id.bankBNI -> {
                        payment = 4
                    }
                    R.id.gopay -> {
                        payment = 5
                    }
                    R.id.dana -> {
                        payment = 6
                    }
                    R.id.ovo -> {
                        payment = 7
                    }
                }
            }
        }
    }


    private fun transactionTrip() {
        transactionViewModel.getDataStoreToken().observe(viewLifecycleOwner){
            val tripId = arguments?.getInt("tripId")
            Log.d("tripId", tripId.toString())
            transactionViewModel.getId().observe(viewLifecycleOwner){id ->
                transactionViewModel.getTransactionId(id, "Bearer $it")
            }
        }
        transactionViewModel.transaction.observe(viewLifecycleOwner) {
            binding.cityCodeOri.text = it?.data?.go?.origin?.cityCode.toString()
            binding.cityOri.text = it?.data?.go?.origin?.city.toString()
            binding.cityCodeDestination.text = it?.data?.go?.destination?.cityCode.toString()
            binding.cityDestination.text = it?.data?.go?.destination?.city.toString()
            binding.goDepartureTime.text = it?.data?.go?.departureTime.toString()
            binding.goDepartureDate.text = it?.data?.go?.departureDate.toString()
            binding.goArrivalTime.text = it?.data?.go?.arrivalTime.toString()
            binding.goArrivalDate.text = it?.data?.go?.arrivalDate.toString()
            binding.goClass.text = it?.data?.go?.classX.toString()
            binding.goPassenger.text =
                it?.data?.passenger?.firstName.toString() + " " + it?.data?.passenger?.lastName.toString()
            binding.goCompany.text = it?.data?.go?.airplane?.company?.companyName.toString()
            binding.priceFare.text = "IDR. " + it?.data?.totalPrice.toString()
            binding.totalFare.text = "IDR. " + it?.data?.totalPrice.toString()
        }
    }
}
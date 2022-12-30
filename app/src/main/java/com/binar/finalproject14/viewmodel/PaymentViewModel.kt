package com.binar.finalproject14.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.binar.finalproject14.data.api.request.AddTransaction
import com.binar.finalproject14.data.api.response.payment.PaymentRequest
import com.binar.finalproject14.data.api.response.payment.PaymentResponse
import com.binar.finalproject14.data.api.response.transaction.add.TransactionResponse
import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.data.utils.UserDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val client: UserApi,
    private val pref: UserDataStoreManager,
    application: Application
) : AndroidViewModel(application) {
    private val _update: MutableLiveData<PaymentResponse?> = MutableLiveData()
    val add: LiveData<PaymentResponse?> get() = _update

    fun updatePayment(
        transactionId : Int,
        paymentId : Int,
        token: String
    ) {
        client.updatePayment(token, PaymentRequest(transactionId, paymentId))
            .enqueue(object : Callback<PaymentResponse> {
                override fun onResponse(
                    call: Call<PaymentResponse>,
                    response: Response<PaymentResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _update.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                    _update.postValue(null)
                }
            })
    }
    fun getDataStoreToken(): LiveData<String> {
        return pref.getToken.asLiveData()
    }
    fun getId(): LiveData<Int> {
        return pref.getTransactionId.asLiveData()
    }
}
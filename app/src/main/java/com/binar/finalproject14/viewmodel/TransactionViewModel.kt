package com.binar.finalproject14.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.binar.finalproject14.data.api.request.AddTransaction
import com.binar.finalproject14.data.api.response.transaction.add.TransactionResponse
import com.binar.finalproject14.data.api.response.transaction.history.TransactionHistory
import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.data.utils.UserDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val client: UserApi,
    private val pref: UserDataStoreManager,
    application: Application
) : AndroidViewModel(application) {
    private val _add: MutableLiveData<TransactionResponse?> = MutableLiveData()
    val add: LiveData<TransactionResponse?> get() = _add

    fun addTransaction(
        ticketGo : Int,
        ticketBack: Int,
        tripId: Int,
        firstName: String,
        lastName: String,
        nIK: String,
        brithDate: String,
        token: String
    ) {
        client.addTransaction(token, AddTransaction(brithDate, firstName, lastName, nIK, ticketBack, ticketGo, tripId))
            .enqueue(object : Callback<TransactionResponse> {
                override fun onResponse(
                    call: Call<TransactionResponse>,
                    response: Response<TransactionResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _add.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                    _add.postValue(null)
                }
            })
    }

    private val _transaction: MutableLiveData<TransactionResponse?> = MutableLiveData()
    val transaction: LiveData<TransactionResponse?> get() = _transaction

    fun getTransactionTrip(
        tripId: Int?,
        token: String
    ) {
        client.getTransactionTrip(token, tripId)
            .enqueue(object : Callback<TransactionResponse> {
                override fun onResponse(
                    call: Call<TransactionResponse>,
                    response: Response<TransactionResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _transaction.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                    _transaction.postValue(null)
                }
            })
    }

    fun getDataStoreToken(): LiveData<String> {
        return pref.getToken.asLiveData()
    }
}
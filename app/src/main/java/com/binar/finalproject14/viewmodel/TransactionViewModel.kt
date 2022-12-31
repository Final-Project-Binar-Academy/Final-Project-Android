package com.binar.finalproject14.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.binar.finalproject14.data.api.request.AddTransaction
import com.binar.finalproject14.data.api.response.transaction.CancelResponse
import com.binar.finalproject14.data.api.response.transaction.add.TransactionResponse
import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.data.utils.UserDataStoreManager
import com.binar.finalproject14.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val client: UserApi,
    private val repository: MainRepository,
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
                            repository.addTransaction(token, AddTransaction(brithDate, firstName, lastName, nIK, ticketBack, ticketGo, tripId))
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

    fun getTransactionId(
        id: Int?,
        token: String
    ) {
        client.getTransactionId(token, id)
            .enqueue(object : Callback<TransactionResponse> {
                override fun onResponse(
                    call: Call<TransactionResponse>,
                    response: Response<TransactionResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            repository.getTransactionId(token, id)
                            _transaction.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                    _transaction.postValue(null)
                }
            })
    }

    private val _cancelTransaction: MutableLiveData<CancelResponse?> = MutableLiveData()
    val cancelDataTransaction: LiveData<CancelResponse?> get() = _cancelTransaction

    fun cancelTransaction(
        id: Int?,
        token: String
    ) {
        client.cancelTransaction(token, id)
            .enqueue(object : Callback<CancelResponse> {
                override fun onResponse(
                    call: Call<CancelResponse>,
                    response: Response<CancelResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            repository.cancelTransaction(token, id)
                            _cancelTransaction.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<CancelResponse>, t: Throwable) {
                    _cancelTransaction.postValue(null)
                }
            })
    }

    fun getDataStoreToken(): LiveData<String> {
        return pref.getToken.asLiveData()
    }
    fun saveTransactionId(id: Int) {
        viewModelScope.launch {
            pref.saveTransactionId(id)
        }
    }

    fun getId(): LiveData<Int> {
        return pref.getTransactionId.asLiveData()
    }
}
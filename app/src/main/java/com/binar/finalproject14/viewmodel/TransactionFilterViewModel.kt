package com.binar.finalproject14.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.binar.finalproject14.data.api.response.transaction.history.TransactionFilterResponse
import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.data.api.service.filter.ApiClient
import com.binar.finalproject14.repository.TransactionFilterRepository
import com.binar.finalproject14.utils.UserDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class TransactionFilterViewModel (
    private val transactionRepository: TransactionFilterRepository,
    private val pref: UserDataStoreManager
) : ViewModel() {

    private val _transaction: MutableLiveData<TransactionFilterResponse?> = MutableLiveData()
    fun getTransactionFilter(): MutableLiveData<TransactionFilterResponse?> = _transaction

    fun getFilterTransaction(token:String,status: String){
        ApiClient.instance.getTransactionFilter(token,status)
            .enqueue(object : Callback<TransactionFilterResponse> {
                override fun onResponse(
                    call: Call<TransactionFilterResponse>,
                    response: Response<TransactionFilterResponse>
                ) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _transaction.postValue(responseBody)
                    }else {
                        _transaction.postValue(null)
                        Log.d("notSuccess", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<TransactionFilterResponse>, t: Throwable) {
                    _transaction.postValue(null)
                    Log.d("Failed", t.message.toString())
                }

            })
    }
    fun getDataStoreToken(): LiveData<String> {
        return pref.getToken.asLiveData()
    }

}
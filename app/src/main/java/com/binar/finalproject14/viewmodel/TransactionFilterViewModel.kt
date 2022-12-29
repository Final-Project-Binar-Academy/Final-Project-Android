package com.binar.finalproject14.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.binar.finalproject14.data.api.response.transaction.history.TransactionHistory
import com.binar.finalproject14.data.api.service.filter.ApiClient
import com.binar.finalproject14.repository.TransactionFilterRepository
import com.binar.finalproject14.data.utils.UserDataStoreManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TransactionFilterViewModel (
    private val transactionRepository: TransactionFilterRepository,
    private val pref: UserDataStoreManager
) : ViewModel() {

    private val _transaction: MutableLiveData<TransactionHistory?> = MutableLiveData()
    fun getTransactionFilter(): MutableLiveData<TransactionHistory?> = _transaction

    fun getFilterTransaction(token:String,status: String){
        ApiClient.instance.getTransactionFilter(token,status)
            .enqueue(object : Callback<TransactionHistory> {
                override fun onResponse(
                    call: Call<TransactionHistory>,
                    response: Response<TransactionHistory>
                ) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _transaction.postValue(responseBody)
                    }else {
                        _transaction.postValue(null)
                        Log.d("notSuccess", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<TransactionHistory>, t: Throwable) {
                    _transaction.postValue(null)
                    Log.d("Failed", t.message.toString())
                }

            })
    }
    fun getDataStoreToken(): LiveData<String> {
        return pref.getToken.asLiveData()
    }

}
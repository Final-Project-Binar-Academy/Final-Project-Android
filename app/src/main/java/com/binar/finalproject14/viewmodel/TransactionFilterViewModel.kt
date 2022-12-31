package com.binar.finalproject14.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.binar.finalproject14.data.api.response.transaction.history.TransactionHistory
import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.data.utils.UserDataStoreManager
import com.binar.finalproject14.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class TransactionFilterViewModel @Inject constructor(
    private val client: UserApi,
    private val repository: MainRepository,
    private val pref: UserDataStoreManager,
    application: Application
) : AndroidViewModel(application) {

    private val _transaction: MutableLiveData<TransactionHistory?> = MutableLiveData()
    fun getTransactionFilter(): MutableLiveData<TransactionHistory?> = _transaction

    fun getFilterTransaction(token:String,status: String){
        client.getTransactionFilter(token,status)
            .enqueue(object : Callback<TransactionHistory> {
                override fun onResponse(
                    call: Call<TransactionHistory>,
                    response: Response<TransactionHistory>
                ) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        repository.getTransactionFilter(token, status)
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
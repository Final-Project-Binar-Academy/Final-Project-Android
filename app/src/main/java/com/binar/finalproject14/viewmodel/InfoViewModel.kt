package com.binar.finalproject14.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.binar.finalproject14.data.api.response.InfoResponse
import com.binar.finalproject14.data.api.service.NewsApi
import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.repository.InfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val client: NewsApi,
    private val infoRepository: InfoRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _info: MutableLiveData<InfoResponse?> = MutableLiveData()
    fun getLiveDataInfo(): MutableLiveData<InfoResponse?> = _info

    fun getDataInfo() {
        client.getInfo()
            .enqueue(object : Callback<InfoResponse> {
                override fun onResponse(
                    call: Call<InfoResponse>,
                    response: Response<InfoResponse>
                ) {
                    if (response.isSuccessful) {
                        infoRepository.getInfo()
                        _info.postValue(response.body())
                    } else {
                        _info.postValue(null)
                        Log.d("notSuccess", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<InfoResponse>, t: Throwable) {
                    _info.postValue(null)
                    Log.d("Failed", t.message.toString())
                }

            })
    }
}
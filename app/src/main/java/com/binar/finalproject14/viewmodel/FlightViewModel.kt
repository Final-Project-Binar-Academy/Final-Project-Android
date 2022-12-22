package com.binar.finalproject14.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.finalproject14.data.api.response.ticket.FlightResponse
import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.repository.FlightRepository
import com.binar.finalproject14.utils.UserDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(
    private val client: UserApi,
    private val flightRepository: FlightRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _flight: MutableLiveData<FlightResponse?> = MutableLiveData()
    fun getLiveDataFlight() : MutableLiveData<FlightResponse?> = _flight

    fun getDataFlight() {
        client.getFlight()
            .enqueue(object : Callback<FlightResponse> {
                override fun onResponse(
                    call: Call<FlightResponse>,
                    response: Response<FlightResponse>
                ) {
                    if (response.isSuccessful){
                        flightRepository.getFlight()
                        _flight.postValue(response.body())
                    }else{
                        _flight.postValue(null)
                        Log.d("notSuccess", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<FlightResponse>, t: Throwable) {
                    _flight.postValue(null)
                    Log.d("Failed",t.message.toString())
                }

            })
    }

}
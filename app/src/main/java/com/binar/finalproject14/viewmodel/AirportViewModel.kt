package com.binar.finalproject14.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.binar.finalproject14.data.api.response.airport.AirportResponse
import com.binar.finalproject14.data.api.response.airport.DataAirport
import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(
    private val client: UserApi,
    private val repository: MainRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _airportCity = MutableLiveData<List<DataAirport>?>()
    val LiveDataCityAirport: LiveData<List<DataAirport>?> = _airportCity

    fun getCityAirport(){
        client.getAirport().enqueue(object : Callback<AirportResponse> {
            override fun onResponse(
                call: Call<AirportResponse>,
                response: Response<AirportResponse>
            ) {
                if (response.isSuccessful) {
                    repository.getAirport()
                    _airportCity.postValue(response.body()!!.data)
                }
            }

            override fun onFailure(call: Call<AirportResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure: ${t.message}")
            }
        })
    }

}

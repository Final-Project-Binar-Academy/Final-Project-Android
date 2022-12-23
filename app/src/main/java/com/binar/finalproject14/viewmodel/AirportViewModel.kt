package com.binar.finalproject14.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.binar.finalproject14.data.api.response.airport.AirportResponse
import com.binar.finalproject14.data.api.response.airport.DataAirport
import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.repository.AirportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(
    private val client: UserApi,
    private val airportRepository: AirportRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _airport: MutableLiveData<AirportResponse?> = MutableLiveData()
    val getLiveDataAirport: LiveData<AirportResponse?> get() = _airport

    fun getAirport(){
        client.getAirport()
            .enqueue(object : Callback <AirportResponse> {
                override fun onResponse(
                    call: Call<AirportResponse>,
                    response: Response<AirportResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _airport.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<AirportResponse>, t: Throwable) {
                }
            })
    }

//    private val _airportCity: MutableLiveData<DataAirport?> = MutableLiveData()
//    val getLiveDataAirportCity: LiveData<DataAirport?> get() = _airportCity
//
//    fun getAirportCity(){
//        client.getAirportCity()
//            .enqueue(object : Callback <DataAirport> {
//                override fun onResponse(
//                    call: Call<DataAirport>,
//                    response: Response<DataAirport>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//                        if (responseBody != null) {
//                            _airportCity.postValue(responseBody)
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<DataAirport>, t: Throwable) {
//                }
//            })
//    }

}

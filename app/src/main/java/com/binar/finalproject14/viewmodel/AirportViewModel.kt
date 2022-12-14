package com.binar.finalproject14.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.finalproject14.data.api.response.airport.AirportResponse
import com.binar.finalproject14.data.api.service.AirportApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(
    private val client: AirportApi
    ) : ViewModel() {

    private val _airport: MutableLiveData<AirportResponse?> = MutableLiveData()
    val airport: LiveData<AirportResponse?> get() = _airport


    fun getDataAirport() {
        client.getAirport()
            .enqueue(object : Callback<AirportResponse> {
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

//    fun getDataAirport() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(null))
//        try {
//            emit(Resource.success(data = airportRepository.getUsers()))
//        } catch (exception: Exception) {
//            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
//        }
//    }


}

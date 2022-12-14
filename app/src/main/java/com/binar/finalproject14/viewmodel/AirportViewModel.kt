package com.binar.finalproject14.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.binar.finalproject14.data.api.response.airport.AirportResponse
import com.binar.finalproject14.data.api.service.ApiClient
import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.repository.AirportRepository
import com.example.mvvm_car_chapter6.utils.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class AirportViewModel(private val airportClient: UserApi, private val airportRepository: AirportRepository) : ViewModel() {

    private val liveDataAirportDatabase : MutableLiveData<AirportResponse?> = MutableLiveData()

    fun getLiveDataAirport() : MutableLiveData<AirportResponse?> = liveDataAirportDatabase


    fun getDataAirport() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = airportRepository.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}

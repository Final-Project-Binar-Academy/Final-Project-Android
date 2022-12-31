package com.binar.finalproject14.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.binar.finalproject14.data.api.response.search.DataSearch
import com.binar.finalproject14.data.api.response.search.SearchResponse
import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.data.utils.SearchDatastore
import com.binar.finalproject14.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val client: UserApi,
    private val repository: MainRepository,
    private val pref: SearchDatastore,
    application: Application
) : AndroidViewModel(application) {

    private val _search: MutableLiveData<DataSearch?> = MutableLiveData()
    fun getLiveDataSearch(): MutableLiveData<DataSearch?> = _search

    fun getDataSearch(departureDate: String, originCity: String, destinationCity: String, returnDate: String) {
        client.search(departureDate, originCity, destinationCity, returnDate)
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful) {
                        repository.search(departureDate, originCity, destinationCity, returnDate)
                        _search.postValue(response.body()?.data)
                    } else {
                        _search.postValue(null)
                        Log.d("notSuccess", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    _search.postValue(null)
                    Log.d("Failed", t.message.toString())
                }

            })
    }

    fun saveDeparture(cityDeparture: String, cityCodeDeparture: String, isDeparture: Boolean){
        viewModelScope.launch {
            pref.saveDeparture(cityDeparture, cityCodeDeparture, isDeparture)
        }
    }

    fun saveDepartureDate(departureDate: String){
        viewModelScope.launch {
            pref.saveDepartureDate(departureDate)
        }
    }

    fun saveReturnDate(returnDate: String){
        viewModelScope.launch {
            pref.saveReturnDate(returnDate)
        }
    }

    fun saveDestination(cityDestination: String, cityCodeDestination: String, isDestination: Boolean){
        viewModelScope.launch {
            pref.saveDestination(cityDestination, cityCodeDestination, isDestination)
        }
    }

    fun saveIsOneway(isOneway: Boolean){
        viewModelScope.launch {
            pref.saveIsOneway(isOneway)
        }
    }

    fun getCityDeparture(): LiveData<String> {
        return pref.getCityDeparture.asLiveData()
    }

    fun getCityCodeDeparture(): LiveData<String> {
        return pref.getCityCodeDeparture.asLiveData()
    }

    fun getCityDestination(): LiveData<String> {
        return pref.getCityDestination.asLiveData()
    }

    fun getCityCodeDestination(): LiveData<String> {
        return pref.getCityCodeDestination.asLiveData()
    }

    fun getDepartureDate(): LiveData<String> {
        return pref.getDepartureDate.asLiveData()
    }

    fun getReturnDate(): LiveData<String> {
        return pref.getReturnDate.asLiveData()
    }

    fun getIsDeparture(): LiveData<Boolean> {
        return pref.getIsDeparture.asLiveData()
    }

    fun getIsDestination(): LiveData<Boolean> {
        return pref.getIsDestination.asLiveData()
    }

    fun getIsDepartureDate(): LiveData<Boolean> {
        return pref.getIsDeparture.asLiveData()
    }

    fun getIsReturnDate(): LiveData<Boolean> {
        return pref.getIsDestination.asLiveData()
    }

    fun getIsOneway(): LiveData<Boolean> {
        return pref.getIsOneway.asLiveData()
    }

    fun removeDeparture(){
        viewModelScope.launch {
            pref.removeDeparture()
        }
    }

    fun removeDestination(){
        viewModelScope.launch {
            pref.removeDestination()
        }
    }

    fun removeDepartureDate(){
        viewModelScope.launch {
            pref.removeDepartureDate()
        }
    }

    fun removeReturnDate(){
        viewModelScope.launch {
            pref.removeReturnDate()
        }
    }

    fun removeIsOneway(){
        viewModelScope.launch {
            pref.removeIsOneway()
        }
    }
}
package com.binar.finalproject14.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.binar.finalproject14.utils.CityDatastore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val pref: CityDatastore,
    application: Application
) : AndroidViewModel(application) {

    fun saveDeparture(cityDeparture: String, cityCodeDeparture: String, isDeparture: Boolean){
        viewModelScope.launch {
            pref.saveDeparture(cityDeparture, cityCodeDeparture, isDeparture)
        }
    }

    fun saveDestination(cityDestination: String, cityCodeDestination: String, isDestination: Boolean){
        viewModelScope.launch {
            pref.saveDestination(cityDestination, cityCodeDestination, isDestination)
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

    fun getIsDeparture(): LiveData<Boolean> {
        return pref.getIsDeparture.asLiveData()
    }

    fun getIsDestination(): LiveData<Boolean> {
        return pref.getIsDestination.asLiveData()
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

}
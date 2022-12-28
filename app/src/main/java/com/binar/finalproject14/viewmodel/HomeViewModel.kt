package com.binar.finalproject14.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.binar.finalproject14.data.utils.SearchDatastore
import com.binar.finalproject14.data.utils.UserDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pref: UserDataStoreManager,
    application: Application
) : AndroidViewModel(application) {
    fun getDataStoreUsername(): LiveData<String> {
        return pref.getUsername.asLiveData()
    }


}
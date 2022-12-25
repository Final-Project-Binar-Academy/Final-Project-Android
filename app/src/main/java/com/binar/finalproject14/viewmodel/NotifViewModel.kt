package com.binar.finalproject14.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotifViewModel:ViewModel() {

    private var notifCount = 0
    private var countLiveData= MutableLiveData<Int>()

    fun getInitialCount(): MutableLiveData<Int> {
        countLiveData.value = notifCount
        return countLiveData
    }

    fun getCurrentCount(){
        notifCount+=1
        countLiveData.value=notifCount
    }


}
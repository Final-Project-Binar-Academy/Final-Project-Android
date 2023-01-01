package com.binar.finalproject14.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.binar.finalproject14.data.api.request.NotificationRequest
import com.binar.finalproject14.data.api.response.notification.NotificationCreateResponse
import com.binar.finalproject14.data.api.response.notification.NotificationIdResponse
import com.binar.finalproject14.data.api.response.notification.NotificationResponse
import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.data.utils.NotifDataStore
import com.binar.finalproject14.data.utils.UserDataStoreManager
import com.binar.finalproject14.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NotifViewModel @Inject constructor(
    private val client: UserApi,
    private val pref: UserDataStoreManager,
    private val notifPref: NotifDataStore,
    private val repository: MainRepository,
    application: Application
) : AndroidViewModel(application) {

    private var notifCount = 0
    private var countLiveData= MutableLiveData<Int>()

    fun getInitialCount(): MutableLiveData<Int> {
        countLiveData.value = notifCount
        return countLiveData
    }

    fun getCurrentCount(){
        notifCount += 1
        countLiveData.value=notifCount
    }

    private val _notif: MutableLiveData<NotificationResponse?> = MutableLiveData()
    fun getLiveDataNotification() : MutableLiveData<NotificationResponse?> = _notif

    fun getDataNotification(token: String) {
        client.getNotification(token)
            .enqueue(object : Callback<NotificationResponse> {
                override fun onResponse(
                        call: Call<NotificationResponse>,
                    response: Response<NotificationResponse>
                ) {
                    if (response.isSuccessful){
                        repository.getNotification(token)
                        _notif.postValue(response.body())
                    }else{
                        _notif.postValue(null)
                        Log.d("notSuccess", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                    _notif.postValue(null)
                    Log.d("Failed",t.message.toString())
                }

            })
    }

    private val getDetailNotification: MutableLiveData<NotificationIdResponse?> = MutableLiveData()
    val notifDetail: LiveData<NotificationIdResponse?> get() = getDetailNotification

    fun getNotificationDetail(token: String, id: Int){
        client.getNotificationDetail(token, id)
            .enqueue(object : Callback <NotificationIdResponse> {
                override fun onResponse(
                    call: Call<NotificationIdResponse>,
                    response: Response<NotificationIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            repository.getNotificationDetail(token, id)
                            getDetailNotification.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<NotificationIdResponse>, t: Throwable) {
                }
            })
    }

    private val _notifCreate: MutableLiveData<NotificationCreateResponse?> = MutableLiveData()
    val add: LiveData<NotificationCreateResponse?> get() = _notifCreate

    fun createNotification(
        token: String,
        message: String
    ) {
        client.createNotification(token, NotificationRequest(message))
            .enqueue(object : Callback<NotificationCreateResponse> {
                override fun onResponse(
                    call: Call<NotificationCreateResponse>,
                    response: Response<NotificationCreateResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            repository.createNotification(token, NotificationRequest(message))
                            _notifCreate.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<NotificationCreateResponse>, t: Throwable) {
                    _notifCreate.postValue(null)
                }
            })
    }

    fun getDataStoreToken(): LiveData<String> {
        return pref.getToken.asLiveData()
    }

    fun saveNotif(notif: String, total: Int){
        viewModelScope.launch {
            notifPref.saveNotif(notif, total)
        }
    }

    fun getTotalNotif(): LiveData<Int> {
        return notifPref.getTotalNotif.asLiveData()
    }

    fun getIsNotif(): LiveData<Boolean> {
        return notifPref.getIsNotif.asLiveData()
    }

    fun removeNotif(){
        viewModelScope.launch {
            notifPref.removeNotif()
        }
    }

}
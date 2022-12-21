package com.binar.finalproject14.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.binar.finalproject14.data.api.response.profile.GetUserResponse
import com.binar.finalproject14.data.api.response.profile.ProfileResponse
import com.binar.finalproject14.data.api.response.profile.User
import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.utils.UserDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val client: UserApi,
    private val pref: UserDataStoreManager,
    application: Application
) : AndroidViewModel(application) {
    private val _user: MutableLiveData<GetUserResponse?> = MutableLiveData()
    val user: LiveData<GetUserResponse?> get() = _user

    fun getUserProfile(token : String){
        client.getUserProfile(token)
            .enqueue(object : Callback <GetUserResponse> {
                override fun onResponse(
                    call: Call<GetUserResponse>,
                    response: Response<GetUserResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _user.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                }
            })
    }
    fun updateUser(
        firstName: String,
        lastName: String,
        address: String,
        phoneNumber: Int,
        token: String
    ) {
        client.updateUser(User(firstName, lastName, address, phoneNumber), token)
            .enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                }
            })
    }
    fun getDataStoreToken(): LiveData<String> {
        return pref.getToken.asLiveData()
    }
    fun removeIsLoginStatus() {
        viewModelScope.launch {
            pref.removeIsLoginStatus()
        }
    }
    fun removeUsername() {
        viewModelScope.launch {
            pref.removeUsername()
        }
    }
    fun removeToken() {
        viewModelScope.launch {
            pref.removeToken()
        }
    }

    fun removeId() {
        viewModelScope.launch {
            pref.removeId()
        }
    }
    fun getDataStoreIsLogin(): LiveData<Boolean> {
        return pref.getIsLogin.asLiveData()
    }
    fun saveUsername(fName: String) {
        viewModelScope.launch {
            pref.saveUsername(fName)
        }
    }

}

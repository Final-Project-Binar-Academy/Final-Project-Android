package com.binar.finalproject14.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.*
import com.binar.finalproject14.data.api.response.profile.GetUserResponse
import com.binar.finalproject14.data.api.response.profile.User
import com.binar.finalproject14.data.api.response.user.UpdateImage
import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.data.utils.UserDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    private val _update: MutableLiveData<User?> = MutableLiveData()
    val update: LiveData<User?> get() = _update

    private val _image: MutableLiveData<UpdateImage?> = MutableLiveData()
    val image: LiveData<UpdateImage?> get() = _image
    private var imageUri: Uri? = null

    fun getUserProfile(token: String) {
        client.getUserProfile(token)
            .enqueue(object : Callback<GetUserResponse> {
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
        firstName: RequestBody,
        lastName: RequestBody,
        address: RequestBody,
        phoneNumber: RequestBody,
        token: String
    ) {
        client.updateUser(firstName, lastName, address, phoneNumber, token)
            .enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _update.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    _update.postValue(null)
                }
            })
    }

    fun updateImage(
        image: MultipartBody.Part,
        token: String
    ) {
        client.updateImage(image, token)
            .enqueue(object : Callback<UpdateImage> {
                override fun onResponse(
                    call: Call<UpdateImage>,
                    response: Response<UpdateImage>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _image.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateImage>, t: Throwable) {
                    _image.postValue(null)
                }
            })
    }

    fun getDataStoreToken(): LiveData<String> {
        return pref.getToken.asLiveData()
    }
//    fun getDataStoreImage(): LiveData<String> {
//        return pref.getProfileImage.asLiveData()
//    }

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
//    fun removeImage() {
//        viewModelScope.launch {
//            pref.removeImage()
//        }
//    }

    fun removeId() {
        viewModelScope.launch {
            pref.removeId()
        }
    }

    fun getDataStoreIsLogin(): LiveData<Boolean> {
        return pref.getIsLogin.asLiveData()
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            pref.saveUsername(username)
        }
    }
//    fun saveImage(uri: String) {
//        viewModelScope.launch {
//            pref.saveImage(uri)
//        }
//    }

}

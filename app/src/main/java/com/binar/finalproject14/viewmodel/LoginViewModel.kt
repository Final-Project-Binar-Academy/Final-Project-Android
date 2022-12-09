package com.binar.finalproject14.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.binar.finalproject14.data.api.request.LoginRequest
import com.binar.finalproject14.data.api.response.BaseResponse
import com.binar.finalproject14.data.api.response.AuthResponse
import com.binar.finalproject14.repository.UserRepository
import com.binar.finalproject14.utils.UserDataStoreManager
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val pref: UserDataStoreManager,
    application: Application
) : AndroidViewModel(application) {

    //    val userRepo = UserRepository()
    val loginResult: MutableLiveData<BaseResponse<AuthResponse>> = MutableLiveData()


    fun loginUser(email: String, pwd: String) {

        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(
                    password = pwd,
                    email = email
                )
                val response = userRepo.loginUser(loginRequest = loginRequest)
                if (response?.code() == 200) {
                    loginResult.value = BaseResponse.Success(response.body())
                } else {
                    loginResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
    fun saveIsLoginStatus(status: Boolean) {
        viewModelScope.launch {
            pref.saveIsLoginStatus(status)
        }
    }

    fun getDataStoreIsLogin(): LiveData<Boolean> {
        return pref.getIsLogin.asLiveData()
    }
}
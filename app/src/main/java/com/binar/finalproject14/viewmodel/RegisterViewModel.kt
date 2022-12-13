package com.binar.finalproject14.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.binar.finalproject14.data.api.request.RegisterRequest
import com.binar.finalproject14.data.api.response.BaseResponse
import com.binar.finalproject14.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepo: UserRepository,
    application: Application
) : AndroidViewModel(application) {
    //    val userRepo = UserRepository()
    val registerResult: MutableLiveData<BaseResponse<AuthResponse>> = MutableLiveData()

    fun registerUser(fname: String, lname: String, email: String, pwd: String) {
        registerResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val registerRequest = RegisterRequest(
                    firstName = fname,
                    lastName = lname,
                    password = pwd,
                    email = email
                )
                val response = userRepo.registerUser(registerRequest = registerRequest)
                if (response?.code() == 201) {
                    registerResult.value = BaseResponse.Success(response.body())
                } else {
                    registerResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                registerResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}
package com.binar.finalproject14.repository

import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.data.api.request.LoginRequest
import com.binar.finalproject14.data.api.request.RegisterRequest
import com.binar.finalproject14.data.api.response.AuthResponse
import retrofit2.Response

class UserRepository {

   suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>? {
      return  UserApi.getApi()?.loginUser(loginRequest = loginRequest)
   }

    suspend fun registerUser(registerRequest: RegisterRequest): Response<AuthResponse>? {
        return  UserApi.getApi()?.registerUser(registerRequest = registerRequest)
    }
}
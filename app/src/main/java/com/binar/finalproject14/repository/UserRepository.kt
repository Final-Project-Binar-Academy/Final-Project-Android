package com.binar.finalproject14.repository

import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.data.api.request.LoginRequest
import com.binar.finalproject14.data.api.response.LoginResponse
import retrofit2.Response

class UserRepository {

   suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
      return  UserApi.getApi()?.loginUser(loginRequest = loginRequest)
    }
}
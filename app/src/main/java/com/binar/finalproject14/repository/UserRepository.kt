package com.binar.finalproject14.repository

import com.binar.finalproject14.data.api.request.LoginRequest
import com.binar.finalproject14.data.api.request.RegisterRequest
import com.binar.finalproject14.data.api.service.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun loginUser(loginRequest: LoginRequest) = apiHelper.loginUser(loginRequest = loginRequest)
    suspend fun registerUser(registerRequest: RegisterRequest) = apiHelper.registerUser(registerRequest = registerRequest)
//
//    suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>? {
//        return client.getUserService().loginUser(loginRequest = loginRequest)
//    }
//
//    suspend fun registerUser(registerRequest: RegisterRequest): Response<AuthResponse>? {
//        return client.getUserService().registerUser(registerRequest = registerRequest)
//    }
}
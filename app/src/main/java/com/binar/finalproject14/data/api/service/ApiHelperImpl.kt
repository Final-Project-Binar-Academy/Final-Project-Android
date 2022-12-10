package com.binar.finalproject14.data.api.service

import com.binar.finalproject14.data.api.request.LoginRequest
import com.binar.finalproject14.data.api.request.RegisterRequest
import com.binar.finalproject14.data.api.response.AuthResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: UserApi): ApiHelper {
    override suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>? = apiService.loginUser(loginRequest = loginRequest)
    override suspend fun registerUser(registerRequest: RegisterRequest): Response<AuthResponse>? =apiService.registerUser(registerRequest = registerRequest)
}
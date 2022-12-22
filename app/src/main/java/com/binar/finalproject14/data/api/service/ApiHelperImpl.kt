package com.binar.finalproject14.data.api.service

import com.binar.finalproject14.data.api.request.LoginRequest
import com.binar.finalproject14.data.api.request.RegisterRequest
import com.binar.finalproject14.data.api.response.airport.AirportResponse
import com.binar.finalproject14.data.api.response.auth.AuthResponse
import com.binar.finalproject14.data.api.response.profile.ProfileResponse
import com.binar.finalproject14.data.api.response.ticket.FlightResponse
import com.binar.finalproject14.utils.UserDataStoreManager
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: UserApi,
    private val pref: UserDataStoreManager

): ApiHelper {
    override suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>? = apiService.loginUser(loginRequest = loginRequest)
    override suspend fun registerUser(registerRequest: RegisterRequest): Response<AuthResponse>? = apiService.registerUser(registerRequest = registerRequest)
    override fun getAllFlight(): Call<FlightResponse> = apiService.getFlight()


}
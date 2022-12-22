package com.binar.finalproject14.data.api.service

import com.binar.finalproject14.data.api.request.LoginRequest
import com.binar.finalproject14.data.api.request.RegisterRequest
import com.binar.finalproject14.data.api.response.airport.AirportResponse
import com.binar.finalproject14.data.api.response.auth.AuthResponse
import com.binar.finalproject14.data.api.response.profile.ProfileResponse
import com.binar.finalproject14.data.api.response.ticket.FlightResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiHelper {
    suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>?

    suspend fun registerUser(registerRequest: RegisterRequest): Response<AuthResponse>?

    fun getAllFlight(): Call<FlightResponse>
}

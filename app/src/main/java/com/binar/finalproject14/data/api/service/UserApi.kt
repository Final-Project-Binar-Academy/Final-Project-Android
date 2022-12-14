package com.binar.finalproject14.data.api.service

import com.binar.finalproject14.data.api.request.LoginRequest
import com.binar.finalproject14.data.api.request.RegisterRequest
import com.binar.finalproject14.data.api.response.airport.AirportResponse
import com.binar.finalproject14.data.api.response.auth.AuthResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @POST("/api/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<AuthResponse>

    @POST("/api/auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<AuthResponse>

    @GET("/api/airport")
    fun getAirport() : Response<AirportResponse>

//    companion object {
//        fun getApi(): UserApi? {
//            return ApiClient.client?.create(UserApi::class.java)
//        }
//    }
}
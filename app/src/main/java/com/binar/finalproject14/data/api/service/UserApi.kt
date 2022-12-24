package com.binar.finalproject14.data.api.service

import com.binar.finalproject14.data.api.request.LoginRequest
import com.binar.finalproject14.data.api.request.RegisterRequest
import com.binar.finalproject14.data.api.response.InfoResponse
import com.binar.finalproject14.data.api.response.airport.AirportResponse
import com.binar.finalproject14.data.api.response.auth.AuthResponse
import com.binar.finalproject14.data.api.response.profile.GetUserResponse
import com.binar.finalproject14.data.api.response.profile.ProfileResponse
import com.binar.finalproject14.data.api.response.profile.User
import com.binar.finalproject14.data.api.response.ticket.FlightIdResponse
import com.binar.finalproject14.data.api.response.ticket.FlightResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @POST("/api/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<AuthResponse>

    @POST("/api/auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<AuthResponse>

    @GET("/api/user")
    fun getUserProfile(@Header("Authorization")token: String): Call <GetUserResponse>

    @PUT("/api/user/update")
    fun updateUser(@Body request: User, @Header("Authorization")token: String): Call <ProfileResponse>

    //    ticket
    @GET("/api/ticket")
    fun getFlight() : Call<FlightResponse>

    @GET("/api/ticket/{id}")
    fun getFlightDetail(@Path("id") id: Int) : Call<FlightIdResponse>

    @GET("/api/airport")
    fun getAirport() : Call<AirportResponse>

//    companion object {
//        fun getApi(): UserApi? {
//            return ApiClient.client?.create(UserApi::class.java)
//        }
//    }
}
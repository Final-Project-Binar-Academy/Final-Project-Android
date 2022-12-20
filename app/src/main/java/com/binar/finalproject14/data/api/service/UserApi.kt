package com.binar.finalproject14.data.api.service

import com.binar.finalproject14.data.api.request.LoginRequest
import com.binar.finalproject14.data.api.request.RegisterRequest
import com.binar.finalproject14.data.api.response.airport.AirportResponse
import com.binar.finalproject14.data.api.response.auth.AuthResponse
import com.binar.finalproject14.data.api.response.profile.GetUserResponse
import com.binar.finalproject14.data.api.response.profile.ProfileResponse
import com.binar.finalproject14.data.api.response.profile.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {

    @POST("/api/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<AuthResponse>

    @POST("/api/auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<AuthResponse>

    @GET("/api/user")
    fun getUserProfile(@Header("Authorization")token: String): Call <GetUserResponse>

    @PUT("/api/user/update")
    fun updateUser(@Body request: User, @Header("Authorization")token: String): Call <ProfileResponse>



//    companion object {
//        fun getApi(): UserApi? {
//            return ApiClient.client?.create(UserApi::class.java)
//        }
//    }
}
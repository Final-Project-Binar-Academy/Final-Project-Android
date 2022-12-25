package com.binar.finalproject14.data.api.service

import com.binar.finalproject14.data.api.response.airport.AirportResponse
import com.binar.finalproject14.data.api.response.user.UserResponse
import retrofit2.Call
import retrofit2.http.GET

interface UserDataApi {

    @GET("/api/user")
    fun getAirport() : Call<UserResponse>
}
package com.binar.finalproject14.data.api.service

import com.binar.finalproject14.data.api.response.airport.AirportResponse
import retrofit2.Call
import retrofit2.http.GET

interface AirportApi {

    @GET("/api/airport")
    fun getAirport() : Call<AirportResponse>
}
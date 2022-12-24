package com.binar.finalproject14.data.api.service

import com.binar.finalproject14.data.api.response.InfoResponse
import retrofit2.Call
import retrofit2.http.GET

interface NewsApi {

    @GET("/v2/top-headlines?country=id&apiKey=561183519dd54948baad2afc4b3aa38b")
    fun getInfo(): Call<InfoResponse>
}
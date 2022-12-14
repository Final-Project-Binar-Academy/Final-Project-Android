package com.binar.finalproject14.repository

import com.binar.finalproject14.data.api.service.ApiHelper

class AirportRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getAirport()
}
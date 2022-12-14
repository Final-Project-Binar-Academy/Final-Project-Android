package com.binar.finalproject14.repository

import com.binar.finalproject14.data.api.service.ApiHelper
import javax.inject.Inject

class AirportRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getAirport() = apiHelper.getAirport()
}
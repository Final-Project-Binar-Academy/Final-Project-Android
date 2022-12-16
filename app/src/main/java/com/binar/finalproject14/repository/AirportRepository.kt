package com.binar.finalproject14.repository

import com.binar.finalproject14.data.api.service.ApiHelperImpl

class AirportRepository(private val apiHelper: ApiHelperImpl) {
    fun getAirport() = apiHelper.getAirport()

}
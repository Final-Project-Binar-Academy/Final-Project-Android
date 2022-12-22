package com.binar.finalproject14.repository

import com.binar.finalproject14.data.api.service.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlightRepository @Inject constructor(private val apiHelper: ApiHelper) {
    fun getFlight() = apiHelper.getAllFlight()

}
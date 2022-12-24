package com.binar.finalproject14.repository

import com.binar.finalproject14.data.api.service.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InfoRepository @Inject constructor(private val apiHelper: ApiHelper) {
    fun getInfo() = apiHelper.getInfo()

}
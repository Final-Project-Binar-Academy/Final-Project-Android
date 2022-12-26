package com.binar.finalproject14.data.api.service.filter

class ApiHelper(private val apiService: ApiService) {
    fun getTransactionFilter(token: String, status: String) = apiService.getTransactionFilter(token, status)
}
package com.binar.finalproject14.repository

import com.binar.finalproject14.data.api.service.filter.ApiHelper

class TransactionFilterRepository(private val apiHelper: ApiHelper) {
    fun getTransactionFilter(token: String, status: String) = apiHelper.getTransactionFilter(token, status)
}
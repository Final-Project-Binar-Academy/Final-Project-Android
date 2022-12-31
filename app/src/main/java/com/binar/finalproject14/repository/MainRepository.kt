package com.binar.finalproject14.repository

import com.binar.finalproject14.data.api.request.AddTransaction
import com.binar.finalproject14.data.api.response.payment.PaymentRequest
import com.binar.finalproject14.data.api.response.transaction.history.TransactionHistory
import com.binar.finalproject14.data.api.service.ApiHelper
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {
    fun getFlight() = apiHelper.getFlight()

    fun getFlightDetail(id: Int) = apiHelper.getFlightDetail(id)

    fun getAirport() = apiHelper.getAirport()

    fun search(departureDate: String,
               originCity: String,
               destinationCity: String,
               returnDate: String) = apiHelper.search(departureDate, originCity, destinationCity, returnDate)

    fun addTransaction(token: String, request: AddTransaction) = apiHelper.addTransaction(token, request)

    fun getTransactionId(token: String, id: Int?) = apiHelper.getTransactionId(token, id)

    fun updatePayment(token: String, request: PaymentRequest) = apiHelper.updatePayment(token, request)

    fun cancelTransaction(token: String, id: Int?) = apiHelper.cancelTransaction(token, id)

    fun getTransactionFilter(token: String, status: String) : Call<TransactionHistory> = apiHelper.getTransactionFilter(token, status)


}
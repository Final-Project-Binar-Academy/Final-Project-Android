package com.binar.finalproject14.repository

import com.binar.finalproject14.data.api.request.AddTransaction
import com.binar.finalproject14.data.api.request.NotificationRequest
import com.binar.finalproject14.data.api.request.PaymentRequest
import com.binar.finalproject14.data.api.response.notification.DeleteResponse
import com.binar.finalproject14.data.api.response.notification.NotificationCreateResponse
import com.binar.finalproject14.data.api.response.notification.NotificationIdResponse
import com.binar.finalproject14.data.api.response.notification.NotificationResponse
import com.binar.finalproject14.data.api.response.transaction.history.TransactionHistory
import com.binar.finalproject14.data.api.service.ApiHelper
import retrofit2.Call
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

    fun createNotification(token: String, request: NotificationRequest): Call<NotificationCreateResponse> = apiHelper.createNotification(token, request)

    fun getNotification(token: String): Call<NotificationResponse> = apiHelper.getNotification(token)

    fun getNotificationDetail(token: String, id: Int?): Call<NotificationIdResponse> = apiHelper.getNotificationDetail(token, id)

    fun deleteNotification(token: String, id: Int?): Call<DeleteResponse> = apiHelper.deleteNotification(token, id)


}
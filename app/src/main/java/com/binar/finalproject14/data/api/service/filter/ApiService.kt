package com.binar.finalproject14.data.api.service.filter

import com.binar.finalproject14.data.api.response.transaction.history.TransactionFilterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("/api/transaction/filter/")
    fun getTransactionFilter(@Header("Authorization")token: String, @Query("status")status: String): Call<TransactionFilterResponse>
}
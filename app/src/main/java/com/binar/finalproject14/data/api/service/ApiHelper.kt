package com.binar.finalproject14.data.api.service

import com.binar.finalproject14.data.api.request.AddTransaction
import com.binar.finalproject14.data.api.request.LoginRequest
import com.binar.finalproject14.data.api.request.RegisterRequest
import com.binar.finalproject14.data.api.response.InfoResponse
import com.binar.finalproject14.data.api.response.airport.AirportResponse
import com.binar.finalproject14.data.api.response.auth.AuthResponse
import com.binar.finalproject14.data.api.response.payment.PaymentRequest
import com.binar.finalproject14.data.api.response.payment.PaymentResponse
import com.binar.finalproject14.data.api.response.profile.GetUserResponse
import com.binar.finalproject14.data.api.response.profile.User
import com.binar.finalproject14.data.api.response.search.SearchResponse
import com.binar.finalproject14.data.api.response.ticket.FlightIdResponse
import com.binar.finalproject14.data.api.response.ticket.FlightResponse
import com.binar.finalproject14.data.api.response.transaction.CancelResponse
import com.binar.finalproject14.data.api.response.transaction.add.TransactionResponse
import com.binar.finalproject14.data.api.response.transaction.history.TransactionHistory
import com.binar.finalproject14.data.api.response.user.UpdateImage
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiHelper {
    suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>?

    suspend fun registerUser(registerRequest: RegisterRequest): Response<AuthResponse>?

    fun getUserProfile(token: String): Call <GetUserResponse>

    fun updateUser(firstName: RequestBody, lastName: RequestBody,
                   address: RequestBody, phoneNumber: RequestBody,
                   token: String): Call <User>

    fun updateImage(image: MultipartBody.Part, token: String): Call <UpdateImage>

    fun getFlight(): Call<FlightResponse>

    fun getFlightDetail(id: Int): Call<FlightIdResponse>

    fun getAirport(): Call<AirportResponse>

    fun search(departureDate: String, originCity: String,
               destinationCity: String, returnDate: String) : Call<SearchResponse>

    fun addTransaction(token: String, request: AddTransaction) : Call<TransactionResponse>

    fun getTransactionId(token: String, id: Int?) : Call<TransactionResponse>

    fun updatePayment(token: String, request: PaymentRequest) : Call<PaymentResponse>

    fun cancelTransaction(token: String, id: Int?) : Call<CancelResponse>

    fun getTransactionFilter(token: String, status: String) : Call<TransactionHistory>

    fun getInfo(): Call<InfoResponse>
}

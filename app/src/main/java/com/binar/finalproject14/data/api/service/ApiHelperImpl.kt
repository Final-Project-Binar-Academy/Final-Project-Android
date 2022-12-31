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
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: UserApi,
    private val apiServiceNews: NewsApi

): ApiHelper {
    override suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>? = apiService.loginUser(loginRequest = loginRequest)
    override suspend fun registerUser(registerRequest: RegisterRequest): Response<AuthResponse>? = apiService.registerUser(registerRequest = registerRequest)
    override fun getUserProfile(token: String): Call<GetUserResponse> {
        return apiService.getUserProfile(token)
    }

    override fun updateUser(
        firstName: RequestBody,
        lastName: RequestBody,
        address: RequestBody,
        phoneNumber: RequestBody,
        token: String
    ): Call<User> {
        return apiService.updateUser(firstName, lastName, address, phoneNumber, token)
    }

    override fun updateImage(image: MultipartBody.Part, token: String): Call<UpdateImage> {
        return apiService.updateImage(image, token)
    }

    override fun getFlight(): Call<FlightResponse> {
        return apiService.getFlight()
    }

    override fun getFlightDetail(id: Int): Call<FlightIdResponse> {
        return apiService.getFlightDetail(id)
    }

    override fun getAirport(): Call<AirportResponse> {
        return apiService.getAirport()
    }

    override fun search(
        departureDate: String,
        originCity: String,
        destinationCity: String,
        returnDate: String
    ): Call<SearchResponse> {
        return apiService.search(departureDate, originCity, destinationCity, returnDate)
    }

    override fun addTransaction(token: String, request: AddTransaction): Call<TransactionResponse> {
        return apiService.addTransaction(token, request)
    }

    override fun getTransactionId(token: String, id: Int?): Call<TransactionResponse> {
        return apiService.getTransactionId(token, id)
    }

    override fun updatePayment(token: String, request: PaymentRequest): Call<PaymentResponse> {
        return apiService.updatePayment(token, request)
    }

    override fun cancelTransaction(token: String, id: Int?): Call<CancelResponse> {
        return apiService.cancelTransaction(token, id)
    }

    override fun getTransactionFilter(token: String, status: String): Call<TransactionHistory> {
        return apiService.getTransactionFilter(token, status)
    }

    override fun getInfo(): Call<InfoResponse> = apiServiceNews.getInfo()


}
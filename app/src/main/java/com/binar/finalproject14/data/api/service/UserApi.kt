package com.binar.finalproject14.data.api.service

import com.binar.finalproject14.data.api.request.LoginRequest
import com.binar.finalproject14.data.api.request.RegisterRequest
import com.binar.finalproject14.data.api.response.TransactionResponse
import com.binar.finalproject14.data.api.response.airport.AirportResponse
import com.binar.finalproject14.data.api.response.auth.AuthResponse
import com.binar.finalproject14.data.api.response.profile.GetUserResponse
import com.binar.finalproject14.data.api.response.profile.User
import com.binar.finalproject14.data.api.response.search.SearchResponse
import com.binar.finalproject14.data.api.response.ticket.FlightIdResponse
import com.binar.finalproject14.data.api.response.ticket.FlightResponse
import com.binar.finalproject14.data.api.response.transaction.add.AddTransaction
import com.binar.finalproject14.data.api.response.transaction.add.Transaksi
import com.binar.finalproject14.data.api.response.transaction.history.Go
import com.binar.finalproject14.data.api.response.transaction.history.TransactionFilterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @POST("/api/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<AuthResponse>

    @POST("/api/auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<AuthResponse>

    @GET("/api/user")
    fun getUserProfile(@Header("Authorization")token: String): Call <GetUserResponse>

    @Multipart
    @PUT("/api/user/update")
    fun updateUser(@Part("firstName") firstName: RequestBody,
                   @Part("lastName") lastName: RequestBody,
                   @Part("address") address: RequestBody,
                   @Part("phoneNumber") phoneNumber: RequestBody,
                   @Part image: MultipartBody.Part,
                   @Header("Authorization") token: String): Call <User>

    //    ticket
    @GET("/api/ticket")
    fun getFlight() : Call<FlightResponse>

    @GET("/api/ticket/{id}")
    fun getFlightDetail(@Path("id") id: Int) : Call<FlightIdResponse>

    @GET("/api/airport")
    fun getAirport() : Call<AirportResponse>

    @GET("/api/transaction/filter")
    fun getTransactionDataByFilter(@Header("Authorization")token: String, @Query("status")status: String) : Call<TransactionFilterResponse>

    // search
    @GET("/api/ticket/search/")
    fun search(@Query("departureDate")departureDate: String, @Query("originCity")originCity: String,
                @Query("destinationCity")destinationCity: String, @Query("returnDate")returnDate: String) : Call<SearchResponse>

    @POST("/api/transaction/add")
    fun addTransaction(@Header("Authorization")token: String, @Body request : AddTransaction) : Call<TransactionResponse>

    @GET("/api/transaction/trip")
    fun getTransactionTrip(@Header("Authorization") token: String, @Query("tripId") tripId: Int?) : Call<Transaksi>


//    companion object {
//        fun getApi(): UserApi? {
//            return ApiClient.client?.create(UserApi::class.java)
//        }
//    }
}
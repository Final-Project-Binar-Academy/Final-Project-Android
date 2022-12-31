package com.binar.finalproject14.repository

import com.binar.finalproject14.data.api.request.LoginRequest
import com.binar.finalproject14.data.api.request.RegisterRequest
import com.binar.finalproject14.data.api.response.profile.GetUserResponse
import com.binar.finalproject14.data.api.response.profile.User
import com.binar.finalproject14.data.api.response.user.UpdateImage
import com.binar.finalproject14.data.api.service.ApiHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun loginUser(loginRequest: LoginRequest) = apiHelper.loginUser(loginRequest = loginRequest)
    suspend fun registerUser(registerRequest: RegisterRequest) = apiHelper.registerUser(registerRequest = registerRequest)

    fun getUserProfile(token: String): Call<GetUserResponse> = apiHelper.getUserProfile(token)

    fun updateUser(firstName: RequestBody, lastName: RequestBody,
                   address: RequestBody, phoneNumber: RequestBody,
                   token: String): Call<User> = apiHelper.updateUser(firstName, lastName, address, phoneNumber, token)

    fun updateImage(image: MultipartBody.Part, token: String): Call<UpdateImage> = apiHelper.updateImage(image, token)

}
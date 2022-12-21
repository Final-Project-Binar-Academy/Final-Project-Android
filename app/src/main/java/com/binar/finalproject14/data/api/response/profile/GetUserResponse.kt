package com.binar.finalproject14.data.api.response.profile

import androidx.annotation.Keep
import com.binar.finalproject14.data.api.request.RegisterRequest
import com.binar.finalproject14.data.api.response.auth.Data
import com.google.gson.annotations.SerializedName

@Keep
data class GetUserResponse (
    @SerializedName("data")
    var `data`: RegisterRequest?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)
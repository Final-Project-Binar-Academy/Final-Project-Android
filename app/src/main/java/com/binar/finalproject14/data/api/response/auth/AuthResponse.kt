package com.binar.finalproject14.data.api.response.auth


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AuthResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)
package com.binar.finalproject14.data.api.request


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LoginRequest(
    @SerializedName("email")
    var email: String?,
    @SerializedName("password")
    var password: String?
)
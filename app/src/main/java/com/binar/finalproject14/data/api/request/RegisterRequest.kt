package com.binar.finalproject14.data.api.request

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class RegisterRequest(
    @SerializedName("email")
    var email: String?,
    @SerializedName("firstName")
    var firstName: String?,
    @SerializedName("lastName")
    var lastName: String?,
    @SerializedName("password")
    var password: String?,
    @SerializedName("address")
    var address: String?,
    @SerializedName("phoneNumber")
    var phoneNumber: String?,
    @SerializedName("avatar")
    var avatar: String?,
)
package com.binar.finalproject14.data.api.response.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class User(
    @SerializedName("firstName")
    var firstName: String?,
    @SerializedName("lastName")
    var lastName: String?,
    @SerializedName("address")
    var address: String?,
    @SerializedName("phoneNumber")
    var phoneNumber: Int?,
    @SerializedName("avatar")
    var avatar: String?,
    )
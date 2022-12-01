package com.binar.finalproject14.data.api.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Data(
    @SerializedName("email")
    var email: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("role")
    var role: String?,
    @SerializedName("roleId")
    var roleId: Int?,
    @SerializedName("token")
    var token: String?
)
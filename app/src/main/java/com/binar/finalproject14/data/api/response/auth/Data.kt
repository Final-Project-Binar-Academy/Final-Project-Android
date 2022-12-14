package com.binar.finalproject14.data.api.response.auth


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("accessToken")
    var accessToken: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("role")
    var role: String?,
    @SerializedName("roleId")
    var roleId: Int?
)
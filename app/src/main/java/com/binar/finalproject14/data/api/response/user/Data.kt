package com.binar.finalproject14.data.api.response.user


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: Any?,
    @SerializedName("avatar")
    val avatar: Any?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("gender")
    val gender: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: Any?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)
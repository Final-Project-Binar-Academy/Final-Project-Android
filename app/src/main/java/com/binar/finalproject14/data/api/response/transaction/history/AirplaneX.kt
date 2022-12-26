package com.binar.finalproject14.data.api.response.transaction.history


import com.google.gson.annotations.SerializedName

data class AirplaneX(
    @SerializedName("airplaneCode")
    val airplaneCode: String?,
    @SerializedName("airplaneName")
    val airplaneName: String?,
    @SerializedName("company")
    val company: Company?,
    @SerializedName("companyId")
    val companyId: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)
package com.binar.finalproject14.data.api.response.transaction.history


import com.google.gson.annotations.SerializedName

data class Origin(
    @SerializedName("airportName")
    val airportName: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("cityCode")
    val cityCode: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)
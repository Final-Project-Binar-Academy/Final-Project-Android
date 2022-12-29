package com.binar.finalproject14.data.api.response.transaction.add


import com.google.gson.annotations.SerializedName

data class Passenger(
    @SerializedName("brithDate")
    val brithDate: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("NIK")
    val nIK: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
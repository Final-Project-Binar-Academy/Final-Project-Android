package com.binar.finalproject14.data.api.response.transaction.add


import com.google.gson.annotations.SerializedName

data class TypeTrip(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
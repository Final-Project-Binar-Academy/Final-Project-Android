package com.binar.finalproject14.data.api.response.transaction.history


import com.google.gson.annotations.SerializedName

data class Company(
    @SerializedName("companyImage")
    val companyImage: String?,
    @SerializedName("companyName")
    val companyName: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)
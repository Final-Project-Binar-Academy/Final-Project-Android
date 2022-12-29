package com.binar.finalproject14.data.api.response.transaction.add


import com.google.gson.annotations.SerializedName

data class Transaksi(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalData")
    val totalData: Int
)
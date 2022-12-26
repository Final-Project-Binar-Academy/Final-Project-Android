package com.binar.finalproject14.data.api.response.transaction.history


import com.google.gson.annotations.SerializedName

data class TransactionFilterResponse(
    @SerializedName("data")
    val `data`: List<Data>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalData")
    val totalData: Int?
)
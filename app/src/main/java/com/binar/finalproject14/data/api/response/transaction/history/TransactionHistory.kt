package com.binar.finalproject14.data.api.response.transaction.history


import androidx.annotation.Keep
import com.binar.finalproject14.data.api.response.transaction.Data
import com.google.gson.annotations.SerializedName

@Keep
data class TransactionHistory(
    @SerializedName("data")
    var `data`: List<Data>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalData")
    var totalData: Int?
)
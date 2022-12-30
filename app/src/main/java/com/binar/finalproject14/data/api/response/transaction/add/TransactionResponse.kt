package com.binar.finalproject14.data.api.response.transaction.add


import androidx.annotation.Keep
import com.binar.finalproject14.data.api.response.transaction.Data
import com.google.gson.annotations.SerializedName

@Keep
data class TransactionResponse(
    @SerializedName("data")
    var data: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)
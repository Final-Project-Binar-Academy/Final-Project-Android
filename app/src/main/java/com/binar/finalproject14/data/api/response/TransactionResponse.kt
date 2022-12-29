package com.binar.finalproject14.data.api.response

import com.binar.finalproject14.data.api.response.transaction.add.AddTransaction
import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("data")
    var data: AddTransaction,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalResults")
    var totalResults: Int?
)
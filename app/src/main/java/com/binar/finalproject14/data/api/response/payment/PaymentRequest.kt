package com.binar.finalproject14.data.api.response.payment


import com.google.gson.annotations.SerializedName

data class PaymentRequest(
    @SerializedName("transactionId")
    val transactionId: Int,
    @SerializedName("paymentId")
    val paymentId: Int

)
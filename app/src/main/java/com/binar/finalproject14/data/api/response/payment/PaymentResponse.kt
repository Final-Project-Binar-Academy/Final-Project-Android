package com.binar.finalproject14.data.api.response.payment

import com.binar.finalproject14.data.api.request.PaymentRequest
import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("data")
    var `data`: PaymentRequest?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)
package com.binar.finalproject14.data.api.response.transaction


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CancelResponse(
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)
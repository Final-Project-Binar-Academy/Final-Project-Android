package com.binar.finalproject14.data.api.response.ticket


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FlightIdResponse(
    @SerializedName("data")
    var `data`: DataFlight?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)
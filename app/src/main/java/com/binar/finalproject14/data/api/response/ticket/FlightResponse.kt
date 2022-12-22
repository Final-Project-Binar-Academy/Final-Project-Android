package com.binar.finalproject14.data.api.response.ticket


import androidx.annotation.Keep
import com.binar.finalproject14.data.api.response.ticket.DataFlight
import com.google.gson.annotations.SerializedName

@Keep
data class FlightResponse(
    @SerializedName("data")
    var `data`: List<DataFlight?>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalData")
    var totalData: Int?
)
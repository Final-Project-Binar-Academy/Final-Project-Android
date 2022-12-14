package com.binar.finalproject14.data.api.response.airport


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AirportResponse(
    @SerializedName("data")
    var `data`: List<DataAirport>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalData")
    var totalData: Int?
)
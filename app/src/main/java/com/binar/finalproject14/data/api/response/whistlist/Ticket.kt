package com.binar.finalproject14.data.api.response.whistlist


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.*

@Keep
data class Ticket(
    @SerializedName("airplaneId")
    var airplaneId: Int?,
    @SerializedName("arrivalDate")
    var arrivalDate: Date?,
    @SerializedName("arrivalTime")
    var arrivalTime: String?,
    @SerializedName("capacity")
    var capacity: Int?,
    @SerializedName("class")
    var classX: String?,
    @SerializedName("code")
    var code: String?,
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("departureDate")
    var departureDate: Date?,
    @SerializedName("departureTime")
    var departureTime: String?,
    @SerializedName("flightFrom")
    var flightFrom: Int?,
    @SerializedName("flightTo")
    var flightTo: Int?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("price")
    var price: Int?,
    @SerializedName("seatNumber")
    var seatNumber: String?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)
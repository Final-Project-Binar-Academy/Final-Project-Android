package com.binar.finalproject14.data.api.response.transaction.history


import com.google.gson.annotations.SerializedName

data class Back(
    @SerializedName("airplane")
    val airplane: Airplane?,
    @SerializedName("airplaneId")
    val airplaneId: Int?,
    @SerializedName("arrivalDate")
    val arrivalDate: String?,
    @SerializedName("arrivalTime")
    val arrivalTime: String?,
    @SerializedName("capacity")
    val capacity: Int?,
    @SerializedName("class")
    val classX: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("departureDate")
    val departureDate: String?,
    @SerializedName("departureTime")
    val departureTime: String?,
    @SerializedName("destination")
    val destination: Destination?,
    @SerializedName("flightFrom")
    val flightFrom: Int?,
    @SerializedName("flightTo")
    val flightTo: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("origin")
    val origin: Origin?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)
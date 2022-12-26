package com.binar.finalproject14.data.api.response.transaction.history


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("back")
    val back: Back?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("go")
    val go: Go?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("passenger")
    val passenger: Passenger?,
    @SerializedName("passengerId")
    val passengerId: Int?,
    @SerializedName("payment")
    val payment: Any?,
    @SerializedName("paymentId")
    val paymentId: Any?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("ticketBack")
    val ticketBack: Int?,
    @SerializedName("ticketGo")
    val ticketGo: Int?,
    @SerializedName("totalPrice")
    val totalPrice: String?,
    @SerializedName("transactionCode")
    val transactionCode: String?,
    @SerializedName("tripId")
    val tripId: Int?,
    @SerializedName("typeTrip")
    val typeTrip: TypeTrip?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("userId")
    val userId: Int?
)
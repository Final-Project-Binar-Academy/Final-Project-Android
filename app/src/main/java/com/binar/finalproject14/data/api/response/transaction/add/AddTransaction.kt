package com.binar.finalproject14.data.api.response.transaction.add


import com.google.gson.annotations.SerializedName

data class AddTransaction(
    @SerializedName("ticketGo")
    val ticketGo: Int,
    @SerializedName("ticketBack")
    val ticketBack: Int,
    @SerializedName("tripId")
    val tripId: Int,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("NIK")
    val nIK: String,
    @SerializedName("brithDate")
    val brithDate: String,


)
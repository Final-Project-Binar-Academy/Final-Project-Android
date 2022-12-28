package com.binar.finalproject14.data.api.response.search


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DataSearch(
    @SerializedName("ticketBack")
    var ticketBack: TicketBack?,
    @SerializedName("ticketGo")
    var ticketGo: List<TicketGo>?
)
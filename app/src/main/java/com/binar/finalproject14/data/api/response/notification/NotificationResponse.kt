package com.binar.finalproject14.data.api.response.notification


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NotificationResponse(
    @SerializedName("data")
    var `data`: List<DataNotif?>?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalData")
    var totalData: Int?
)
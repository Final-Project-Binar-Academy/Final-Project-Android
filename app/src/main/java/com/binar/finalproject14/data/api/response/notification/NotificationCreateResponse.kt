package com.binar.finalproject14.data.api.response.notification


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NotificationCreateResponse(
    @SerializedName("message")
    var message: String?,
    @SerializedName("notifId")
    var notifId: Int?,
    @SerializedName("status")
    var status: String?
)
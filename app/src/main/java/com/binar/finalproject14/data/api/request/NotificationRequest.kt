package com.binar.finalproject14.data.api.request


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NotificationRequest(
    @SerializedName("message")
    var message: String?
)
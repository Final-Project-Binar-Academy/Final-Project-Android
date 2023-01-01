package com.binar.finalproject14.data.api.response.notification


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DeleteResponse(
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)
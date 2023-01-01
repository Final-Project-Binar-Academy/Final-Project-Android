package com.binar.finalproject14.data.api.response.notification


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DataNotif(
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)
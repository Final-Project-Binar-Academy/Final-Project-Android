package com.binar.finalproject14.data.api.response.whistlist


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
@Keep
data class WhistlistResponse(
    @SerializedName("data")
    var `data`: List<DataWhistlist>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)
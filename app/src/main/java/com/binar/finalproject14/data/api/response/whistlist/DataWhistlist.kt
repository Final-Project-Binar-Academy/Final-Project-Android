package com.binar.finalproject14.data.api.response.whistlist


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
@Keep
data class DataWhistlist(
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("ticket")
    var ticket: Ticket?,
    @SerializedName("ticketId")
    var ticketId: Int?,
    @SerializedName("updatedAt")
    var updatedAt: String?,
    @SerializedName("user")
    var user: User?,
    @SerializedName("userId")
    var userId: Int?
)
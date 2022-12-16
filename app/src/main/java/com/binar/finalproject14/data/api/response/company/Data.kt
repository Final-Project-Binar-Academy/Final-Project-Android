package com.binar.finalproject14.data.api.response.company


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("companyImage")
    var companyImage: String?,
    @SerializedName("companyName")
    var companyName: String?,
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)
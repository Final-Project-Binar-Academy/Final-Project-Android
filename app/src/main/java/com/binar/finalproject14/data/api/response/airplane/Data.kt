package com.binar.finalproject14.data.api.response.airplane


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("airplaneCode")
    var airplaneCode: String?,
    @SerializedName("airplaneName")
    var airplaneName: String?,
    @SerializedName("company")
    var company: Company?,
    @SerializedName("companyeName")
    var companyeName: String?,
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)
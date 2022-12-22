package com.binar.finalproject14.data.api.response.ticket


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Airplane(
    @SerializedName("airplaneCode")
    var airplaneCode: String?,
    @SerializedName("airplaneName")
    var airplaneName: String?,
    @SerializedName("company")
    var company: Company?,
    @SerializedName("companyId")
    var companyId: Int?,
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)
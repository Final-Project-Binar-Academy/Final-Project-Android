package com.binar.finalproject14.data.api.response.company


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CompanyResponse(
    @SerializedName("data")
    var `data`: List<Data?>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalData")
    var totalData: Int?
)
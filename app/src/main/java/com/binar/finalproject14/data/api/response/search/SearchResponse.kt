package com.binar.finalproject14.data.api.response.search


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchResponse(
    @SerializedName("data")
    var `data`: DataSearch?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)
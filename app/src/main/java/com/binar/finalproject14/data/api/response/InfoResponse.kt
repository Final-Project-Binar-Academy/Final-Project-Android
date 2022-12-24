package com.binar.finalproject14.data.api.response


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class InfoResponse(
    @SerializedName("articles")
    var articles: List<Article?>?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalResults")
    var totalResults: Int?
)
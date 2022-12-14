package com.binar.finalproject14.data.api.response


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Source(
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    var name: String?
)
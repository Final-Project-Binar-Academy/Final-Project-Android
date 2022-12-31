package com.binar.finalproject14.data.api.response.user

import com.google.gson.annotations.SerializedName

data class UpdateImage(
    @SerializedName("image")
    var image: String?
)
package com.binar.finalproject14.data.api.response.transaction.history


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("roleId")
    val roleId: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)
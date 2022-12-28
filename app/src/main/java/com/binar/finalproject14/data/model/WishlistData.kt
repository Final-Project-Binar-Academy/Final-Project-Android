package com.binar.finalproject14.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
class WishlistData(
    @PrimaryKey(autoGenerate = true)
    var id_wishlist: Int,
    var departure_date: String,
    var departure_time: String,
    var codeIataFrom: String,
    var city_origin: String,
    var duration: String,
    var kelas: String,
    var arrival_date: String,
    var arrival_time: String,
    var codeIataTo: String,
    var city_destination: String,
    var company: String,
    var price: String
) : Parcelable
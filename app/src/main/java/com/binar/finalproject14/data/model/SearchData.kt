package com.binar.finalproject14.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
class SearchData(
    @PrimaryKey(autoGenerate = true)
    var departure_date: String,
    var city_origin: String,
    var city_destination: String,
    var return_date: String,
) : Parcelable
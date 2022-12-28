package com.binar.finalproject14.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.binar.finalproject14.data.model.WishlistData

@Dao
interface WishlistDao {
    @Query("SELECT * FROM WishlistData")
    fun getAllWishlist() : List<WishlistData>

    @Query("SELECT EXISTS(SELECT id_wishlist FROM WishlistData WHERE id_wishlist = :id)")
    fun cekWishlist(id : Int) : Boolean

    @Insert
    fun addWishlist(wishlistTicket: WishlistData)

    @Delete
    fun deleteWishlist(wishlistTicket: WishlistData)
}
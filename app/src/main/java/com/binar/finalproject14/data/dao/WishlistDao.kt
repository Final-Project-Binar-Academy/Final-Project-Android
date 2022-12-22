package com.binar.finalproject14.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.binar.finalproject14.data.dao.WishlistData

@Dao
interface WishlistDao {
    @Query("SELECT * FROM WishlistData")
    fun getAllWishlist() : List<WishlistData>

    @Query("SELECT EXISTS(SELECT id_wishlist FROM WishlistData WHERE id_wishlist = :id)")
    fun cekWishlist(id : Int) : Boolean

    @Insert
    fun addWishlist(wishlistMovie: WishlistData)

    @Delete
    fun deleteWishlist(wishlistMovie: WishlistData)
}
package com.binar.finalproject14.repository

import com.binar.finalproject14.data.dao.WishlistDao
import com.binar.finalproject14.data.dao.WishlistData

class WishlistRepository(private val data: WishlistDao) {

    fun getAllDataWishlist() : List<WishlistData> {
        return data.getAllWishlist()
    }

    suspend fun addWishlist(wishlist: WishlistData) = data.addWishlist(wishlist)

    suspend fun cekWishlist(id: Int) = data.cekWishlist(id)

    suspend fun deleteWishlist(wishlist: WishlistData) = data.deleteWishlist(wishlist)

}
package com.binar.finalproject14.repository

import com.binar.finalproject14.data.dao.WishlistDao
import com.binar.finalproject14.data.model.WishlistData

class WishlistRepository(private val data: WishlistDao) {

    fun getAllDataWishlist() : List<WishlistData> {
        return data.getAllWishlist()
    }

    fun addWishlist(wishlist: WishlistData) = data.addWishlist(wishlist)

    fun cekWishlist(id: Int) = data.cekWishlist(id)

    fun deleteWishlist(wishlist: WishlistData) = data.deleteWishlist(wishlist)

}
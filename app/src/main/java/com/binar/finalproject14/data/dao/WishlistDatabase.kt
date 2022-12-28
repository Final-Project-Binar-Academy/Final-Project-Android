package com.binar.finalproject14.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.binar.finalproject14.data.model.WishlistData

@Database( entities = [
    WishlistData::class],
    version = 1 )
abstract class WishlistDatabase : RoomDatabase() {
    abstract fun wishlistDao() : WishlistDao

    companion object{
        @Volatile
        private var INSTANCE : WishlistDatabase? = null

        fun getInstance(context : Context): WishlistDatabase? {
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WishlistDatabase::class.java,
                        "wishlist.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

}
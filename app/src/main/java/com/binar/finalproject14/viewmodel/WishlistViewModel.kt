package com.binar.finalproject14.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.binar.finalproject14.data.api.service.UserApi
import com.binar.finalproject14.data.model.WishlistData
import com.binar.finalproject14.data.dao.WishlistDatabase
import com.binar.finalproject14.repository.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val client: UserApi,
    application: Application
) : AndroidViewModel(application) {

    private val repository : WishlistRepository

    init {
        val wishlistDao = WishlistDatabase.getInstance(application)?.wishlistDao()
        repository = WishlistRepository(wishlistDao!!)
    }

    private val listWishlist: MutableLiveData<List<WishlistData>> = MutableLiveData()
    val list_wishlist: LiveData<List<WishlistData>> get() = listWishlist

    @OptIn(DelicateCoroutinesApi::class)
    fun getAllWishlistFlight() {
        GlobalScope.launch {
            listWishlist.postValue(repository.getAllDataWishlist())
        }
    }

    private val isWishlist: MutableLiveData<Boolean> = MutableLiveData()
    val wishlist: LiveData<Boolean> get() = isWishlist

    @OptIn(DelicateCoroutinesApi::class)
    fun isWishlist(id: Int) {
        GlobalScope.launch {
            isWishlist.postValue(repository.cekWishlist(id))
        }
    }

    private val fav_flight: MutableLiveData<WishlistData> = MutableLiveData()
    val wishlistFlight: LiveData<WishlistData> get() = fav_flight

    @OptIn(DelicateCoroutinesApi::class)
    fun addWishlist(wishlist: WishlistData) {
        GlobalScope.launch {
            repository.addWishlist(wishlist)
            fav_flight.postValue(wishlist)
        }
    }


    private val delete_wishlist: MutableLiveData<WishlistData> = MutableLiveData()
    val deleteWishlist: LiveData<WishlistData> get() = delete_wishlist

    @OptIn(DelicateCoroutinesApi::class)
    fun removeWishlist(wishlist: WishlistData) {
        GlobalScope.launch {
            repository.deleteWishlist(wishlist)
            delete_wishlist.postValue(wishlist)
        }
    }
}

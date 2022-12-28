package com.binar.finalproject14.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.finalproject14.data.api.service.filter.ApiHelper
import com.binar.finalproject14.repository.TransactionFilterRepository
import com.binar.finalproject14.data.utils.UserDataStoreManager
import com.binar.finalproject14.viewmodel.TransactionFilterViewModel

class TransactionFilterViewModelFactory(
    private val apiHelper: ApiHelper,
    private val pref: UserDataStoreManager
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionFilterViewModel::class.java)) {
            return TransactionFilterViewModel(TransactionFilterRepository(apiHelper), pref) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
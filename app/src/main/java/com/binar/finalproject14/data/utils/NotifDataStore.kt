package com.binar.finalproject14.data.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotifDataStore(@ApplicationContext val context: Context) {

    companion object {
        private const val DATASTORE_NAME = "NOTIF_DATASTORE"
        private val NOTIFICATION = stringPreferencesKey("NOTIFICATION")
        private val IS_NOTIFICATION = booleanPreferencesKey("IS_NOTIFICATION")
        private val TOTAL_NOTIF = intPreferencesKey("TOTAL_NOTIF")
        private val Context.dataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }

    val getNotif: Flow<String> =
        context.dataStore.data.map {
            it[NOTIFICATION] ?: ""
        }

    val getIsNotif: Flow<Boolean> =
        context.dataStore.data.map {
            it[IS_NOTIFICATION] ?: false
        }

    val getTotalNotif: Flow<Int> =
        context.dataStore.data.map {
            it[TOTAL_NOTIF] ?: 0
        }

    suspend fun saveNotif(notif: String, total: Int){
        context.dataStore.edit {
            it[NOTIFICATION] = notif
            it[IS_NOTIFICATION] = true
            it[TOTAL_NOTIF] = total
        }
    }

    suspend fun removeNotif(){
        context.dataStore.edit {
            it.remove(NOTIFICATION)
            it.remove(IS_NOTIFICATION)
            it.remove(TOTAL_NOTIF)
        }
    }
}
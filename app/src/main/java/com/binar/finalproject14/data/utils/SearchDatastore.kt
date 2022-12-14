package com.binar.finalproject14.data.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchDatastore(@ApplicationContext val context: Context) {

    companion object {
        private const val DATASTORE_NAME = "CITY_DATASTORE"
        private val CITY_DESTINATION = stringPreferencesKey("CITY_DESTINATION")
        private val CITYCODE_DESTINATION = stringPreferencesKey("CITYCODE_DESTINATION")
        private val CITY_DEPARTURE = stringPreferencesKey("CITY_DEPARTURE")
        private val CITYCODE_DEPARTURE = stringPreferencesKey("CITYCODE_DEPARTURE")
        private val IS_DEPARTURE_KEY = booleanPreferencesKey("IS_DEPARTURE_KEY")
        private val IS_DESTINATION_KEY = booleanPreferencesKey("IS_DESTINATION_KEY")
        private val DEPARTURE_DATE = stringPreferencesKey("DEPARTURE_DATE")
        private val RETURN_DATE = stringPreferencesKey("RETURN_DATE")
        private val IS_DEPARTURE_DATE = booleanPreferencesKey("IS_DEPARTURE_DATE")
        private val IS_RETURN_DATE = booleanPreferencesKey("IS_RETURN_DATE")
        private val IS_ONEWAY = booleanPreferencesKey("IS_ONEWAY")
        private val Context.dataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }

    val getCityDeparture: Flow<String> =
        context.dataStore.data.map {
            it[CITY_DEPARTURE] ?: ""
        }

    val getCityCodeDeparture: Flow<String> =
        context.dataStore.data.map {
            it[CITYCODE_DEPARTURE] ?: ""
        }

    val getCityDestination: Flow<String> =
        context.dataStore.data.map {
            it[CITY_DESTINATION] ?: ""
        }

    val getCityCodeDestination: Flow<String> =
        context.dataStore.data.map {
            it[CITYCODE_DESTINATION] ?: ""
        }

    val getDepartureDate: Flow<String> =
        context.dataStore.data.map {
            it[DEPARTURE_DATE] ?: ""
        }

    val getReturnDate: Flow<String> =
        context.dataStore.data.map {
            it[RETURN_DATE] ?: ""
        }

    val getIsDeparture: Flow<Boolean> =
        context.dataStore.data.map {
            it[IS_DEPARTURE_KEY] ?: false
        }

    val getIsDestination: Flow<Boolean> =
        context.dataStore.data.map {
            it[IS_DESTINATION_KEY] ?: false
        }

    val getIsDepartureDate: Flow<Boolean> =
        context.dataStore.data.map {
            it[IS_DEPARTURE_DATE] ?: false
        }

    val getIsReturnDate: Flow<Boolean> =
        context.dataStore.data.map {
            it[IS_RETURN_DATE] ?: false
        }

    val getIsOneway: Flow<Boolean> =
        context.dataStore.data.map {
            it[IS_ONEWAY] ?: false
        }

    suspend fun saveIsOneway(isOneway: Boolean){
        context.dataStore.edit {
            it[IS_ONEWAY] = isOneway
        }
    }

    suspend fun saveDepartureDate(departureDate: String){
        context.dataStore.edit {
            it[DEPARTURE_DATE] = departureDate
        }
    }

    suspend fun saveReturnDate(returnDate: String){
        context.dataStore.edit {
            it[RETURN_DATE] = returnDate
        }
    }


    suspend fun saveDeparture(cityDeparture: String, cityCodeDeparture: String, isDeparture: Boolean) {
        context.dataStore.edit {
            it[CITY_DEPARTURE] = cityDeparture
            it[CITYCODE_DEPARTURE] = cityCodeDeparture
            it[IS_DEPARTURE_KEY] = isDeparture
        }
    }

    suspend fun saveDestination(cityDestination: String, cityCodeDestination: String, isDestination: Boolean) {
        context.dataStore.edit {
            it[CITY_DESTINATION] = cityDestination
            it[CITYCODE_DESTINATION] = cityCodeDestination
            it[IS_DESTINATION_KEY] = isDestination
        }
    }

    suspend fun removeDeparture(){
        context.dataStore.edit {
            it.remove(CITY_DEPARTURE)
            it.remove(CITYCODE_DEPARTURE)
            it.remove(IS_DEPARTURE_KEY)
        }
    }

    suspend fun removeDestination(){
        context.dataStore.edit {
            it.remove(CITY_DESTINATION)
            it.remove(CITYCODE_DESTINATION)
            it.remove(IS_DESTINATION_KEY)
        }
    }

    suspend fun removeDepartureDate(){
        context.dataStore.edit {
            it.remove(DEPARTURE_DATE)
        }
    }

    suspend fun removeReturnDate(){
        context.dataStore.edit {
            it.remove(RETURN_DATE)
        }
    }

    suspend fun removeIsOneway(){
        context.dataStore.edit {
            it.remove(IS_ONEWAY)
        }
    }
}
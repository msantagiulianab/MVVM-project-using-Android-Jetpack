package com.example.android.roomwordsample

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class SettingDataStore(
    context: Context
) {
    companion object {
        private const val DATA_STORE_NAME = "setting_dark_mode.pref"
        private val IS_DARK_MODE = preferencesKey<Boolean>("is_dark_mode")
    }

    private val appContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = appContext.createDataStore(
            name = DATA_STORE_NAME
        )
    }

    suspend fun setDarkMode(uiMode: UiMode) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = when (uiMode) {
                UiMode.LIGHT -> false
                UiMode.DARK -> true
            }
        }
    }

    val uiModeFlow: Flow<UiMode> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            when (preferences[IS_DARK_MODE] ?: false) {
                true -> UiMode.DARK
                false -> UiMode.LIGHT
            }
        }
}

//class SettingDataStore(context: Context) {
//
//    companion object {
//        private const val DATA_STORE_NAME = "setting_dark_mode.pref"
//        private val IS_DARK_MODE = preferencesKey<Int>("is_dark_mode")
//    }
//
//    private val appContext = context.applicationContext
//    private val dataStore: DataStore<Preferences>
//
//    init {
//        dataStore = appContext.createDataStore(
//            name = DATA_STORE_NAME
//        )
//    }
//
//    suspend fun setDarkMode(uiMode: UiMode) {
//        dataStore.edit { preferences ->
//            preferences[IS_DARK_MODE] = when (uiMode) {
//                UiMode.LIGHT -> 0
//                UiMode.DARK -> 1
//                UiMode.SYSTEM -> 2
//            }
//        }
//    }
//
//    val uiModeFlow: Flow<UiMode> = dataStore.data
//        .catch {
//            if (it is IOException) {
//                it.printStackTrace()
//                emit(emptyPreferences())
//            } else {
//                throw it
//            }
//        }
//        .map { preferences ->
//            when (preferences[IS_DARK_MODE] ?: 2) {
//                0 -> UiMode.DARK
//                1 -> UiMode.LIGHT
//                else -> UiMode.SYSTEM
//            }
//        }
//}
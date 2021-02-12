package com.example.android.roomwordsample

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException

enum class UiTheme {
    LIGHT, DARK
}

class PreferencesManager(context: Context) {

    companion object {
        private const val DATA_STORE_NAME = "setting_dark_mode.pref"
        private val ENABLE_DARK = booleanPreferencesKey("is_dark_mode")
    }

    private val appContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = appContext.createDataStore(
            name = DATA_STORE_NAME
        )
    }

    suspend fun updateEnableDark(uiTheme: UiTheme) {
        dataStore.edit { preferences ->
            preferences[ENABLE_DARK] = when (uiTheme) {
                UiTheme.LIGHT -> false
                UiTheme.DARK -> true
            }
        }
    }

    val preferencesFlow: Flow<UiTheme> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e(exception, "Error reading preferences")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            when (preferences[ENABLE_DARK] ?: false) {
                true -> UiTheme.DARK
                false -> UiTheme.LIGHT
            }
        }
}

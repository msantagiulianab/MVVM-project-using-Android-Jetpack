package com.example.android.roomwordsample

import android.app.Application
import com.example.android.roomwordsample.database.WordRoomDatabase
import com.example.android.roomwordsample.preferences.PreferencesManager
import com.example.android.roomwordsample.repositories.DefaultWordRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class WordsApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }

    val repository by lazy { DefaultWordRepository(database.wordDao()) }

    val prefManager by lazy { PreferencesManager(applicationContext) }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
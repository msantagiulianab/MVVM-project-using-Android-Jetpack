package com.example.android.roomwordsample.application

import android.app.Application
import com.example.android.roomwordsample.BuildConfig
import com.example.android.roomwordsample.PreferencesManager
import com.example.android.roomwordsample.database.WordRepository
import com.example.android.roomwordsample.database.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import timber.log.Timber.DebugTree


class WordsApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }

    val prefManager by lazy { PreferencesManager(applicationContext) }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
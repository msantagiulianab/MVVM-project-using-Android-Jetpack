package com.example.android.roomwordsample.di

import android.content.Context
import androidx.room.Room
import com.example.android.roomwordsample.database.WordDao
import com.example.android.roomwordsample.database.WordRoomDatabase
import com.example.android.roomwordsample.network.apiNews.ApiInterface
import com.example.android.roomwordsample.repositories.DefaultWordRepository
import com.example.android.roomwordsample.util.Constants.Companion.BASE_URL
import com.example.android.roomwordsample.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun wordRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, WordRoomDatabase::class.java, DATABASE_NAME)

    @Singleton
    @Provides
    fun provideWordDao(
        database: WordRoomDatabase
    ) = database.wordDao()

    @Singleton
    @Provides
    fun provideWordRepository(
        dao: WordDao
    ) = DefaultWordRepository(dao)

    @Singleton
    @Provides
    fun provideApiInterface(): ApiInterface {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
    }
}
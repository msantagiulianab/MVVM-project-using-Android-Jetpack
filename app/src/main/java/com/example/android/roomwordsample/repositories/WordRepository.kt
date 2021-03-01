package com.example.android.roomwordsample.repositories

import com.example.android.roomwordsample.database.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {

    val allWords: Flow<List<Word>>

    suspend fun insertWordItem(word: Word)
//    suspend fun insert(word: Word)

    suspend fun deleteItem(word: Word)

    suspend fun deleteAll()

}
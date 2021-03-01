package com.example.android.roomwordsample.repositories

import com.example.android.roomwordsample.database.Word
import com.example.android.roomwordsample.database.WordDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultWordRepository @Inject constructor(
    private val wordDao: WordDao
) : WordRepository {

    override val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    override suspend fun insertWordItem(word: Word) {
        wordDao.insertWordItem(word)
    }

    override suspend fun deleteItem(word: Word) {
        wordDao.deleteItem(word)
    }

    override suspend fun deleteAll() {
        wordDao.deleteAll()
    }

//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun insert(word: Word) {
//        wordDao.insert(word)
//    }
//
//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun deleteItem(word: Word) {
//        wordDao.deleteItem(word)
//    }
//
//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun deleteAll() {
//        wordDao.deleteAll()
//    }

}
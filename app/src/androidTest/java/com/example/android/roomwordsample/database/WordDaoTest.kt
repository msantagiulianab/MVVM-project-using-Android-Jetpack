package com.example.android.roomwordsample.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.test.filters.SmallTest
import com.example.android.roomwordsample.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class WordDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: WordRoomDatabase
    private lateinit var dao: WordDao

    @Before
    fun setup() {
        hiltRule.inject()

//        database = Room.inMemoryDatabaseBuilder(
//            ApplicationProvider.getApplicationContext(),
//            WordRoomDatabase::class.java
//        ).allowMainThreadQueries().build()
        dao = database.wordDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertWord() = runBlocking {
        val word = Word("stuff")
        dao.insertWordItem(word)

        val allWords = dao.getAlphabetizedWords().asLiveData().getOrAwaitValue()

        assertThat(allWords).contains(word)
    }

    @Test
    fun deleteWord() = runBlocking {
        val word = Word("thing")
        dao.insertWordItem(word)
        dao.deleteItem(word)

        val allWords = dao.getAlphabetizedWords().asLiveData().getOrAwaitValue()

        assertThat(allWords).doesNotContain(word)
    }

    @Test
    fun deleteAll() = runBlocking {
        val word1 = Word("name")
        dao.insertWordItem(word1)
        val word2 = Word("surname")
        dao.insertWordItem(word2)
        dao.deleteAll()

        val allWords = dao.getAlphabetizedWords().asLiveData().getOrAwaitValue()
        assertThat(allWords).isEmpty()
    }

    @Test
    fun getAllWords() = runBlocking {
        val word1 = Word("name")
        dao.insertWordItem(word1)
        val word2 = Word("surname")
        dao.insertWordItem(word2)

        val allWords = dao.getAlphabetizedWords().asLiveData().getOrAwaitValue()
        assertThat(allWords[0].word).isEqualTo(word1.word)
        assertThat(allWords[1].word).isEqualTo(word2.word)
    }
}
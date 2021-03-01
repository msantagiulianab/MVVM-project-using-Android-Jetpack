package com.example.android.roomwordsample.repositories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.android.roomwordsample.database.Word
import kotlinx.coroutines.flow.Flow

class FakeWordRepository : WordRepository {

    private val wordItems = mutableListOf<Word>()

    private val observableWordItems = MutableLiveData<List<Word>>(wordItems)

    private fun refreshLiveData() {
        observableWordItems.postValue(wordItems)
    }

    override val allWords: Flow<List<Word>> = observableWordItems.asFlow()

    override suspend fun insertWordItem(word: Word) {
        wordItems.add(word)
        refreshLiveData()
    }

    override suspend fun deleteItem(word: Word) {
        wordItems.remove(word)
        refreshLiveData()
    }

    override suspend fun deleteAll() {
        wordItems.removeAll(wordItems)
    }


}
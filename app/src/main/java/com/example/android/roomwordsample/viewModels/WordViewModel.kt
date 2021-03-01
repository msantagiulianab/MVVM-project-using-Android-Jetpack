package com.example.android.roomwordsample.viewModels

import androidx.lifecycle.*
import com.example.android.roomwordsample.database.Word
import com.example.android.roomwordsample.repositories.WordRepository
import com.example.android.roomwordsample.util.Constants
import com.example.android.roomwordsample.util.Event
import com.example.android.roomwordsample.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(private val repository: WordRepository) : ViewModel() {

    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    private val _insertWordItemStatus = MutableLiveData<Event<Resource<Word>>>()
    val insertWordItemStatus: LiveData<Event<Resource<Word>>> = _insertWordItemStatus

    fun insert(word: Word) = viewModelScope.launch {
        repository.insertWordItem(word)
    }

    fun insertWordItem(name: String) {
        if (name.isEmpty()) {
            _insertWordItemStatus.postValue(
                Event(
                    Resource.error(
                        "The field must not be empty",
                        null
                    )
                )
            )
            return
        }
        if (name.length > Constants.MAX_NAME_LENGTH) {
            _insertWordItemStatus.postValue(
                Event(
                    Resource.error(
                        "The word must not exceed " +
                                "${Constants.MAX_NAME_LENGTH} characters", null
                    )
                )
            )
            return
        }
        val wordItem = Word(name)
        insert(wordItem)
        _insertWordItemStatus.postValue(Event(Resource.success(wordItem)))
    }

    fun deleteItem(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteItem(word)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.android.roomwordsample.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.roomwordsample.network.apiNews.ApiInterface
import com.example.android.roomwordsample.network.apiNews.models.ArticlesModel
import com.example.android.roomwordsample.repositories.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TopStoriesViewModel @Inject constructor() : ViewModel() {

    private val repository = NetworkRepository(ApiInterface())

    private var mutableLiveData = MutableLiveData<ArticlesModel>()
    val liveData: LiveData<ArticlesModel>

    private var mutableTopLiveData = MutableLiveData<ArticlesModel>()
    private val topliveData: LiveData<ArticlesModel>

    init {
        liveData = mutableLiveData
        topliveData = mutableTopLiveData
    }

    fun getInternational(searchWord: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableLiveData.postValue(
                    repository.getInternational(searchWord)
                )
            } catch (e: Exception) {
                Timber.e(e.message!!)
            }
        }
    }

    fun getBusiness() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableLiveData.postValue(
                    repository.getBusiness()
                )
            } catch (e: Exception) {
                Timber.e(e.message!!)
            }
        }
    }

    fun getEntertainment() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableLiveData.postValue(
                    repository.getEntertainment()
                )
            } catch (e: Exception) {
                Timber.e(e.message!!)
            }
        }
    }

    fun getSports() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableLiveData.postValue(
                    repository.getSports()
                )
            } catch (e: Exception) {
                Timber.e(e.message!!)
            }
        }
    }

    fun getScience() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableLiveData.postValue(
                    repository.getScience()
                )
            } catch (e: Exception) {
                Timber.e(e.message!!)
            }
        }
    }

    fun getTechnology() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableLiveData.postValue(
                    repository.getTechnology()
                )
            } catch (e: Exception) {
                Timber.e(e.message!!)
            }
        }
    }

    fun getMedical() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableLiveData.postValue(
                    repository.getMedical()
                )
            } catch (e: Exception) {
                Timber.e(e.message!!)
            }
        }
    }

    fun getTopHeadlines() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableTopLiveData.postValue(
                    repository.getTopHeadlines()
                )
            } catch (e: Exception) {
                Timber.e(e.message!!)
            }
        }
    }

    fun getArticles() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableLiveData.postValue(
                    repository.getArticles()
                )
            } catch (e: Exception) {
                Timber.e(e.message!!)
            }
        }
    }

}
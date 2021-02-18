package com.example.android.roomwordsample.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.roomwordsample.database.NetworkRepository
import com.example.android.roomwordsample.network.apiNews.ApiInterface
import com.example.android.roomwordsample.network.apiNews.ArticlesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopStoriesViewModel : ViewModel() {

    private val repository = NetworkRepository(ApiInterface())

    private var mutableLiveData = MutableLiveData<ArticlesModel>()
    val liveData: LiveData<ArticlesModel>

    private var mutableTopLiveData = MutableLiveData<ArticlesModel>()
    val topliveData: LiveData<ArticlesModel>

    init {
        liveData = mutableLiveData
        topliveData = mutableTopLiveData
    }

    fun getBusiness() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableLiveData.postValue(
                    repository.getBusiness()
                )
            } catch (e: Exception) {
                Log.e("Get Feeds", e.message!!)
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
                Log.e("Get Feeds", e.message!!)
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
                Log.e("Get Feeds", e.message!!)
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
                Log.e("Get Feeds", e.message!!)
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
                Log.e("Get Feeds", e.message!!)
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
                Log.e("Get Feeds", e.message!!)
            }
        }
    }

    fun getInternational(searchWord: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableLiveData.postValue(
                    repository.getInternational(searchWord)
                )
            } catch (e: Exception) {
                Log.e("Get Feeds", e.message!!)
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
                Log.e("Get Feeds", e.message!!)
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
                Log.e("Get Feeds", e.message!!)
            }
        }
    }

//    fun getBookmarks (context: Context) {
//        viewModelScope.launch(Dispatchers.Main) {
//            try {
//                mutableLiveData.postValue(
//                    BookmarkDatabase(context).bookmarkDao().getBookmarks()
//                )
//            }
//            catch (e: Exception) {
//                Log.e("Get Feeds", e.message)
//            }
//        }
//    }


}
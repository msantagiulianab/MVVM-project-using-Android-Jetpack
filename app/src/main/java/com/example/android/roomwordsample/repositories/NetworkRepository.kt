package com.example.android.roomwordsample.repositories

import com.example.android.roomwordsample.network.apiNews.ApiInterface
import com.example.android.roomwordsample.util.SafeApiRequest
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : SafeApiRequest() {

    suspend fun getBusiness() = apiRequest { apiInterface.getBusiness() }

    suspend fun getEntertainment() = apiRequest { apiInterface.getEntertainment() }

    suspend fun getSports() = apiRequest { apiInterface.getSports() }

    suspend fun getScience() = apiRequest { apiInterface.getScience() }

    suspend fun getTechnology() = apiRequest { apiInterface.getTechnology() }

    suspend fun getMedical() = apiRequest { apiInterface.getHealth() }

    suspend fun getInternational(searchWord: String) =
        apiRequest { apiInterface.getInternational(q = searchWord) }

    suspend fun getTopHeadlines() = apiRequest { apiInterface.getTopHeadlines() }

    suspend fun getArticles() = apiRequest { apiInterface.getArticles() }

}
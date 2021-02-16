package com.example.android.roomwordsample.network.apiNews

import com.example.android.roomwordsample.util.Constants.Companion.API_KEY_NEWS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("apiKey")
        apiKey: String = API_KEY_NEWS
    ): Response<NewsResponse>

//    @GET("/everything")
//    fun searchForNews(
//        @Query("q")
//        searchQuery: String,
//        @Query("apiKey")
//        apiKey: String = API_KEY
//    ): Response<NewsResponse>

//    @GET("top-headlines")
//    fun getHeadlines(
//        @Query("country") country: String,
//        @Query("category") category: String,
//        @Query("apiKey") apiKey: String
//    ): Call<NewsJson>

}
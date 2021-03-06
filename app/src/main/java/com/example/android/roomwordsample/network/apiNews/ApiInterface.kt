package com.example.android.roomwordsample.network.apiNews

import com.example.android.roomwordsample.network.apiNews.models.ArticlesModel
import com.example.android.roomwordsample.util.Constants.Companion.API_KEY_NEWS
import com.example.android.roomwordsample.util.Constants.Companion.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    companion object {
        operator fun invoke(): ApiInterface {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }

    @GET("everything")
    suspend fun getInternational(
        @Query("language") language: String = "en",
        @Query("q") q: String = "international",
        @Query("pageSize") pageSize: String = "100",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = API_KEY_NEWS
    ): Response<ArticlesModel>

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "uk",
        @Query("pageSize") pageSize: String = "100",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = API_KEY_NEWS
    ): Response<ArticlesModel>

    @GET("top-headlines")
    suspend fun getArticles(
        @Query("category") category: String = "technology",
        @Query("country") country: String = "uk",
        @Query("pageSize") pageSize: String = "100",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = API_KEY_NEWS
    ): Response<ArticlesModel>


    @GET("top-headlines")
    suspend fun getTechnology(
        @Query("category") category: String = "technology",
        @Query("country") country: String = "uk",
        @Query("pageSize") pageSize: String = "100",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = API_KEY_NEWS
    ): Response<ArticlesModel>

    @GET("top-headlines")
    suspend fun getEntertainment(
        @Query("category") category: String = "entertainment",
        @Query("country") country: String = "uk",
        @Query("pageSize") pageSize: String = "100",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = API_KEY_NEWS
    ): Response<ArticlesModel>

    @GET("top-headlines")
    suspend fun getBusiness(
        @Query("category") category: String = "business",
        @Query("country") country: String = "uk",
        @Query("pageSize") pageSize: String = "100",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = API_KEY_NEWS
    ): Response<ArticlesModel>

    @GET("top-headlines")
    suspend fun getHealth(
        @Query("category") category: String = "health",
        @Query("country") country: String = "uk",
        @Query("pageSize") pageSize: String = "100",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = API_KEY_NEWS
    ): Response<ArticlesModel>

    @GET("top-headlines")
    suspend fun getScience(
        @Query("category") category: String = "science",
        @Query("country") country: String = "uk",
        @Query("pageSize") pageSize: String = "100",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = API_KEY_NEWS
    ): Response<ArticlesModel>

    @GET("top-headlines")
    suspend fun getSports(
        @Query("category") category: String = "sports",
        @Query("country") country: String = "uk",
        @Query("pageSize") pageSize: String = "100",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = API_KEY_NEWS
    ): Response<ArticlesModel>

}
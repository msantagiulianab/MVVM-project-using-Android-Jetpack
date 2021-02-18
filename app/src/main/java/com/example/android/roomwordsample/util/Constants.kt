package com.example.android.roomwordsample.util

class Constants {
    companion object {
        const val API_KEY_NEWS = "858b60d1c1de4643a9bbf74df08ab1a0"
        const val BASE_URL = "https://newsapi.org/v2/"
        const val BASE_URL_CATS = "https://cat-fact.herokuapp.com/"
        const val BASE_URL_MARS = "https://android-kotlin-fun-mars-server.appspot.com/"
        const val NEWS_DATABASE_NAME = "news_db.db"
        const val SEARCH_NEWS_TIME_DELAY = 500L

        const val baseUrl: String = "https://newsapi.org/v2/"
        const val apiKey: String = "apiKey=858b60d1c1de4643a9bbf74df08ab1a0"
        const val country: String = "country"
        const val category: String = "category"
        const val language: String = "language"
        const val topHeadlines: String = "top-headlines?"
        const val everything: String = "everything?"
        const val pageSize: String = "pageSize"
        const val sortBy: String = "sortBy"

        lateinit var query: String

        const val category_business: String = "category=business"
        const val category_entertainment: String = "category=entertainment"
        const val category_general: String = "category=general"
        const val category_health: String = "category=health"
        const val category_science: String = "category=science"
        const val category_sports: String = "category=sports"
        const val category_technology: String = "category=technology"


    }
}
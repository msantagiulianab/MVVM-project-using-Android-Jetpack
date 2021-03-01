package com.example.android.roomwordsample.util

class Constants {
    companion object {
        const val API_KEY_NEWS = "858b60d1c1de4643a9bbf74df08ab1a0"
        const val BASE_URL = "https://newsapi.org/v2/"
        const val DATABASE_NAME = "word_db"
        const val MAX_NAME_LENGTH = 20

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
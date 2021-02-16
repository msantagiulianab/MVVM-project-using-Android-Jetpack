package com.example.android.roomwordsample.network.apiNews

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)
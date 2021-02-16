package com.example.android.roomwordsample.network.apiCats

import com.example.android.roomwordsample.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface CatsApiService {

    @GET("facts/random")
    fun getCatFacts(): Call<CatsJson>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL_CATS)
    .build()

object CatsApi {
    val retrofitService: CatsApiService by lazy {
        retrofit.create(CatsApiService::class.java)
    }
}
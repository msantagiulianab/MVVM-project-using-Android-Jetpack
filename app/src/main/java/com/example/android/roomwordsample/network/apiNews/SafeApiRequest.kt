package com.example.android.roomwordsample.network.apiNews

import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {


        val response = call.invoke()

        if (response.body() != null) {

            return response.body()!!

        } else {
            // todo handle api exception
            throw ApiException(response.code().toString())
        }

    }

    suspend fun <T : Any> apiResponseCode(call: suspend () -> Response<T>): Int {

        val response = call.invoke()

        return response.code()
    }

    class ApiException(message: String) : Exception(message)

}
package com.example.android.roomwordsample.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.android.roomwordsample.R
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object UtilMethods {

    fun isInternetAvailable(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Timber.i("NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Timber.i("NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Timber.i("NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false

    }

    fun convertISOTime(context: Context, isoTime: String?): String? {

        val sdf = SimpleDateFormat(context.getString(R.string.default_time_format))
        var convertedDate: Date? = null
        var formattedDate: String? = null
        var formattedTime: String = "10:00 AM"
        try {
            convertedDate = sdf.parse(isoTime)
            formattedDate =
                SimpleDateFormat(context.getString(R.string.date_format)).format(convertedDate)
            formattedTime =
                SimpleDateFormat(context.getString(R.string.time_format)).format(convertedDate)

            Timber.e(formattedTime)

            if ((formattedTime.subSequence(6, 8).toString() == "PM" || formattedTime.subSequence(
                    6,
                    8
                ).toString() == "pm") && formattedTime.subSequence(0, 2).toString().toInt() > 12
            ) {
                formattedTime = (formattedTime.subSequence(0, 2).toString()
                    .toInt() - 12).toString() + formattedTime.subSequence(2, 8).toString()
            }
            if (formattedTime.subSequence(0, 2).toString() == "00") {
                formattedTime = (formattedTime.subSequence(0, 2).toString()
                    .toInt() + 1).toString() + formattedTime.subSequence(2, 8).toString()

            }
            if (formattedTime.subSequence(0, 2).toString() == "0:") {
                formattedTime = (formattedTime.subSequence(0, 1).toString()
                    .toInt() + 1).toString() + formattedTime.subSequence(2, 8).toString()

            }

        } catch (e: ParseException) {
            e.printStackTrace()
            Timber.e(e.message!!)
        }
        return "$formattedDate | $formattedTime"
    }

    fun hideKeyboard(view: View) {
        val inputManager: InputMethodManager = view.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
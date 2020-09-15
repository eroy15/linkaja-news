package com.linkaja.news.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.linkaja.news.LinkAjaNewsApplication
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = LinkAjaNewsApplication.getInstance()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    fun getStringFormatDate(strDate: String?): String {
        val dateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var convertedDate: Date? = Date()
        var date = ""
        try {
            convertedDate = dateFormat.parse(strDate)
            val sdfnewformat =
                SimpleDateFormat("dd MMMM yyyy")
            date = sdfnewformat.format(convertedDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }
}
package com.ssk.movies.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import com.bumptech.glide.Glide

object Utils {

    /**
     * Utility method to check for network connectivity
     *
     * @param application
     * @return Boolean for the network connectivity status
     */
    fun isNetworkConnected(application: Application): Boolean {
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    /**
     * Extension function to load an image using the Glide library
     *
     * @param url
     */
    fun ImageView.loadImage(url: String) {
        Glide.with(this)
            .load(url)
            .into(this)
    }
}
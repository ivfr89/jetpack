package com.fernandez.rico.jetpatck.data.server

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkHandler
constructor(private val context: Context) {
    val isConnected : Boolean
        get() {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)

            return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
}
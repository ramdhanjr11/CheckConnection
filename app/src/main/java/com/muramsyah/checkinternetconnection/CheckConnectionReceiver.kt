package com.muramsyah.checkinternetconnection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.getSystemService

class CheckConnectionReceiver : BroadcastReceiver() {

    companion object {
        val CHECK_CONNECTION_RECEIVER: String = CheckConnectionReceiver::class.java.simpleName
        const val RESULT_OF_CHECK_CONNECTION = "RESULT_OF_CHECK_CONNECTION"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val isOnline = isOnline(context)
        val intent = Intent(CHECK_CONNECTION_RECEIVER)
        intent.putExtra(RESULT_OF_CHECK_CONNECTION, isOnline)
        context.sendBroadcast(intent)
    }

    private fun isOnline(context: Context) : Boolean {
        var result = false
        val connectivityManager = context.getSystemService<ConnectivityManager>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager?.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            result = when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                val activeNetworkInfo = connectivityManager?.activeNetworkInfo
                activeNetworkInfo != null && activeNetworkInfo.isConnected
            }
        }
        return result
    }
}
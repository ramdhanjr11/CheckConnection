package com.muramsyah.checkinternetconnection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.muramsyah.checkinternetconnection.CheckConnectionReceiver.Companion.CHECK_CONNECTION_RECEIVER
import com.muramsyah.checkinternetconnection.CheckConnectionReceiver.Companion.RESULT_OF_CHECK_CONNECTION

class MainActivity : AppCompatActivity() {

    private lateinit var checkConnectionReceiver: BroadcastReceiver
    private lateinit var checkConnectionResult: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkConnectionReceiver = CheckConnectionReceiver()
        checkConnectionResult = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Toast.makeText(
                    this@MainActivity,
                    "connection : ${intent.getBooleanExtra(RESULT_OF_CHECK_CONNECTION, false)}",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }

        registerReceiver(checkConnectionResult, IntentFilter(CHECK_CONNECTION_RECEIVER))
        registerCheckConnectionReceiver()
    }

    private fun registerCheckConnectionReceiver() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(checkConnectionReceiver, intentFilter)
    }

    private fun unregisterCheckConnectionReceiver() {
        unregisterReceiver(checkConnectionReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterCheckConnectionReceiver()
    }
}
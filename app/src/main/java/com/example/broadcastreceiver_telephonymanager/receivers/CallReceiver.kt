package com.example.broadcastreceiver_telephonymanager.receivers

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.telephony.PhoneStateListener
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine



class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED){
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            if(state == TelephonyManager.EXTRA_STATE_RINGING) {

                val incomingNumber = intent?.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

                val prefs = context?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val numberToReply = prefs?.getString("numberToReply", "")
                val message = prefs?.getString("message", "")

                Log.d("Incoming number: ",incomingNumber.toString())
                Log.d("NUmber to reply: ",("+52"+numberToReply.toString()))

                if(numberToReply!= null && message!=null && incomingNumber.toString()==("+52"+numberToReply.toString())){
                    try {
                        val smsManager = SmsManager.getDefault()
                        smsManager.sendTextMessage("+52"+numberToReply, null, message, null, null)

                    } catch (e: Exception) {
                        Log.e("CallReceiver", "Error sending SMS", e)
                    }
                }
            }
        }
    }
}



package com.example.broadcastreceiver_telephonymanager

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.telephony.TelephonyManager
import com.example.broadcastreceiver_telephonymanager.receivers.CallReceiver

class ServiceTelephony: Service(){
    val broadcastReceiver: CallReceiver = CallReceiver()
    val intentFilter: IntentFilter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)

    // El sistema invoca a este método cuando otro componente quiere enlazarse con el servicio
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    override fun onCreate() {
        super.onCreate()
        registerReceiver(broadcastReceiver, intentFilter)
    }

    // El sistema invoca a este método llamando a startService() cuando otro componente solicita que se inicie el servicio.
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}
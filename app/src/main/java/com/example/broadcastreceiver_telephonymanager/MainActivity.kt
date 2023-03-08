package com.example.broadcastreceiver_telephonymanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextWatcher
import androidx.activity.viewModels
import com.example.broadcastreceiver_telephonymanager.databinding.ActivityMainBinding
import android.text.Editable
import android.view.View
import androidx.core.app.ActivityCompat
import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Switch
import android.widget.Toast
import com.example.broadcastreceiver_telephonymanager.receivers.CallReceiver


class MainActivity : AppCompatActivity() {
    var stateSwitch: Boolean = false
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 1)


        binding.btnUpdate.setOnClickListener {
            changeMessageAndNumber()
        }

        val prefs = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val numberToReply = prefs?.getString("numberToReply", "")
        val message = prefs?.getString("message", "")
        binding.txtNumber.setText(numberToReply)
        binding.txtMessage.setText(message)


        stateSwitch = isMyServiceRunning(ServiceTelephony::class.java)
        binding.isServiceActivedSwitch.isChecked = stateSwitch
        binding.isServiceActivedSwitch.setOnClickListener { view: View? ->
            stateSwitch = binding.isServiceActivedSwitch.isChecked
            if (stateSwitch) {
                val callService = Intent(this, ServiceTelephony::class.java)
                try {
                    startService(callService)
                    Log.d(packageName, "onClick: starting service")
                } catch (ex: Exception) {
                    Log.d(packageName, ex.toString())
                }
            } else {
                stopService(Intent(this, ServiceTelephony::class.java))
                Log.d(packageName, "onClick: stoping service")
            }
        }
    }
    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                Log.d(packageName, "isMyServiceRunning: YES")
                return true
            }
        }
        Log.d(packageName, "isMyServiceRunning: NO")
        return false
    }

    private fun changeMessageAndNumber() {
        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove("numberToReply")
        editor.remove("message")
        editor.apply()
        editor.putString("numberToReply", binding.txtNumber.text.toString())
        editor.putString("message", binding.txtMessage.text.toString())
        editor.apply()
    }
}


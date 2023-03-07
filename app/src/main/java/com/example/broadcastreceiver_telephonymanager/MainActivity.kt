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
import android.content.Context
import android.util.Log
import android.widget.Toast



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 1)


        binding.txtMessage.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.remove("numberToReply")
                editor.remove("message")
                editor.apply()
                editor.putString("numberToReply", binding.txtNumber.text.toString())
                editor.putString("message", binding.txtMessage.text.toString())
                editor.apply()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.txtNumber.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.remove("numberToReply")
                editor.remove("message")
                editor.apply()
                editor.putString("numberToReply", binding.txtNumber.text.toString())
                editor.putString("message", binding.txtMessage.text.toString())
                editor.apply()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {

            }
        })

    }
}


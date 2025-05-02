package com.abhi.headlines360

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize views using findViewById
        val backButton: ImageView = findViewById(R.id.backButton)
        val resetPreferencesButton: Button = findViewById(R.id.resetPreferencesButton)

        // Handle back button click
        backButton.setOnClickListener {
            finish()
        }

        // Handle reset preferences button click
        resetPreferencesButton.setOnClickListener {
            AppPreferences.resetPreferences(this)
            // Restart the app flow
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}
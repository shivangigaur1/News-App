package com.abhi.headlines360

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Set up the back button functionality
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()  // This will take the user back to the previous screen
        }
    }

    // Handle the back button press behavior (optional)
    override fun onBackPressed() {
        super.onBackPressed()  // Default back behavior
    }
}

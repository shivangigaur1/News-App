package com.abhi.headlines360
//
//import android.app.DatePickerDialog
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.Spinner
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.material.textfield.TextInputEditText
//import java.util.Calendar
//
//class SettingsActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_settings)
//
//        // Initialize views using findViewById
//        val backButton: ImageView = findViewById(R.id.backButton)
//        val resetPreferencesButton: Button = findViewById(R.id.resetPreferencesButton)
//
//        // Handle back button click
//        backButton.setOnClickListener {
//            finish()
//        }
//
//        // Handle reset preferences button click
//        resetPreferencesButton.setOnClickListener {
//            AppPreferences.resetPreferences(this)
//            // Restart the app flow
//            val intent = Intent(this, MainActivity::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            }
//            startActivity(intent)
//            finish()
//        }
//    }
//}
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton: ImageView = findViewById(R.id.backButton)
        val resetPreferencesButton: Button = findViewById(R.id.resetPreferencesButton)

        backButton.setOnClickListener { finish() }

        resetPreferencesButton.setOnClickListener {
            // Clear preferences
            AppPreferences.resetPreferences(this)

            // Show confirmation
            Toast.makeText(this, "Preferences reset successfully", Toast.LENGTH_SHORT).show()

            // Restart app properly
            val intent = packageManager.getLaunchIntentForPackage(packageName)?.apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
            finishAffinity()
        }
        
        setupKeywordDateSearch()
    }



    private fun setupKeywordDateSearch() {
        val btnApply = findViewById<Button>(R.id.btnApplyKeywordFilter)
        val keywordInput = findViewById<TextInputEditText>(R.id.etKeyword)
        val dateInput = findViewById<TextInputEditText>(R.id.etDate)

        dateInput.setOnClickListener { showDatePicker(dateInput) }

        btnApply.setOnClickListener {
            val keyword = keywordInput.text.toString()
            val date = dateInput.text.toString()

            if (keyword.isBlank()) {
                Toast.makeText(this, "Please enter a keyword", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidDate(date)) {
                Toast.makeText(this, "Invalid date format (YYYY-MM-DD)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            startActivity(Intent(this, SearchResultsActivity::class.java).apply {
                putExtra("SEARCH_TYPE", "KEYWORD_DATE")
                putExtra("KEYWORD", keyword)
                putExtra("DATE", date)
            })
        }
    }

    private fun isValidDate(date: String): Boolean {
        return date.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))
    }

    private fun showDatePicker(dateInput: TextInputEditText) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, day ->
            dateInput.setText(String.format("%04d-%02d-%02d", year, month + 1, day))
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }
}
//package com.abhi.headlines360
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.Toast
//
//class MainActivity : BaseActivity() {
//    private val selectedCategories = mutableListOf<String>() // Store selected categories
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Initialize buttons for each category
//        val generalButton = findViewById<Button>(R.id.generalButton)
//        val businessButton = findViewById<Button>(R.id.businessButton)
//        val entertainmentButton = findViewById<Button>(R.id.entertainmentButton)
//        val healthButton = findViewById<Button>(R.id.healthButton)
//        val scienceButton = findViewById<Button>(R.id.scienceButton)
//        val sportsButton = findViewById<Button>(R.id.sportsButton)
//        val technologyButton = findViewById<Button>(R.id.technologyButton)
//        val politicsButton = findViewById<Button>(R.id.politicsButton)
//        val environmentButton = findViewById<Button>(R.id.environmentButton)
//        val educationButton = findViewById<Button>(R.id.educationButton)
//        val foodButton = findViewById<Button>(R.id.foodButton)
//        val travelButton = findViewById<Button>(R.id.travelButton)
//        val nextButton = findViewById<Button>(R.id.nextButton)
//
//        // Handle category selection for each button
//        generalButton.setOnClickListener {
//            toggleCategorySelection("general", generalButton)
//        }
//
//        businessButton.setOnClickListener {
//            toggleCategorySelection("business", businessButton)
//        }
//
//        entertainmentButton.setOnClickListener {
//            toggleCategorySelection("entertainment", entertainmentButton)
//        }
//
//        healthButton.setOnClickListener {
//            toggleCategorySelection("health", healthButton)
//        }
//
//        scienceButton.setOnClickListener {
//            toggleCategorySelection("science", scienceButton)
//        }
//
//        sportsButton.setOnClickListener {
//            toggleCategorySelection("sports", sportsButton)
//        }
//
//        technologyButton.setOnClickListener {
//            toggleCategorySelection("technology", technologyButton)
//        }
//
//        politicsButton.setOnClickListener {
//            toggleCategorySelection("politics", politicsButton)
//        }
//
//        environmentButton.setOnClickListener {
//            toggleCategorySelection("environment", environmentButton)
//        }
//
//        educationButton.setOnClickListener {
//            toggleCategorySelection("education", educationButton)
//        }
//
//        foodButton.setOnClickListener {
//            toggleCategorySelection("food", foodButton)
//        }
//
//        travelButton.setOnClickListener {
//            toggleCategorySelection("travel", travelButton)
//        }
//
//        // Navigate to NewsActivity
//        nextButton.setOnClickListener {
//            if (selectedCategories.isEmpty()) {
//                showToast("Please select at least one category")
//            } else {
//                val intent = Intent(this, NewsActivity::class.java)
//                intent.putStringArrayListExtra("CATEGORIES", ArrayList(selectedCategories)) // Pass the list of selected categories
//                startActivity(intent)
//            }
//        }
//        val settingsIcon = findViewById<ImageView>(R.id.settingsIcon)
//        settingsIcon.setOnClickListener {
//            // Open settings activity
//            val intent = Intent(this, SettingsActivity::class.java)
//            startActivity(intent)
//        }
//    }
//
//    // Helper function to toggle category selection
//    private fun toggleCategorySelection(category: String, button: Button) {
//        val originalColor = button.background // Store the original color
//
//        if (selectedCategories.contains(category)) {
//            // Deselect the category
//            selectedCategories.remove(category)
//            button.setBackgroundColor(resources.getColor(android.R.color.transparent)) // Reset to the original color (transparent for reset)
//        } else {
//            // Select the category
//            selectedCategories.add(category)
//            button.setBackgroundColor(resources.getColor(android.R.color.black)) // Change to black color (selected)
//        }
//    }
//
//
//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
//}

package com.abhi.headlines360

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class MainActivity : BaseActivity() {
    private val selectedCategories = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if we should skip category selection
        if (AppPreferences.getLaunchCount(this) >= 5) {
            val topCategories = AppPreferences.getTopCategories(this, 5)
            if (topCategories.isNotEmpty()) {
                startNewsActivity(topCategories)
                finish()
                return
            }
        }

        setContentView(R.layout.activity_main)
        setupUI()
    }

    private fun setupUI() {
        // Initialize all buttons and set click listeners
        val buttons = listOf(
            findViewById<Button>(R.id.generalButton) to "general",
            findViewById<Button>(R.id.businessButton) to "business",
            findViewById<Button>(R.id.entertainmentButton) to "entertainment",
            findViewById<Button>(R.id.healthButton) to "health",
            findViewById<Button>(R.id.scienceButton) to "science",
            findViewById<Button>(R.id.sportsButton) to "sports",
            findViewById<Button>(R.id.technologyButton) to "technology",
            findViewById<Button>(R.id.politicsButton) to "politics",
            findViewById<Button>(R.id.environmentButton) to "environment",
            findViewById<Button>(R.id.educationButton) to "education",
            findViewById<Button>(R.id.foodButton) to "food",
            findViewById<Button>(R.id.travelButton) to "travel"
        )

        buttons.forEach { (button, category) ->
            button.setOnClickListener {
                toggleCategorySelection(category, button)
            }
        }

        findViewById<Button>(R.id.nextButton).setOnClickListener {
            if (selectedCategories.isEmpty()) {
                showToast("Please select at least one category")
            } else {
                // Save selections and increment counts
                AppPreferences.incrementLaunchCount(this)
                selectedCategories.forEach { category ->
                    AppPreferences.incrementCategoryCount(this, category)
                }
                startNewsActivity(selectedCategories)
            }
        }

        findViewById<ImageView>(R.id.settingsIcon).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun startNewsActivity(categories: List<String>) {
        val intent = Intent(this, NewsActivity::class.java).apply {
            putStringArrayListExtra("CATEGORIES", ArrayList(categories))
        }
        startActivity(intent)
    }

    private fun toggleCategorySelection(category: String, button: Button) {
        if (selectedCategories.contains(category)) {
            selectedCategories.remove(category)
            button.setBackgroundColor(resources.getColor(android.R.color.transparent))
        } else {
            selectedCategories.add(category)
            button.setBackgroundColor(resources.getColor(android.R.color.black))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
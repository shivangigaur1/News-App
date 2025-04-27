//package com.abhi.headlines360
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import android.widget.Toast
//
//class MainActivity : BaseActivity() {
//    private var selectedCategory: String? = null
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
//            selectedCategory = "general"
//            showToast("General selected")
//        }
//
//        businessButton.setOnClickListener {
//            selectedCategory = "business"
//            showToast("Business selected")
//        }
//
//        entertainmentButton.setOnClickListener {
//            selectedCategory = "entertainment"
//            showToast("Entertainment selected")
//        }
//
//        healthButton.setOnClickListener {
//            selectedCategory = "health"
//            showToast("Health selected")
//        }
//
//        scienceButton.setOnClickListener {
//            selectedCategory = "science"
//            showToast("Science selected")
//        }
//
//        sportsButton.setOnClickListener {
//            selectedCategory = "sports"
//            showToast("Sports selected")
//        }
//
//        technologyButton.setOnClickListener {
//            selectedCategory = "technology"
//            showToast("Technology selected")
//        }
//
//        politicsButton.setOnClickListener {
//            selectedCategory = "politics"
//            showToast("Politics selected")
//        }
//
//        environmentButton.setOnClickListener {
//            selectedCategory = "environment"
//            showToast("Environment selected")
//        }
//
//        educationButton.setOnClickListener {
//            selectedCategory = "education"
//            showToast("Education selected")
//        }
//
//        foodButton.setOnClickListener {
//            selectedCategory = "food"
//            showToast("Food selected")
//        }
//
//        travelButton.setOnClickListener {
//            selectedCategory = "travel"
//            showToast("Travel selected")
//        }
//
//        // Navigate to NewsActivity
//        nextButton.setOnClickListener {
//            if (selectedCategory == null) {
//                showToast("Please select a category")
//            } else {
//                val intent = Intent(this, NewsActivity::class.java)
//                intent.putExtra("CATEGORY", selectedCategory)
//                startActivity(intent)
//            }
//        }
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
//}
package com.abhi.headlines360

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : BaseActivity() {
    private val selectedCategories = mutableListOf<String>() // Store selected categories

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize buttons for each category
        val generalButton = findViewById<Button>(R.id.generalButton)
        val businessButton = findViewById<Button>(R.id.businessButton)
        val entertainmentButton = findViewById<Button>(R.id.entertainmentButton)
        val healthButton = findViewById<Button>(R.id.healthButton)
        val scienceButton = findViewById<Button>(R.id.scienceButton)
        val sportsButton = findViewById<Button>(R.id.sportsButton)
        val technologyButton = findViewById<Button>(R.id.technologyButton)
        val politicsButton = findViewById<Button>(R.id.politicsButton)
        val environmentButton = findViewById<Button>(R.id.environmentButton)
        val educationButton = findViewById<Button>(R.id.educationButton)
        val foodButton = findViewById<Button>(R.id.foodButton)
        val travelButton = findViewById<Button>(R.id.travelButton)
        val nextButton = findViewById<Button>(R.id.nextButton)

        // Handle category selection for each button
        generalButton.setOnClickListener {
            toggleCategorySelection("general", generalButton)
        }

        businessButton.setOnClickListener {
            toggleCategorySelection("business", businessButton)
        }

        entertainmentButton.setOnClickListener {
            toggleCategorySelection("entertainment", entertainmentButton)
        }

        healthButton.setOnClickListener {
            toggleCategorySelection("health", healthButton)
        }

        scienceButton.setOnClickListener {
            toggleCategorySelection("science", scienceButton)
        }

        sportsButton.setOnClickListener {
            toggleCategorySelection("sports", sportsButton)
        }

        technologyButton.setOnClickListener {
            toggleCategorySelection("technology", technologyButton)
        }

        politicsButton.setOnClickListener {
            toggleCategorySelection("politics", politicsButton)
        }

        environmentButton.setOnClickListener {
            toggleCategorySelection("environment", environmentButton)
        }

        educationButton.setOnClickListener {
            toggleCategorySelection("education", educationButton)
        }

        foodButton.setOnClickListener {
            toggleCategorySelection("food", foodButton)
        }

        travelButton.setOnClickListener {
            toggleCategorySelection("travel", travelButton)
        }

        // Navigate to NewsActivity
        nextButton.setOnClickListener {
            if (selectedCategories.isEmpty()) {
                showToast("Please select at least one category")
            } else {
                val intent = Intent(this, NewsActivity::class.java)
                intent.putStringArrayListExtra("CATEGORIES", ArrayList(selectedCategories)) // Pass the list of selected categories
                startActivity(intent)
            }
        }

    }

    // Helper function to toggle category selection
    private fun toggleCategorySelection(category: String, button: Button) {
        val originalColor = button.background // Store the original color

        if (selectedCategories.contains(category)) {
            // Deselect the category
            selectedCategories.remove(category)
            button.setBackgroundColor(resources.getColor(android.R.color.transparent)) // Reset to the original color (transparent for reset)
        } else {
            // Select the category
            selectedCategories.add(category)
            button.setBackgroundColor(resources.getColor(android.R.color.black)) // Change to black color (selected)
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

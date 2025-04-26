package com.abhi.headlines360

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {
    private var selectedCategory: String? = null  // To track user selection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // ✅ Schedule background worker for breaking news notifications
        //scheduleBreakingNewsWorker()

        // ✅ Trigger Breaking News Worker Immediately (for testing)
        //val testRequest = OneTimeWorkRequestBuilder<BreakingNewsService>().build()
       // WorkManager.getInstance(this).enqueue(testRequest)

        val sportsButton = findViewById<Button>(R.id.sportsButton)
        val politicsButton = findViewById<Button>(R.id.politicsButton)
        val scienceButton = findViewById<Button>(R.id.scienceButton)
        val nextButton = findViewById<Button>(R.id.nextButton)


        // Handle category selection
        sportsButton.setOnClickListener {
            selectedCategory = "sports"
            showToast("Sports selected")
        }

        politicsButton.setOnClickListener {
            selectedCategory = "politics"
            showToast("Politics selected")
        }

        scienceButton.setOnClickListener {
            selectedCategory = "science"
            showToast("Science selected")
        }

        // Navigate to NewsActivity
        nextButton.setOnClickListener {
            if (selectedCategory == null) {
                showToast("Please select a category")
            } else {
                val intent = Intent(this, NewsActivity::class.java)
                intent.putExtra("CATEGORY", selectedCategory)
                startActivity(intent)
            }
        }
    }

//    private fun scheduleBreakingNewsWorker() {
//        val workRequest = PeriodicWorkRequestBuilder<BreakingNewsService>(15, TimeUnit.MINUTES)
//            .build()
//
//        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
//            "BreakingNewsWork",
//            ExistingPeriodicWorkPolicy.KEEP,
//            workRequest
//        )
//    }
    private fun showToast(message: String) {
      Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
   }
}




package com.abhi.headlines360

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class NewsActivity : BaseActivity() {

    private lateinit var newsService: NewsService
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        // Get selected categories from intent
        val selectedCategories = intent.getStringArrayListExtra("CATEGORIES") ?: arrayListOf("general")

        // Initialize Views
        tabLayout = findViewById(R.id.tabLayoutCategories)
        viewPager = findViewById(R.id.viewPagerCategories)

        // Initialize NewsService
        newsService = ApiClient.instance

        // Setup ViewPager2 with CategoryAdapter
        setupViewPager(selectedCategories)
    }

    private fun setupViewPager(categories: List<String>) {
        val adapter = CategoryAdapter(categories) { category ->
            // Fetch and update the news for the selected category
            fetchNews(category)
        }
        viewPager.adapter = adapter

        // Setup TabLayout to show category titles above the pages
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = categories[position] // Set the tab text as the category name
        }.attach()

        // Refresh the news every time a tab is selected
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Fetch news for the selected category
                fetchNews(categories[position])
            }
        })
    }

    private fun fetchNews(category: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Get the news for the selected category
                val response = newsService.getNews(category, "en", NewsService.API_KEY).execute()
                if (response.isSuccessful && response.body() != null) {
                    val newsResponse = response.body()!!

                    // Update RecyclerView with the fetched articles
                    withContext(Dispatchers.Main) {
                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewNews)
                        recyclerView.layoutManager = LinearLayoutManager(this@NewsActivity)

                        // Check if newsAdapter is null or already initialized
                        val newsAdapter = NewsAdapter(newsResponse.articles, this@NewsActivity::openArticle, this@NewsActivity)
                        recyclerView.adapter = newsAdapter
                    }
                } else {
                    showToast("Failed to load news for $category")
                }
            } catch (e: IOException) {
                showToast("Network error, check your connection")
            } catch (e: HttpException) {
                showToast("Server error")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Method to open article URL when clicked
    private fun openArticle(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
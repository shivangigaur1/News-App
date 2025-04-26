package com.abhi.headlines360

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate

class NewsActivity : BaseActivity() {

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsService: NewsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        // Get selected category from intent
        val category = intent.getStringExtra("CATEGORY") ?: "general"

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewNews)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize NewsAdapter with TTS support
        newsAdapter = NewsAdapter(emptyList(), this::openArticle, this)
        recyclerView.adapter = newsAdapter
        newsService = ApiClient.instance

        // Fetch news based on the selected category
        fetchNews(category)
    }

    private fun fetchNews(category: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val today = LocalDate.now().toString()
                val response = newsService.getNews(category,"en", NewsService.API_KEY).execute()
                if (response.isSuccessful && response.body() != null) {
                    val newsResponse = response.body()!!

                    withContext(Dispatchers.Main) {
                        newsAdapter.updateNews(newsResponse.articles)
                    }
                } else {
                    Log.e("NewsActivity", "Error: ${response.code()} - ${response.message()}")
                    withContext(Dispatchers.Main) {
                        showToast("Failed to load news")
                    }
                }
            } catch (e: IOException) {
                Log.e("NewsActivity", "Network error: ${e.localizedMessage}", e)
                withContext(Dispatchers.Main) { showToast("Network error, check your connection") }
            } catch (e: HttpException) {
                Log.e("NewsActivity", "HTTP error: ${e.localizedMessage}", e)
                withContext(Dispatchers.Main) { showToast("Server error") }
            }
        }
    }

    private fun openArticle(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Shutdown TTS when activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
        newsAdapter.shutdownTTS()
    }
}

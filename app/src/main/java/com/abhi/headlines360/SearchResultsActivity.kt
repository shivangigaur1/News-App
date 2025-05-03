package com.abhi.headlines360

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultsActivity : AppCompatActivity() {
    private lateinit var newsAdapter: NewsAdapter
    private val apiService = ApiClient.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        // Initialize NewsAdapter with proper URL handling
        newsAdapter = NewsAdapter(emptyList(), { url ->
            try {
                if (url.isNotBlank() && url.startsWith("http")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Invalid article URL", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Cannot open article", Toast.LENGTH_SHORT).show()
            }
        }, this)

        // Setup RecyclerView
        findViewById<RecyclerView>(R.id.recyclerView).apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@SearchResultsActivity)
        }

        // Handle search type
        when (intent.getStringExtra("SEARCH_TYPE")) {
            "KEYWORD_DATE" -> searchByKeywordDate()
            else -> finish()
        }
    }

    private fun searchByKeywordDate() {
        val keyword = intent.getStringExtra("KEYWORD") ?: ""
        val date = intent.getStringExtra("DATE") ?: ""

        apiService.searchByKeywordAndDate(keyword, date).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    response.body()?.articles?.let { articles ->
                        // Filter valid articles
                        val validArticles = articles.filter {
                            it.url.isNotBlank() && it.url.startsWith("http")
                        }

                        if (validArticles.isNotEmpty()) {
                            newsAdapter.updateNews(validArticles)
                        } else {
                            Toast.makeText(
                                this@SearchResultsActivity,
                                "No articles with valid URLs found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        this@SearchResultsActivity,
                        "API Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(
                    this@SearchResultsActivity,
                    "Network Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        newsAdapter.shutdownTTS()
    }
}
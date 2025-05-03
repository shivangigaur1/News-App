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

        newsAdapter = NewsAdapter(emptyList(), { url ->
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).takeIf { it.resolveActivity(packageManager) != null }?.let {
                startActivity(it)
            } ?: Toast.makeText(this, "No app to handle this request", Toast.LENGTH_SHORT).show()
        }, this)

        findViewById<RecyclerView>(R.id.recyclerView).apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@SearchResultsActivity)
        }

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
                    response.body()?.articles?.let { newsAdapter.updateNews(it) }
                } else {
                    Toast.makeText(this@SearchResultsActivity, "API Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(this@SearchResultsActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }






    override fun onDestroy() {
        super.onDestroy()
        newsAdapter.shutdownTTS()
    }
}
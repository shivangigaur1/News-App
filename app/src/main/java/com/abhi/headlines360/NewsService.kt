package com.abhi.headlines360

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("everything") // 🔹 Uses `everything` for full news search
    fun getNews(
        @Query("q") query: String, // 🔹 Use `q` for searching news based on keyword
        //@Query("from") from: String,
        //@Query("country") country: String = "us",
        //@Query("sortBy") sortBy: String = "publishedAt",
        @Query("language") language: String = "en", // 🔹 Filter by language (English)
        @Query("apiKey") apiKey: String = API_KEY // 🔹 Secure API Key
    ): Call<NewsResponse>

    companion object {
        const val API_KEY = "b27c40eb1f4f47c1a999383670f62722" // Replace with your actual API Key
    }
}

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
        const val API_KEY = "7e6d6b39f4cd44d0a3241cba601b06ef" // Replace with your actual API Key
    }
}

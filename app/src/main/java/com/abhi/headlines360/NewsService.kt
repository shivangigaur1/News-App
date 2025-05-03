package com.abhi.headlines360

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//interface NewsService {
//    @GET("everything") // 🔹 Uses `everything` for full news search
//    fun getNews(
//        @Query("q") query: String, // 🔹 Use `q` for searching news based on keyword
//        //@Query("from") from: String,
//        //@Query("country") country: String = "us",
//        //@Query("sortBy") sortBy: String = "publishedAt",
//        @Query("language") language: String = "en", // 🔹 Filter by language (English)
//        @Query("apiKey") apiKey: String = API_KEY // 🔹 Secure API Key
//    ): Call<NewsResponse>
//
//    companion object {
//        const val API_KEY = "b27c40eb1f4f47c1a999383670f62722" // Replace with your actual API Key
//    }
//}

//interface NewsService {
//    // Existing endpoint (keep this)
//    @GET("everything")
//    fun getNews(
//        @Query("q") query: String,
//        @Query("language") language: String = "en",
//        @Query("apiKey") apiKey: String = API_KEY
//    ): Call<NewsResponse>
//
//    // New endpoints for search functionality
//    @GET("everything")
//    fun searchByKeywordAndDate(
//        @Query("q") keyword: String,
//        @Query("from") date: String,
//        @Query("sortBy") sortBy: String = "publishedAt",
//        @Query("language") language: String = "en",
//        @Query("apiKey") apiKey: String = API_KEY
//    ): Call<NewsResponse>
//
//    @GET("everything")
//    fun searchByCountryAndKeyword(
//        @Query("country") country: String,
//        @Query("q") keyword: String,
//        @Query("language") language: String = "en",
//        @Query("apiKey") apiKey: String = API_KEY
//    ): Call<NewsResponse>
//
//    companion object {
//        const val API_KEY = "b27c40eb1f4f47c1a999383670f62722"
//    }
//}

interface NewsService {
    // Existing endpoints (keep these)
    @GET("everything")
    fun getNews(
        @Query("q") query: String,
        @Query("language") language: String = "en",
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<NewsResponse>

    @GET("everything")
    fun searchByKeywordAndDate(
        @Query("q") keyword: String,
        @Query("from") date: String,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("language") language: String = "en",
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<NewsResponse>

    companion object {
        const val API_KEY = "b27c40eb1f4f47c1a999383670f62722"
    }
}
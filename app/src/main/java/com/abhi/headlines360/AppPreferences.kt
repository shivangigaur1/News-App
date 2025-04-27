package com.abhi.headlines360

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val PREFS_NAME = "NewsAppPrefs"
    private const val KEY_LAUNCH_COUNT = "launch_count"
    private const val KEY_CATEGORY_PREFIX = "category_"

    fun getLaunchCount(context: Context): Int {
        val prefs = getSharedPrefs(context)
        return prefs.getInt(KEY_LAUNCH_COUNT, 0)
    }

    fun incrementLaunchCount(context: Context) {
        val prefs = getSharedPrefs(context)
        prefs.edit().putInt(KEY_LAUNCH_COUNT, getLaunchCount(context) + 1).apply()
    }

    fun incrementCategoryCount(context: Context, category: String) {
        val prefs = getSharedPrefs(context)
        val key = KEY_CATEGORY_PREFIX + category
        val currentCount = prefs.getInt(key, 0)
        prefs.edit().putInt(key, currentCount + 1).apply()
    }

    fun getTopCategories(context: Context, limit: Int): List<String> {
        val prefs = getSharedPrefs(context)
        val categoryCounts = mutableMapOf<String, Int>()

        prefs.all.forEach { (key, value) ->
            if (key.startsWith(KEY_CATEGORY_PREFIX)) {
                val category = key.removePrefix(KEY_CATEGORY_PREFIX)
                if (value is Int) {
                    categoryCounts[category] = value
                }
            }
        }

        return categoryCounts.entries
            .sortedByDescending { it.value }
            .take(limit)
            .map { it.key }
    }

    private fun getSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
}
package com.abhi.headlines360

import androidx.appcompat.app.AppCompatDelegate

object ThemeUtils {
    fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    fun enableLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}

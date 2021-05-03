package com.gs.weather.utils

import androidx.appcompat.app.AppCompatDelegate
import com.gs.weather.utils.Theme.LIGHT
import com.gs.weather.utils.Theme.NIGHT

object ThemeUtils {
    fun setAppTheme(theme: Theme) {
        when (theme) {
            LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            NIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}
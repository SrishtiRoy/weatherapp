package com.gs.weather

import android.app.Application
import com.gs.weather.data.AppSettings
import com.gs.weather.di.appModule
import com.gs.weather.di.networkModule
import com.gs.weather.di.uiModule
import com.gs.weather.utils.ThemeUtils
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Set Up Koin DI
        startKoin {
            androidContext(this@AndroidApplication)
            modules(appModule, networkModule, uiModule)
        }

        // Set Up App Theme
        val settings by inject<AppSettings>()
        ThemeUtils.setAppTheme(settings.theme)
    }
}
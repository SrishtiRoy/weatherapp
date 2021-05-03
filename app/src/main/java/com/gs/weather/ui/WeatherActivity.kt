package com.gs.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.gs.weather.R
import com.gs.weather.base.BaseFragment
import com.gs.weather.ui.search.SearchFragment
import com.gs.weather.ui.weather.WeatherFragment
import com.gs.weather.utils.extension.observe
import org.koin.android.ext.android.inject

class WeatherActivity : AppCompatActivity() {
    private val viewModel: WeatherViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null)
            observe(viewModel.isAppFirstLaunched, ::showNextScreen)
    }

    /**
     * If app is launched for the first time, then navigates to [SearchFragment] to pick the location.
     *
     * If not - opens [WeatherFragment].
     */
    private fun showNextScreen(isAppFirstLaunched: Boolean) {
        val fragment: BaseFragment = if (isAppFirstLaunched) {
            SearchFragment.newInstance()
        } else {
            WeatherFragment.newInstance()
        }
        supportFragmentManager.commitNow {
            replace(R.id.mainContainer, fragment)
        }
    }
}
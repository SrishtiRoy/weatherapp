package com.gs.weather.ui.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.*
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.gs.weather.R
import com.gs.weather.base.BaseFragment
import com.gs.weather.data.entity.Location
import com.gs.weather.ui.WeatherViewModel
import com.gs.weather.ui.weather.WeatherFragment
import com.gs.weather.utils.exception.BadServerResponse
import com.gs.weather.utils.exception.LocationsNotFound
import com.gs.weather.utils.exception.NoConnection
import com.gs.weather.utils.extension.hide
import com.gs.weather.utils.extension.observe
import com.gs.weather.utils.extension.show
import com.gs.weather.utils.extension.snackbarShort
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.android.synthetic.main.search_hint_enter_text.*
import kotlinx.android.synthetic.main.search_hint_no_internet.*
import kotlinx.android.synthetic.main.search_hint_no_locations.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class SearchFragment : BaseFragment() {
    override val layoutId = R.layout.search_fragment
    private val viewModel: SearchViewModel by inject()
    private val activityViewModel: WeatherViewModel by sharedViewModel()
    private val searchAdapter: SearchAdapter by inject {
        parametersOf(::onLocationChange)
    }

    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            observe(searchedLocations, ::renderData)
            observe(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setFocusOnInput()
        showHint(enterTextHint)
        searchCityEditText.doOnTextChanged { text, _, _, _ ->
            if (text != null && text.length > 1)
                viewModel.getLocationsByName(text.toString())
        }
    }

    /**
     * Displays found locations
     */
    private fun renderData(locations: List<Location>) {
        if (locations.isEmpty()) {
            handleFailure(LocationsNotFound)
        } else {
            hints.hide()
            locationList.show()
            searchAdapter.submitList(locations)
        }
    }

    /**
     * Shows [hintToShow] and hides previous data with other hints
     */
    private fun showHint(hintToShow: View) {
        locationList.hide()
        hints.show()
        hints.children.forEach {
            if (it != hintToShow)
                it.hide()
            else
                it.show()
        }
    }

    /**
     * Handles with failures that may occur while loading data
     */
    private fun handleFailure(failure: Exception) {
        when (failure) {
            is LocationsNotFound -> showHint(noLocationsHint)
            is NoConnection -> showHint(noInternetHint)
            is BadServerResponse -> snackbarShort(rootLayout, R.string.failure_server_error)
        }
    }

    /**
     * Loads new forecast by given [location], closes keyboard and opens new screen
     */
    private fun onLocationChange(location: Location) {
        activityViewModel.fetchForecast(location)
        hideKeyboard()
        if (isAppFirstLaunched()) {
            showMainScreen()
        } else {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setUpRecyclerView() {
        locationList.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            adapter = searchAdapter
            setHasFixedSize(true)
        }
    }

    /**
     * Sets focus on [searchCityEditText] and opens keyboard
     */
    private fun setFocusOnInput() {
        searchCityEditText.requestFocus()
        val inputMethodManager =
            requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(SHOW_FORCED, HIDE_IMPLICIT_ONLY)
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            searchCityEditText.windowToken,
            RESULT_UNCHANGED_SHOWN
        )
    }

    private fun isAppFirstLaunched(): Boolean = parentFragmentManager.backStackEntryCount == 0

    private fun showMainScreen() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainContainer, WeatherFragment.newInstance())
            .commitNow()
    }
}
package com.gs.weather.ui.settings

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.gs.weather.BuildConfig
import com.gs.weather.R
import com.gs.weather.base.BaseFragment
import com.gs.weather.databinding.SettingsFragmentBinding
import com.gs.weather.ui.WeatherViewModel
import com.gs.weather.utils.Pressure
import com.gs.weather.utils.Temperature
import com.gs.weather.utils.Theme
import com.gs.weather.utils.Wind
import com.gs.weather.utils.extension.openLink
import com.gs.weather.views.SettingsItem
import kotlinx.android.synthetic.main.settings_fragment.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SettingsFragment : BaseFragment() {
    override val layoutId = R.layout.settings_fragment
    private val viewModel: SettingsViewModel by inject()
    private val activityViewModel: WeatherViewModel by sharedViewModel()

    private lateinit var binding: SettingsFragmentBinding

    companion object {
        const val TAG = "SettingsFragment"
        fun newInstance() = SettingsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SettingsFragmentBinding.bind(view)
        setUpAppearanceSection()

    }

    private fun setUpAppearanceSection() {
        binding.themes.setValue(viewModel.theme)
        binding.themes.setOnClickListener {
            showDialog(
                titleId = R.string.theme_title,
                items = viewModel.availableThemes,
                valueToUpdate = binding.themes
            ) { item: Theme ->
                viewModel.updateTheme(item)
            }
        }
    }


    private inline fun <T> showDialog(
        @StringRes titleId: Int,
        items: Array<T>,
        valueToUpdate: SettingsItem,
        crossinline onSelect: (item: T) -> Unit
    ) {
        MaterialDialog(requireContext()).show {
            title(titleId)
            listItems(items = items.map { it.toString() }) { _, index, text ->
                valueToUpdate.setValue(text.toString())
                onSelect(items[index])
                activityViewModel.applyNewUnits()
            }
        }
    }
}
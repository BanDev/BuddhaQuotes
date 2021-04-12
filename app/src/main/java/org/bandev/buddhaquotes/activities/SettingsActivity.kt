/**

Buddha Quotes
Copyright (C) 2021  BanDev

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

package org.bandev.buddhaquotes.activities

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.content.ContextCompat.getColor
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.maxkeppeler.sheets.color.ColorSheet
import com.maxkeppeler.sheets.color.ColorView
import com.maxkeppeler.sheets.core.SheetStyle
import com.maxkeppeler.sheets.options.DisplayMode
import com.maxkeppeler.sheets.options.Option
import com.maxkeppeler.sheets.options.OptionsSheet
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial
import com.mikepenz.iconics.typeface.library.octicons.Octicons
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.ActivitySettingsBinding

/**
 * Where the user can customise their app
 */
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set theme, navigation bar and language
        setAccentColour(this)
        window.setStatusBarAsAccentColour(this)
        window.setNavigationBarColourDefault(this)
        Languages(baseContext).setLanguage()

        // Setup view binding
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        with(binding.toolbar) {
            navigationIcon =
                IconicsDrawable(context, RoundedGoogleMaterial.Icon.gmr_arrow_back).apply {
                    colorInt = Color.WHITE
                    sizeDp = 16
                }
            setBackgroundColor(toolbarColour(context))
            setNavigationOnClickListener { onBackPressed() }
        }

        if ((intent.extras ?: return).getBoolean("switch")) {
            val sharedPrefs = getSharedPreferences("Settings", 0)
            val darkmode = sharedPrefs.getBoolean("dark_mode", false)
            val sys = sharedPrefs.getBoolean("sys", true)
            when {
                sys -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                darkmode -> setDefaultNightMode(MODE_NIGHT_YES)
                else -> setDefaultNightMode(MODE_NIGHT_NO)
            }
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            findPreference<Preference>("app_language")?.apply {
                icon = IconicsDrawable(
                    requireContext(),
                    RoundedGoogleMaterial.Icon.gmr_language
                ).apply {
                    colorInt = getColor(requireContext(), R.color.textColorPrimary)
                    sizeDp = 20
                }
                val int = Languages(base = context).getLanguageAsInt(requireContext())
                val singleItems = resources.getStringArray(R.array.language_entries)
                summary = singleItems[int]
                onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
                    val editor = sharedPrefs.edit()
                    val values = resources.getStringArray(R.array.language_values)
                    val defaultDrawable =
                        IconicsDrawable(requireContext(), Octicons.Icon.oct_code_review)
                    val machineDrawable = IconicsDrawable(requireContext(), Octicons.Icon.oct_cpu)

                    OptionsSheet().show(requireContext()) {
                        title(R.string.settings_language)
                        style(SheetStyle.DIALOG)
                        displayMode(DisplayMode.LIST)
                        with(
                            Option(machineDrawable, R.string.zh),
                            Option(defaultDrawable, R.string.en),
                            Option(machineDrawable, R.string.fr),
                            Option(machineDrawable, R.string.de),
                            Option(machineDrawable, R.string.hi),
                            Option(machineDrawable, R.string.ja),
                            Option(R.drawable.ic_language, R.string.pl),
                            Option(machineDrawable, R.string.ru),
                            Option(machineDrawable, R.string.es)
                        )
                        onPositive { index: Int, _: Option ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) requireView().performHapticFeedback(
                                HapticFeedbackConstants.CONFIRM
                            )
                            else requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)

                            if (Languages(base = context).getLanguageAsInt(requireContext()) != index) {
                                editor.putInt("app_language_int", index)
                                editor.putString("app_language", values[index])
                                editor.apply()
                                startActivity(
                                    Intent(context, SettingsActivity::class.java).putExtra(
                                        "lang",
                                        true
                                    )
                                )
                                activity?.finish()
                                activity?.overridePendingTransition(
                                    android.R.anim.fade_in,
                                    android.R.anim.fade_out
                                )
                            } else dismiss()
                        }
                    }
                    true
                }
            }

            findPreference<Preference>("theme")?.apply {
                val lightModeDrawable =
                    IconicsDrawable(
                        requireContext(),
                        RoundedGoogleMaterial.Icon.gmr_wb_sunny
                    ).apply {
                        colorInt = getColor(requireContext(), R.color.textColorPrimary)
                        sizeDp = 20
                    }
                val darkModeDrawable = IconicsDrawable(
                    requireContext(),
                    RoundedGoogleMaterial.Icon.gmr_nights_stay
                ).apply {
                    colorInt = getColor(requireContext(), R.color.textColorPrimary)
                    sizeDp = 20
                }
                val systemDefaultDrawable = IconicsDrawable(
                    requireContext(),
                    RoundedGoogleMaterial.Icon.gmr_brightness_medium
                ).apply {
                    colorInt = getColor(requireContext(), R.color.textColorPrimary)
                    sizeDp = 20
                }
                summary = when (getAppTheme(requireContext())) {
                    0 -> {
                        icon = lightModeDrawable
                        getString(R.string.light_mode)
                    }
                    1 -> {
                        icon = darkModeDrawable
                        getString(R.string.dark_mode)
                    }
                    else -> {
                        icon = systemDefaultDrawable
                        getString(R.string.follow_system_default)
                    }
                }

                val sharedPrefs = requireContext().getSharedPreferences("Settings", 0)
                val editor = sharedPrefs.edit()
                onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    OptionsSheet().show(requireContext()) {
                        title(R.string.app_theme)
                        style(SheetStyle.DIALOG)
                        displayMode(DisplayMode.LIST)
                        with(
                            Option(lightModeDrawable, R.string.light_mode),
                            Option(darkModeDrawable, R.string.dark_mode),
                            Option(systemDefaultDrawable, R.string.follow_system_default)
                        )
                        onNegative { requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }
                        onPositive { index: Int, _: Option ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) requireView().performHapticFeedback(
                                HapticFeedbackConstants.CONFIRM
                            )
                            else requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            if (getAppTheme(requireContext()) != index) {
                                when (index) {
                                    0 -> {
                                        // Light mode
                                        editor.putBoolean("dark_mode", false)
                                        editor.putBoolean("sys", false)
                                        editor.putInt("appThemeInt", 0)
                                    }
                                    1 -> {
                                        // Dark mode
                                        editor.putBoolean("dark_mode", true)
                                        editor.putBoolean("sys", false)
                                        editor.putInt("appThemeInt", 1)
                                    }
                                    2 -> {
                                        // System default
                                        editor.putBoolean("dark_mode", false)
                                        editor.putBoolean("sys", true)
                                        editor.putInt("appThemeInt", 2)
                                    }
                                }
                                editor.apply()
                                startActivity(
                                    Intent(context, SettingsActivity::class.java).putExtra(
                                        "switch",
                                        true
                                    )
                                )
                                activity?.finish()
                                activity?.overridePendingTransition(0, 0)
                            } else dismiss()
                        }
                    }
                    true
                }
            }

            findPreference<Preference>("accent_color")?.apply {
                val paletteDrawable =
                    IconicsDrawable(
                        requireContext(),
                        RoundedGoogleMaterial.Icon.gmr_palette
                    ).apply {
                        colorInt = getColor(requireContext(), R.color.textColorPrimary)
                        sizeDp = 20
                    }
                icon = paletteDrawable
                summary = when (getAccentColourAsString(requireContext())) {
                    "pink" -> getString(R.string.pink)
                    "violet" -> getString(R.string.violet)
                    "blue" -> getString(R.string.blue)
                    "lightBlue" -> getString(R.string.lightBlue)
                    "teal" -> getString(R.string.teal)
                    "green" -> getString(R.string.green)
                    "lime" -> getString(R.string.lime)
                    "yellow" -> getString(R.string.yellow)
                    "orange" -> getString(R.string.orange)
                    "red" -> getString(R.string.red)
                    "crimson" -> getString(R.string.crimson)
                    else -> getString(R.string.original)
                }
                onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    ColorSheet().show(requireContext()) {
                        colors(
                            mutableListOf(
                                R.color.pinkAccent,
                                R.color.violetAccent,
                                R.color.blueAccent,
                                R.color.lightBlueAccent,
                                R.color.tealAccent,
                                R.color.greenAccent,
                                R.color.limeAccent,
                                R.color.yellowAccent,
                                R.color.orangeAccent,
                                R.color.redAccent,
                                R.color.crimsonAccent,
                                R.color.colorPrimary
                            )
                        )
                        title(R.string.settings_accent_colour)
                        style(SheetStyle.DIALOG)
                        defaultView(ColorView.TEMPLATE)
                        disableSwitchColorView()
                        onNegative { requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }
                        onPositive { color ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) requireView().performHapticFeedback(
                                HapticFeedbackConstants.CONFIRM
                            )
                            else requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)

                            // Checks if the chosen color is not the same as the current color
                            if (requireContext().resolveColorAttr(R.attr.colorPrimary) != color) {
                                val colorOut = when (color) {
                                    getColor(requireContext(), R.color.pinkAccent) -> "pink"
                                    getColor(requireContext(), R.color.violetAccent) -> "violet"
                                    getColor(requireContext(), R.color.blueAccent) -> "blue"
                                    getColor(requireContext(), R.color.lightBlueAccent) -> "lightBlue"
                                    getColor(requireContext(), R.color.tealAccent) -> "teal"
                                    getColor(requireContext(), R.color.greenAccent) -> "green"
                                    getColor(requireContext(), R.color.limeAccent) -> "lime"
                                    getColor(requireContext(), R.color.yellowAccent) -> "yellow"
                                    getColor(requireContext(), R.color.orangeAccent) -> "orange"
                                    getColor(requireContext(), R.color.redAccent) -> "red"
                                    getColor(requireContext(), R.color.crimsonAccent) -> "crimson"
                                    else -> "original"
                                }

                                PreferenceManager.getDefaultSharedPreferences(context)
                                    .edit()
                                    .putString("accent_color", colorOut)
                                    .apply()

                                startActivity(
                                    Intent(context, SettingsActivity::class.java).putExtra(
                                        "switch",
                                        true
                                    )
                                )
                                activity?.finish()
                                activity?.overridePendingTransition(
                                    android.R.anim.fade_in,
                                    android.R.anim.fade_out
                                )
                            } else dismiss()
                        }
                    }
                    true
                }
            }

            findPreference<Preference>("about")?.apply {
                icon =
                    IconicsDrawable(requireContext(), RoundedGoogleMaterial.Icon.gmr_info).apply {
                        colorInt = getColor(requireContext(), R.color.textColorPrimary)
                        sizeDp = 20
                    }
                onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    startActivity(Intent(activity, AboutActivity::class.java))
                    true
                }
            }

            findPreference<Preference>("about_libraries")?.apply {
                icon = IconicsDrawable(
                    requireContext(),
                    RoundedGoogleMaterial.Icon.gmr_library_books
                ).apply {
                    colorInt = getColor(requireContext(), R.color.textColorPrimary)
                    sizeDp = 20
                }
                onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    startActivity(Intent(activity, LibrariesActivity::class.java))
                    true
                }
            }
        }
    }
}
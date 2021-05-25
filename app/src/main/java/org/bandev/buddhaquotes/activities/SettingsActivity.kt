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
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.content.ContextCompat.getColor
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.akexorcist.localizationactivity.core.LanguageSetting.getLanguage
import com.akexorcist.localizationactivity.core.LanguageSetting.setLanguage
import com.akexorcist.localizationactivity.ui.LocalizationActivity
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
import java.util.*

/**
 * Where the user can customise their app
 */
class SettingsActivity : LocalizationActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set theme, navigation bar and language
        setAccentColour(this)
        window.setStatusBarAsAccentColour(this)
        window.setNavigationBarColourDefault(this)

        // Setup view binding
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        with(binding.toolbar) {
            navigationIcon = context.backIcon()
            setBackgroundColor(toolbarColour(context))
            setNavigationOnClickListener { onBackPressed() }
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        private lateinit var sharedPrefs: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            editor = sharedPrefs.edit()

            findPreference<Preference>("app_language")?.apply {
                icon = IconicsDrawable(
                    requireContext(),
                    RoundedGoogleMaterial.Icon.gmr_language
                ).apply {
                    colorInt = context.resolveColorAttr(android.R.attr.textColorPrimary)
                    sizeDp = 20
                }
                summary = getLanguage(requireContext()).toString()
                onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    val defaultDrawable =
                        IconicsDrawable(requireContext(), Octicons.Icon.oct_code_review)
                    val machineDrawable = IconicsDrawable(requireContext(), Octicons.Icon.oct_cpu)
                    val currentLangauge = getLanguage(requireContext())
                    OptionsSheet().show(requireContext()) {
                        title(R.string.settings_language)
                        style(SheetStyle.DIALOG)
                        displayMode(DisplayMode.LIST)
                        with(
                            Option(defaultDrawable, R.string.en),
                            Option(machineDrawable, R.string.zh),
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
                            ) else requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            when (index) {
                                0 -> setLanguage(requireContext(), Locale("en"))
                                1 -> setLanguage(requireContext(), Locale("zh"))
                                2 -> setLanguage(requireContext(), Locale("fr"))
                                3 -> setLanguage(requireContext(), Locale("de"))
                                4 -> setLanguage(requireContext(), Locale("hi"))
                                5 -> setLanguage(requireContext(), Locale("ja"))
                                6 -> setLanguage(requireContext(), Locale("pl"))
                                7 -> setLanguage(requireContext(), Locale("ru"))
                                8 -> setLanguage(requireContext(), Locale("es"))
                            }
                            if (getLanguage(requireContext()) != currentLangauge) {
                                startActivity(Intent(context, SettingsActivity::class.java))
                                activity?.finish()
                                activity?.overridePendingTransition(
                                    android.R.anim.fade_in,
                                    android.R.anim.fade_out
                                )
                            }
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
                        colorInt = context.resolveColorAttr(android.R.attr.textColorPrimary)
                        sizeDp = 20
                    }
                val darkModeDrawable = IconicsDrawable(
                    requireContext(),
                    RoundedGoogleMaterial.Icon.gmr_nights_stay
                ).apply {
                    colorInt = context.resolveColorAttr(android.R.attr.textColorPrimary)
                    sizeDp = 20
                }
                val systemDefaultDrawable = IconicsDrawable(
                    requireContext(),
                    RoundedGoogleMaterial.Icon.gmr_brightness_medium
                ).apply {
                    colorInt = context.resolveColorAttr(android.R.attr.textColorPrimary)
                    sizeDp = 20
                }

                val appTheme = sharedPrefs.getInt("appThemeInt", 2)
                summary = when (appTheme) {
                    0 -> getString(R.string.light_mode)
                    1 -> getString(R.string.dark_mode)
                    else -> getString(R.string.follow_system_default)
                }
                icon = when (appTheme) {
                    0 -> lightModeDrawable
                    1 -> darkModeDrawable
                    else -> systemDefaultDrawable
                }

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
                            setDefaultNightMode(
                                when (index) {
                                    0 -> MODE_NIGHT_NO
                                    1 -> MODE_NIGHT_YES
                                    else -> MODE_NIGHT_FOLLOW_SYSTEM
                                }
                            )
                            if (index != sharedPrefs.getInt("appThemeInt", 2)) {
                                summary = when (index) {
                                    0 -> getString(R.string.light_mode)
                                    1 -> getString(R.string.dark_mode)
                                    else -> getString(R.string.follow_system_default)
                                }
                                icon = when (index) {
                                    0 -> lightModeDrawable
                                    1 -> darkModeDrawable
                                    else -> systemDefaultDrawable
                                }
                                editor.putInt("appThemeInt", index).apply()
                            }
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
                        colorInt = context.resolveColorAttr(android.R.attr.textColorPrimary)
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
                        colorsRes(
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
                                editor.putString("accent_color", colorOut).apply()

                                startActivity(Intent(context, SettingsActivity::class.java))
                                activity?.finish()
                                activity?.overridePendingTransition(
                                    android.R.anim.fade_in,
                                    android.R.anim.fade_out
                                )
                            }
                        }
                    }
                    true
                }
            }

            findPreference<Preference>("about")?.apply {
                icon =
                    IconicsDrawable(requireContext(), RoundedGoogleMaterial.Icon.gmr_info).apply {
                        colorInt = context.resolveColorAttr(android.R.attr.textColorPrimary)
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
                    colorInt = context.resolveColorAttr(android.R.attr.textColorPrimary)
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

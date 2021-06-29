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
    private lateinit var icons: Icons

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set theme, navigation bar and language
        setAccentColour(this)
        window.setStatusBarAsAccentColour(this)
        window.setNavigationBarColourDefault(this)

        // Setup view binding
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        icons = Icons(this)

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        with(binding.toolbar) {
            navigationIcon = icons.back()
            setBackgroundColor(toolbarColour(context))
            setNavigationOnClickListener { onBackPressed() }
        }

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        private lateinit var sharedPrefs: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        private lateinit var icons: Icons

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            editor = sharedPrefs.edit()
            icons = Icons(requireContext())

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
                    IconicsDrawable(requireContext(), RoundedGoogleMaterial.Icon.gmr_message)
                    IconicsDrawable(requireContext(), RoundedGoogleMaterial.Icon.gmr_memory)
                    val currentLangauge = getLanguage(requireContext())
                    OptionsSheet().show(requireContext()) {
                        title(R.string.settings_language)
                        style(SheetStyle.DIALOG)
                        displayMode(DisplayMode.LIST)
                        with(
                            Option(icons.message(), R.string.en),
                            Option(icons.memory(), R.string.ar),
                            Option(icons.memory(), R.string.zh),
                            Option(icons.memory(), R.string.fr),
                            Option(icons.memory(), R.string.de),
                            Option(icons.memory(), R.string.hi),
                            Option(icons.memory(), R.string.ja),
                            Option(icons.memory(), R.string.pl),
                            Option(icons.memory(), R.string.ru),
                            Option(icons.memory(), R.string.es)
                        )
                        onPositive { index: Int, _: Option ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                (view ?: return@onPositive).performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                            } else {
                                (view ?: return@onPositive).performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            }
                            when (index) {
                                0 -> setLanguage(requireContext(), Locale("en"))
                                1 -> setLanguage(requireContext(), Locale("ar"))
                                2 -> setLanguage(requireContext(), Locale("zh"))
                                3 -> setLanguage(requireContext(), Locale("fr"))
                                4 -> setLanguage(requireContext(), Locale("de"))
                                5 -> setLanguage(requireContext(), Locale("hi"))
                                6 -> setLanguage(requireContext(), Locale("ja"))
                                7 -> setLanguage(requireContext(), Locale("pl"))
                                8 -> setLanguage(requireContext(), Locale("ru"))
                                9 -> setLanguage(requireContext(), Locale("es"))
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

            findPreference<Preference>("app_theme")?.apply {
                val appTheme = sharedPrefs.getInt("appThemeInt", 2)
                summary = when (appTheme) {
                    0 -> getString(R.string.light_mode)
                    1 -> getString(R.string.dark_mode)
                    else -> getString(R.string.follow_system_default)
                }
                icon = when (appTheme) {
                    0 -> icons.lightMode()
                    1 -> icons.darkMode()
                    else -> icons.systemDefault()
                }

                onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    OptionsSheet().show(requireContext()) {
                        style(SheetStyle.DIALOG)
                        displayToolbar(false)
                        displayMode(DisplayMode.LIST)
                        with(
                            Option(icons.lightMode(), R.string.light_mode),
                            Option(icons.darkMode(), R.string.dark_mode),
                            Option(icons.systemDefault(), R.string.follow_system_default)
                        )
                        onNegative { requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }
                        onPositive { index: Int, _: Option ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                (view ?: return@onPositive).performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                            } else {
                                (view ?: return@onPositive).performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            }
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
                                    0 -> icons.lightMode()
                                    1 -> icons.darkMode()
                                    else -> icons.systemDefault()
                                }
                                editor.putInt("appThemeInt", index).apply()
                            }
                        }
                    }
                    true
                }
            }

            findPreference<Preference>("accent_color")?.apply {
                icon = icons.palette()
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
                        onNegative(R.string.cancel) {
                            requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        }
                        onPositive(R.string.okay) { color ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                (view ?: return@onPositive).performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                            } else {
                                (view ?: return@onPositive).performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            }

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
        }
    }
}

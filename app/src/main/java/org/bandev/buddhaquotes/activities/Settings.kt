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
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.maxkeppeler.sheets.color.ColorSheet
import com.maxkeppeler.sheets.color.ColorView
import com.maxkeppeler.sheets.core.SheetStyle
import com.maxkeppeler.sheets.options.DisplayMode
import com.maxkeppeler.sheets.options.Option
import com.maxkeppeler.sheets.options.OptionsSheet
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the data is safe to get
        // if so set timer data

        // Set theme, navigation bar and language
        Colours().setAccentColour(this, window, resources)
        Compatibility().setNavigationBarColourDefault(this, window, resources)
        Languages(baseContext).setLanguage()

        // Setup view binding
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras?.getBoolean("paused") == true) {
            Snackbar.make(binding.root, "Timer has been paused", LENGTH_SHORT).show()
        }

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        if ((intent.extras ?: return).getBoolean("switch")) {
            val sharedPreferences = getSharedPreferences("Settings", 0)
            val darkmode = sharedPreferences.getBoolean("dark_mode", false)
            val sys = sharedPreferences.getBoolean("sys", true)
            when {
                sys -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                darkmode -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onPreferenceTreeClick(preference: Preference?): Boolean {
            when (preference?.key) {
                "About" -> {
                    startActivity(Intent(activity, About::class.java))
                }
                "Help" -> {
                    startActivity(Intent(activity, Intro::class.java))
                }
                "AboutLibraries" -> {
                    startActivity(Intent(activity, AboutLibraries::class.java))
                }
            }
            return true
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            val pref = requireContext().getSharedPreferences("Settings", 0)

            pref.getBoolean("dark_mode", false)
            pref.getBoolean("sys", false)

            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            preferenceScreen

            updateColorSummary()
            updateThemeSummary()
            updateLanguageSummary()

            findPreference<Preference>("accent_color")?.apply {
                this.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    showColorPopup()
                    true
                }
            }

            findPreference<Preference>("theme")?.apply {
                this.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    showThemePopup()
                    true
                }
            }

            findPreference<Preference>("app_language")?.apply {
                this.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    showLanguagePopup()
                    true
                }
            }
        }

        private fun updateColorSummary() {
            findPreference<Preference>("accent_color")?.apply {
                this.summary = when (Colours().getAccentColourAsString(requireContext())) {
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
            }
        }

        private fun updateThemeSummary() {
            findPreference<Preference>("theme")?.apply {
                this.summary = when (Theme().getAppTheme(requireContext())) {
                    0 -> {
                        this.setIcon(R.drawable.ic_day_settings)
                        getString(R.string.light_mode)
                    }
                    1 -> {
                        this.setIcon(R.drawable.ic_night_settings)
                        getString(R.string.dark_mode)
                    }
                    else -> getString(R.string.follow_system_default)
                }
            }
        }

        private fun updateLanguageSummary() {
            findPreference<Preference>("app_language")?.apply {
                val int = Languages(base = context).getLanguageAsInt(requireContext())
                val singleItems = resources.getStringArray(R.array.language_entries)
                this.summary = singleItems[int]
            }
        }

        private fun showLanguagePopup() {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPrefs.edit()
            resources.getStringArray(R.array.language_entries)
            val values = resources.getStringArray(R.array.language_values)
            sharedPrefs.getInt("app_language_int", 0)

            OptionsSheet().show(requireContext()) {
                title(R.string.settings_language)
                style(SheetStyle.DIALOG)
                displayMode(DisplayMode.GRID_VERTICAL)
                with(
                    Option(R.drawable.ic_default, R.string.en),
                    Option(R.drawable.ic_machine, R.string.fr),
                    Option(R.drawable.ic_machine, R.string.de),
                    Option(R.drawable.ic_machine, R.string.es),
                    Option(R.drawable.ic_machine, R.string.ru),
                    Option(R.drawable.ic_machine, R.string.zh),
                    Option(R.drawable.ic_machine, R.string.ja),
                    Option(R.drawable.ic_machine, R.string.hi),
                    Option(R.drawable.ic_language, R.string.pl)
                )
                onNegative { requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }
                onPositive { index: Int, _: Option ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        requireView().performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    } else {
                        requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    }

                    if (Languages(base = context).getLanguageAsInt(requireContext()) != index) {
                        editor.putInt("app_language_int", index)
                        editor.putString("app_language", values[index])
                        editor.apply()
                        val intent2 = Intent(context, Settings::class.java)
                        val mBundle = Bundle()
                        mBundle.putBoolean("lang", true)
                        intent2.putExtras(mBundle)
                        startActivity(intent2)
                        activity?.finish()
                        activity?.overridePendingTransition(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                    } else {
                        dismiss()
                    }
                }
                onNegative {
                    requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }
            }
        }

        private fun showThemePopup() {
            val pref = requireContext().getSharedPreferences("Settings", 0)
            val editor = pref.edit()
            resources.getStringArray(R.array.theme_entries)
            pref.getInt("appThemeInt", 2)

            OptionsSheet().show(requireContext()) {
                title(R.string.app_theme)
                style(SheetStyle.DIALOG)
                with(
                    Option(R.drawable.ic_day_settings, R.string.light_mode),
                    Option(R.drawable.ic_night_settings, R.string.dark_mode),
                    Option(R.drawable.ic_palette, R.string.follow_system_default)
                )
                onNegative { requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }
                onPositive { index: Int, _: Option ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        requireView().performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    } else {
                        requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    }
                    if (Theme().getAppTheme(requireContext()) != index) {
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
                        val intent2 = Intent(context, Settings::class.java)
                        val mBundle = Bundle()
                        mBundle.putBoolean("switch", true)
                        intent2.putExtras(mBundle)
                        startActivity(intent2)
                        activity?.finish()
                        activity?.overridePendingTransition(0, 0)
                    } else {
                        dismiss()
                    }
                }
            }
        }

        private fun showColorPopup() {
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        requireView().performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    } else {
                        requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    }

                    // Checks if the chosen color is not the same as the current color
                    if (Colours().getAccentColourAsInt(requireContext()) != color) {
                        var colorOut = "original"
                        when (color) {
                            ContextCompat.getColor(requireContext(), R.color.pinkAccent) -> {
                                colorOut = "pink"
                            }
                            ContextCompat.getColor(requireContext(), R.color.violetAccent) -> {
                                colorOut = "violet"
                            }
                            ContextCompat.getColor(requireContext(), R.color.blueAccent) -> {
                                colorOut = "blue"
                            }
                            ContextCompat.getColor(requireContext(), R.color.lightBlueAccent) -> {
                                colorOut = "lightBlue"
                            }
                            ContextCompat.getColor(requireContext(), R.color.tealAccent) -> {
                                colorOut = "teal"
                            }
                            ContextCompat.getColor(requireContext(), R.color.greenAccent) -> {
                                colorOut = "green"
                            }
                            ContextCompat.getColor(requireContext(), R.color.limeAccent) -> {
                                colorOut = "lime"
                            }
                            ContextCompat.getColor(requireContext(), R.color.yellowAccent) -> {
                                colorOut = "yellow"
                            }
                            ContextCompat.getColor(requireContext(), R.color.orangeAccent) -> {
                                colorOut = "orange"
                            }
                            ContextCompat.getColor(requireContext(), R.color.redAccent) -> {
                                colorOut = "red"
                            }
                            ContextCompat.getColor(requireContext(), R.color.crimsonAccent) -> {
                                colorOut = "crimson"
                            }
                        }
                        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
                        val editor = sharedPrefs.edit()
                        editor.putString("accent_color", colorOut)
                        editor.apply()

                        updateColorSummary()

                        val intent2 = Intent(context, Settings::class.java)
                        val mBundle = Bundle()
                        mBundle.putBoolean("switch", true)
                        intent2.putExtras(mBundle)
                        startActivity(intent2)
                        activity?.finish()
                        activity?.overridePendingTransition(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                    } else {
                        dismiss()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@Settings, Main::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(
            R.anim.anim_slide_in_right,
            R.anim.anim_slide_out_right
        )
    }
}

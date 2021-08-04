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
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
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
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.core.Insets.STATUS_BARS
import org.bandev.buddhaquotes.core.Insets.applyInsets
import org.bandev.buddhaquotes.databinding.ActivitySettingsBinding
import java.util.*

/**
 * Where the user can customise their app
 */
class SettingsActivity : LocalizationActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.apply {
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
            setNavigationBarColourDefault()
            setDarkStatusIcons()
            setDecorFitsSystemWindows(this, false)
        }

        // Setup view binding
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        binding.toolbar.apply {
            setSupportActionBar(this)
            applyInsets(STATUS_BARS)
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

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val settings = Prefs(requireContext()).Settings()


            fun Preference.updateTheme() {
                val theme = settings.theme

                summary = getString(
                    when (theme) {
                        MODE_NIGHT_NO -> R.string.light_mode
                        MODE_NIGHT_YES -> R.string.dark_mode
                        else -> R.string.follow_system_default
                    }
                )

                icon = getDrawable(
                    context, when (theme) {
                        MODE_NIGHT_NO -> R.drawable.ic_light_mode
                        MODE_NIGHT_YES -> R.drawable.ic_dark_mode
                        else -> R.drawable.ic_dark_mode
                    }
                )
            }

            fun Preference.updateAccent() {
                val accent = settings.accent

                summary = getString(
                    when (accent) {
                        settings.ACCENT_PINK -> R.string.pink
                        settings.ACCENT_VIOLET -> R.string.violet
                        settings.ACCENT_BLUE -> R.string.blue
                        settings.ACCENT_LIGHT_BLUE -> R.string.lightBlue
                        settings.ACCENT_TEAL -> R.string.teal
                        settings.ACCENT_GREEN -> R.string.green
                        settings.LI -> R.string.lime
                        settings.ACCENT_Ori -> R.string.yellow
                        settings.ACCENT_PINK -> R.string.orange
                        settings.ACCENT_PINK -> R.string.red
                        settings.ACCENT_PINK -> R.string.crimson
                        else -> R.string.original
                    }
                )

                icon = getDrawable(context, R.drawable.ic_circle)?.apply {
                    setTint(
                        getColor(
                            context,
                            when (getAccentColourAsString(requireContext())) {
                                "pink" -> R.color.pinkAccent
                                "violet" -> R.color.violetAccent
                                "blue" -> R.color.blueAccent
                                "lightBlue" -> R.color.lightBlueAccent
                                "teal" -> R.color.tealAccent
                                "green" -> R.color.greenAccent
                                "lime" -> R.color.limeAccent
                                "yellow" -> R.color.yellowAccent
                                "orange" -> R.color.orangeAccent
                                "red" -> R.color.redAccent
                                "crimson" -> R.color.crimsonAccent
                                else -> R.color.colorPrimary
                            }
                        )
                    )
                }
            }


            findPreference<Preference>("app_language")?.apply {
                icon = getDrawable(
                    context, when (getLanguage(requireContext())) {
                        Locale("en") -> R.drawable.flag_england
                        Locale("ar") -> R.drawable.flag_united_arab_emirates
                        Locale("zh") -> R.drawable.flag_china
                        Locale("fr") -> R.drawable.flag_france
                        Locale("de") -> R.drawable.flag_germany
                        Locale("hi") -> R.drawable.flag_india
                        Locale("ja") -> R.drawable.flag_japan
                        Locale("pl") -> R.drawable.flag_poland
                        Locale("ru") -> R.drawable.flag_russia
                        Locale("es") -> R.drawable.flag_spain
                        else -> R.drawable.flag_england
                    }
                )
                summary = getString(
                    when (getLanguage(requireContext())) {
                        Locale("en") -> R.string.en
                        Locale("ar") -> R.string.ar
                        Locale("zh") -> R.string.zh
                        Locale("fr") -> R.string.fr
                        Locale("de") -> R.string.de
                        Locale("hi") -> R.string.hi
                        Locale("ja") -> R.string.ja
                        Locale("pl") -> R.string.pl
                        Locale("ru") -> R.string.ru
                        Locale("es") -> R.string.es
                        else -> R.string.settings_language
                    }
                )
                onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    it.isEnabled = false
                    Feedback.virtualKey(requireView())
                    val currentLangauge = getLanguage(requireContext())
                    OptionsSheet().show(requireContext()) {
                        style(SheetStyle.DIALOG)
                        displayMode(DisplayMode.LIST)
                        with(
                            Option(R.drawable.fl_sheet_england, R.string.en),
                            Option(R.drawable.fl_sheet_united_arab_emirates, R.string.ar),
                            Option(R.drawable.fl_sheet_china, R.string.zh),
                            Option(R.drawable.fl_sheet_france, R.string.fr),
                            Option(R.drawable.fl_sheet_germany, R.string.de),
                            Option(R.drawable.fl_sheet_india, R.string.hi),
                            Option(R.drawable.fl_sheet_japan, R.string.ja),
                            Option(R.drawable.fl_sheet_poland, R.string.pl),
                            Option(R.drawable.fl_sheet_russia, R.string.ru),
                            Option(R.drawable.fl_sheet_spain, R.string.es)
                        )
                        onPositive { index: Int, _: Option ->
                            Feedback.confirm(view ?: return@onPositive)
                            setLanguage(
                                requireContext(), when (index) {
                                    0 -> Locale("en")
                                    1 -> Locale("ar")
                                    2 -> Locale("zh")
                                    3 -> Locale("fr")
                                    4 -> Locale("de")
                                    5 -> Locale("hi")
                                    6 -> Locale("ja")
                                    7 -> Locale("pl")
                                    8 -> Locale("ru")
                                    9 -> Locale("es")
                                    else -> Locale("en")
                                }
                            )
                            if (getLanguage(requireContext()) != currentLangauge) {
                                startActivity(Intent(context, SettingsActivity::class.java))
                                activity?.run {
                                    finish()
                                    overridePendingTransition(
                                        android.R.anim.fade_in,
                                        android.R.anim.fade_out
                                    )
                                }
                            }
                        }
                        onClose { it.isEnabled = true }
                    }
                    true
                }
            }

            findPreference<Preference>("app_theme")?.apply {
                updateTheme()
                onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    it.isEnabled = false
                    Feedback.virtualKey(requireView())
                    OptionsSheet().show(requireContext()) {
                        style(SheetStyle.DIALOG)
                        displayToolbar(false)
                        displayMode(DisplayMode.LIST)
                        with(
                            Option(R.drawable.ic_light_mode, R.string.light_mode),
                            Option(R.drawable.ic_dark_mode, R.string.dark_mode),
                            Option(R.drawable.ic_dark_mode, R.string.follow_system_default)
                        )
                        onPositive { index: Int, _: Option ->
                            Feedback.confirm(view ?: return@onPositive)

                            settings.theme = when (index) {
                                0 -> MODE_NIGHT_NO
                                1 -> MODE_NIGHT_YES
                                else -> MODE_NIGHT_FOLLOW_SYSTEM
                            }
                            setDefaultNightMode(settings.theme)
                            updateTheme()
                        }
                        onClose { it.isEnabled = true }
                    }
                    true
                }
            }

            findPreference<Preference>("accent_color")?.apply {

                onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    it.isEnabled = false
                    Feedback.virtualKey(requireView())
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
                        style(SheetStyle.DIALOG)
                        defaultView(ColorView.TEMPLATE)
                        disableSwitchColorView()
                        onNegative(R.string.cancel) { Feedback.virtualKey(requireView()) }
                        onPositive(R.string.okay) { color ->
                            Feedback.confirm(view ?: return@onPositive)
                            if (requireContext().resolveColorAttr(R.attr.colorPrimary) != color) {
                                editor.putString("accent_color", colorOut(color)).apply()
                                startActivity(Intent(context, SettingsActivity::class.java))
                                activity?.run {
                                    finish()
                                    overridePendingTransition(
                                        android.R.anim.fade_in,
                                        android.R.anim.fade_out
                                    )
                                }
                            }
                        }
                        onClose { it.isEnabled = true }
                    }
                    true
                }
            }
        }

        private fun colorOut(color: Int): String {
            return when (color) {
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
        }
    }
}

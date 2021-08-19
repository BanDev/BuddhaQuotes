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

package org.bandev.buddhaquotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.akexorcist.localizationactivity.core.LanguageSetting.getLanguage
import com.akexorcist.localizationactivity.core.LanguageSetting.setLanguage
import com.maxkeppeler.sheets.color.ColorSheet
import com.maxkeppeler.sheets.color.ColorView
import com.maxkeppeler.sheets.core.SheetStyle
import com.maxkeppeler.sheets.options.DisplayMode
import com.maxkeppeler.sheets.options.Option
import com.maxkeppeler.sheets.options.OptionsSheet
import me.kosert.flowbus.GlobalBus
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.bus.Message
import org.bandev.buddhaquotes.bus.MessageType
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.FragmentSettingsBinding
import java.util.*

/**
 * Where the user can customise their app
 */
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsChild())
            .commit()
    }

    class SettingsChild : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val settings = Prefs(requireContext()).Settings()

            fun Preference.setLanguageSummary() {
                val language = getLanguage(requireContext())

                icon = getDrawable(
                    context, when (language) {
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
                    when (language) {
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
            }

            fun Preference.setThemeSummary() {
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

            fun Preference.setAccentSummary() {
                val accent = AccentSetting.getAccentColor(requireContext())

                summary = getString(
                    when (accent) {
                        AccentColor.PINK -> R.string.pink
                        AccentColor.VIOLET -> R.string.violet
                        AccentColor.BLUE -> R.string.blue
                        AccentColor.LIGHT_BLUE -> R.string.lightBlue
                        AccentColor.TEAL -> R.string.teal
                        AccentColor.GREEN -> R.string.green
                        AccentColor.LIME -> R.string.lime
                        AccentColor.YELLOW -> R.string.yellow
                        AccentColor.ORANGE -> R.string.orange
                        AccentColor.RED -> R.string.red
                        AccentColor.CRIMSON -> R.string.crimson
                        else -> R.string.original
                    }
                )

                icon = getDrawable(context, R.drawable.ic_circle)?.apply {
                    setTint(
                        getColor(
                            context,
                            when (accent) {
                                AccentColor.PINK -> R.color.pinkAccent
                                AccentColor.VIOLET -> R.color.violetAccent
                                AccentColor.BLUE -> R.color.blueAccent
                                AccentColor.LIGHT_BLUE -> R.color.lightBlueAccent
                                AccentColor.TEAL -> R.color.tealAccent
                                AccentColor.GREEN -> R.color.greenAccent
                                AccentColor.LIME -> R.color.limeAccent
                                AccentColor.YELLOW -> R.color.yellowAccent
                                AccentColor.ORANGE -> R.color.orangeAccent
                                AccentColor.RED -> R.color.redAccent
                                AccentColor.CRIMSON -> R.color.crimsonAccent
                                else -> R.color.colorPrimary
                            }
                        )
                    )
                }
            }


            findPreference<Preference>("app_language")?.apply {
                setLanguageSummary()
                onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    isEnabled = false
                    Feedback.virtualKey(requireView())
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
                            Option(R.drawable.fl_sheet_spain, R.string.es),
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
                                    else -> Locale.ROOT
                                }
                            )
                            activity?.run {
                                startActivity(intent.apply { putExtra("languageChange", true) })
                                finish()
                                overridePendingTransition(
                                    android.R.anim.fade_in,
                                    android.R.anim.fade_out
                                )
                            }
                        }
                        onClose { it.isEnabled = true }
                    }
                    true
                }
            }

            findPreference<Preference>("app_theme")?.apply {
                setThemeSummary()
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
                            setThemeSummary()
                        }
                        onClose { it.isEnabled = true }
                    }
                    true
                }
            }

            findPreference<Preference>("accent_color")?.apply {
                setAccentSummary()
                onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    isEnabled = false
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
                            AccentSetting.setAccentColorAndPref(requireContext(), color.toAccentColor())
                            parentFragmentManager
                                .beginTransaction()
                                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                                .replace(R.id.settings, SettingsChild())
                                .commit()
                        }
                        onClose { it.isEnabled = true }
                    }
                    true
                }
            }
        }

        private fun Int.toAccentColor(): AccentColor {
            return when (this) {
                getColor(requireContext(), R.color.pinkAccent) -> AccentColor.PINK
                getColor(requireContext(), R.color.violetAccent) -> AccentColor.VIOLET
                getColor(requireContext(), R.color.blueAccent) -> AccentColor.BLUE
                getColor(requireContext(), R.color.lightBlueAccent) -> AccentColor.LIGHT_BLUE
                getColor(requireContext(), R.color.tealAccent) -> AccentColor.TEAL
                getColor(requireContext(), R.color.greenAccent) -> AccentColor.GREEN
                getColor(requireContext(), R.color.limeAccent) -> AccentColor.LIME
                getColor(requireContext(), R.color.yellowAccent) -> AccentColor.YELLOW
                getColor(requireContext(), R.color.orangeAccent) -> AccentColor.ORANGE
                getColor(requireContext(), R.color.redAccent) -> AccentColor.RED
                getColor(requireContext(), R.color.crimsonAccent) -> AccentColor.CRIMSON
                else -> AccentColor.ORIGINAL
            }
        }
    }
}

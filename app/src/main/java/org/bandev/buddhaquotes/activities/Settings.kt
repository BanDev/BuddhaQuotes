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
import com.maxkeppeler.bottomsheets.color.ColorSheet
import com.maxkeppeler.bottomsheets.color.ColorView
import com.maxkeppeler.bottomsheets.options.DisplayMode
import com.maxkeppeler.bottomsheets.options.Option
import com.maxkeppeler.bottomsheets.options.OptionsSheet
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages
import org.bandev.buddhaquotes.core.Theme
import org.bandev.buddhaquotes.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set theme, navigation bar and language
        Colours().setAccentColour(this, window, resources)
        Compatibility().setNavigationBarColourWhite(this, window, resources)
        Languages().setLanguage(this)

        // Setup view binding
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            val accentColorButton = findPreference<Preference>("accent_color")
            (accentColorButton ?: return).onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    showColorPopup()
                    true
                }

            val shapesModeButton = findPreference<Preference>("shapes_mode")
            (shapesModeButton ?: return).onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    true
                }

            val appThemeButton = findPreference<Preference>("theme")
            (appThemeButton ?: return).onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    showThemePopup()
                    true
                }

            val languageButton = findPreference<Preference>("app_language")
            (languageButton ?: return).onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    showLanguagePopup()
                    true
                }
        }

        private fun updateColorSummary() {
            val accentColor = findPreference<Preference>("accent_color")
            when (Colours().getAccentColourAsString(requireContext())) {
                "pink" -> (accentColor ?: return).summary = getString(R.string.pink)
                "violet" -> (accentColor ?: return).summary = getString(R.string.violet)
                "blue" -> (accentColor ?: return).summary = getString(R.string.blue)
                "lightBlue" -> (accentColor ?: return).summary = getString(R.string.lightBlue)
                "teal" -> (accentColor ?: return).summary = getString(R.string.teal)
                "green" -> (accentColor ?: return).summary = getString(R.string.green)
                "lime" -> (accentColor ?: return).summary = getString(R.string.lime)
                "yellow" -> (accentColor ?: return).summary = getString(R.string.yellow)
                "orange" -> (accentColor ?: return).summary = getString(R.string.orange)
                "red" -> (accentColor ?: return).summary = getString(R.string.red)
                "crimson" -> (accentColor ?: return).summary = getString(R.string.crimson)
                else -> (accentColor ?: return).summary = getString(R.string.original)
            }
        }

        private fun updateThemeSummary() {
            val theme = findPreference<Preference>("theme")
            when (Theme().getAppTheme(requireContext())) {
                0 -> {
                    (theme ?: return).summary = getString(R.string.light_mode)
                    theme.setIcon(R.drawable.ic_day_settings)
                }
                1 -> {
                    (theme ?: return).summary = getString(R.string.dark_mode)
                    theme.setIcon(R.drawable.ic_night_settings)
                }
                else -> (theme ?: return).summary = getString(R.string.follow_system_default)
            }
        }

        private fun updateLanguageSummary() {
            val language = findPreference<Preference>("app_language")
            val int = Languages().getLanguageAsInt(requireContext())
            val singleItems = resources.getStringArray(R.array.language_entries)
            (language ?: return).summary = singleItems[int]
        }

        private fun showLanguagePopup() {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPrefs.edit()
            val singleItems = resources.getStringArray(R.array.language_entries)
            val values = resources.getStringArray(R.array.language_values)
            val checkedItem = sharedPrefs.getInt("app_language_int", 0)

            OptionsSheet().show(requireContext()) {
                title(R.string.settings_language)
                displayMode(DisplayMode.LIST)
                with(
                    Option(R.string.en),
                    Option(R.string.fr),
                    Option(R.string.de),
                    Option(R.string.pl),
                    Option(R.string.ru),
                    Option(R.string.es)
                )
                onPositive { index: Int, option: Option ->
                    // Handle selected option

                    editor.putInt("app_language_int", index)
                    editor.putString("app_language", values[index])

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        requireView().performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    } else {
                        requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    }
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
                }
                onNegative {
                    requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }
            }

        }

        private fun showThemePopup() {
            val pref = requireContext().getSharedPreferences("Settings", 0)
            val editor = pref.edit()
            val singleItems = resources.getStringArray(R.array.theme_entries)
            val checkedItem = pref.getInt("appThemeInt", 2)


            OptionsSheet().show(requireContext()) {
                title(R.string.app_theme)
                with(
                    Option(R.drawable.ic_day_settings, R.string.light_mode),
                    Option(R.drawable.ic_night_settings, R.string.dark_mode),
                    Option(R.drawable.ic_palette, R.string.follow_system_default)
                )
                onPositive { index: Int, option: Option ->
                    when (index) {
                        0 -> {
                            editor.putBoolean("dark_mode", false)
                            editor.putBoolean("sys", false)
                            editor.putInt("appThemeInt", 0)
                        }
                        1 -> {
                            editor.putBoolean("dark_mode", true)
                            editor.putBoolean("sys", false)
                            editor.putInt("appThemeInt", 1)
                        }
                        2 -> {
                            editor.putBoolean("dark_mode", false)
                            editor.putBoolean("sys", true)
                            editor.putInt("appThemeInt", 2)
                        }
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        requireView().performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    } else {
                        requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    }
                    editor.apply()
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
                }
            }
        }

        private fun showColorPopup() {
            val colors = mutableListOf(
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

            ColorSheet().show(requireContext()) {
                colors(colors)
                title(R.string.settings_accent_colour)
                defaultView(ColorView.TEMPLATE)
                disableSwitchColorView()
                onPositive { color ->
                    // Use color
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
                }
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@Settings, Main::class.java))
        finish()
        overridePendingTransition(
            R.anim.anim_slide_in_right,
            R.anim.anim_slide_out_right
        )
    }
}
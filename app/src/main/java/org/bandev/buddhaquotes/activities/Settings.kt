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
import com.maxkeppeler.bottomsheets.color.ColorSheet
import com.maxkeppeler.bottomsheets.color.ColorView
import com.maxkeppeler.bottomsheets.options.DisplayMode
import com.maxkeppeler.bottomsheets.options.Option
import com.maxkeppeler.bottomsheets.options.OptionsSheet
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var timerData: Bundle
    private var paused: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(
            R.anim.anim_slide_in_left,
            R.anim.anim_slide_out_left
        )

        // Check if the data is safe to get
        // if so set timer data

        // Set theme, navigation bar and language
        Colours().setAccentColour(this, window, resources)
        Compatibility().setNavigationBarColourGray(this, window, resources)
        Languages().setLanguage(this)

        // Setup view binding
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if(intent.extras?.getBoolean("paused") == true){
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

            val accentColorButton = findPreference<Preference>("accent_color")
            (accentColorButton ?: return).onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    showColorPopup()
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
                closeButtonDrawable(R.drawable.ic_down_arrow)
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
                onPositive { index: Int, option: Option ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        requireView().performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    } else {
                        requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    }

                    if (Languages().getLanguageAsInt(requireContext()) != index) {
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
            val singleItems = resources.getStringArray(R.array.theme_entries)
            val checkedItem = pref.getInt("appThemeInt", 2)

            OptionsSheet().show(requireContext()) {
                title(R.string.app_theme)
                closeButtonDrawable(R.drawable.ic_down_arrow)
                with(
                    Option(R.drawable.ic_day_settings, R.string.light_mode),
                    Option(R.drawable.ic_night_settings, R.string.dark_mode),
                    Option(R.drawable.ic_palette, R.string.follow_system_default)
                )
                onNegative { requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }
                onPositive { index: Int, option: Option ->
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
                closeButtonDrawable(R.drawable.ic_down_arrow)
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
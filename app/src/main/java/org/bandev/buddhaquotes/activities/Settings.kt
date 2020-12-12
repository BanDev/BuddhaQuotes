package org.bandev.buddhaquotes.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.colorChooser
import com.afollestad.materialdialogs.utils.MDUtil.getStringArray
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.bandev.buddhaquotes.OldMainActivity
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
        Compatibility().setNavigationBarColour(this, window, resources)
        Languages().setLanguage(this)

        // Setup view binding & force portrait mode
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            val i = Intent(this, OldMainActivity::class.java)
            overridePendingTransition(
                R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right
            )
            finish()
        }

        if ((intent.extras ?: return).getBoolean("lang")) {
            this.overridePendingTransition(0, 0)
        }

        if ((intent.extras ?: return).getBoolean("switch")) {
            val sharedPreferences = getSharedPreferences("Settings", 0)
            val darkmode = sharedPreferences.getBoolean("dark_mode", false)
            val sys = sharedPreferences.getBoolean("sys", true)
            when {
                sys -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    overridePendingTransition(
                        R.anim.fade_out,
                        R.anim.fade_in
                    )
                }
                darkmode -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    overridePendingTransition(
                        R.anim.fade_out,
                        R.anim.fade_in
                    )
                }
                else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    overridePendingTransition(
                        R.anim.fade_out,
                        R.anim.fade_in
                    )
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
                    val i = Intent(activity, About::class.java)
                    startActivity(i)
                }
                "Help" -> {
                    val i = Intent(activity, Intro::class.java)
                    startActivity(i)
                }
                "AboutLibraries" -> {
                    val i = Intent(activity, OssLibraries::class.java)
                    startActivity(i)
                }
            }
            return true
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            val pref = requireContext().getSharedPreferences("Settings", 0)

            val dark = pref.getBoolean("dark_mode", false)
            val sys = pref.getBoolean("sys", false)

            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val screen = preferenceScreen

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
            val singleItems = requireContext().getStringArray(R.array.language_entries)
            (language ?: return).summary = singleItems[int]
        }

        private fun showLanguagePopup() {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPrefs.edit()
            val singleItems = requireContext().getStringArray(R.array.language_entries)
            val values = requireContext().getStringArray(R.array.language_values)
            val checkedItem = sharedPrefs.getInt("app_language_int", 0)

            MaterialAlertDialogBuilder(requireContext(), R.style.PopupTheme)
                .setTitle(R.string.settings_language)
                .setNegativeButton(R.string.cancel) { _, _ ->
                    requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }
                .setPositiveButton(R.string.confirm) { _, _ ->
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
                }
                .setSingleChoiceItems(singleItems, checkedItem) { _, which ->
                    editor.putInt("app_language_int", which)
                    editor.putString("app_language", values[which])
                }
                .show()
        }

        private fun showThemePopup() {
            val pref = requireContext().getSharedPreferences("Settings", 0)
            val editor = pref.edit()
            val singleItems = requireContext().getStringArray(R.array.theme_entries)
            val checkedItem = pref.getInt("appThemeInt", 2)

            MaterialAlertDialogBuilder(requireContext(), R.style.PopupTheme)
                .setTitle(R.string.app_theme)
                .setNegativeButton(R.string.cancel) { _, _ ->
                    requireView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }
                .setPositiveButton(R.string.confirm) { _, _ ->
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
                }
                .setSingleChoiceItems(singleItems, checkedItem) { _, which ->
                    when (which) {
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
                }
                .show()
        }

        private fun showColorPopup() {
            val colors = intArrayOf(
                ContextCompat.getColor(requireContext(), R.color.pinkAccent),
                ContextCompat.getColor(requireContext(), R.color.violetAccent),
                ContextCompat.getColor(requireContext(), R.color.blueAccent),
                ContextCompat.getColor(requireContext(), R.color.lightBlueAccent),
                ContextCompat.getColor(requireContext(), R.color.tealAccent),
                ContextCompat.getColor(requireContext(), R.color.greenAccent),
                ContextCompat.getColor(requireContext(), R.color.limeAccent),
                ContextCompat.getColor(requireContext(), R.color.yellowAccent),
                ContextCompat.getColor(requireContext(), R.color.orangeAccent),
                ContextCompat.getColor(requireContext(), R.color.redAccent),
                ContextCompat.getColor(requireContext(), R.color.crimsonAccent),
                ContextCompat.getColor(requireContext(), R.color.colorPrimary)
            )

            MaterialDialog(requireContext()).show {
                title(R.string.settings_accent_colour)
                colorChooser(colors) { _, color ->
                    // Use color integer
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
                }
                positiveButton(R.string.confirm) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        (window ?: return@positiveButton).decorView.performHapticFeedback(
                            HapticFeedbackConstants.CONFIRM
                        )
                    } else {
                        (window ?: return@positiveButton).decorView.performHapticFeedback(
                            HapticFeedbackConstants.VIRTUAL_KEY
                        )
                    }
                }
                negativeButton(R.string.cancel) {
                    (window ?: return@negativeButton).decorView.performHapticFeedback(
                        HapticFeedbackConstants.VIRTUAL_KEY
                    )
                }
            }
        }
    }

    override fun onBackPressed() {
        val i = Intent(this, OldMainActivity::class.java)
        overridePendingTransition(
            R.anim.anim_slide_in_right,
            R.anim.anim_slide_out_right
        )
        finish()
    }
}
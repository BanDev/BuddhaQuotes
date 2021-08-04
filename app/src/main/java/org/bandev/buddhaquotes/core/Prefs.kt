package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.preference.PreferenceManager.getDefaultSharedPreferences

class Prefs(context: Context) {

    private var sharedPrefs: SharedPreferences = getDefaultSharedPreferences(context)
    private var editor: SharedPreferences.Editor = sharedPrefs.edit()

    inner class Timer {
        // Should the app vibrate
        var vibrateSecond: Boolean
            get() = sharedPrefs.getBoolean("timer_vibrateSecond", false)
            set(value) {
                editor.putBoolean("timer_vibrateSecond", value).apply()
            }

        // Play a sound at the end?
        var endSound: Boolean
            get() = sharedPrefs.getBoolean("timer_endSound", true)
            set(value) {
                editor.putBoolean("timer_endSound", value).apply()
            }

        // Show a notification with progress?
        var showNotificaton: Boolean
            get() = sharedPrefs.getBoolean("timer_showNotificaton", false)
            set(value) {
                editor.putBoolean("timer_showNotificaton", value).apply()
            }
    }

    inner class Settings {
        // The app's language
        var language: String
            get() = sharedPrefs.getString("settings_language", "en").toString()
            set(value) {
                editor.putString("settings_language", value).apply()
            }

        // The theme for the app
        var theme: Int
            get() = sharedPrefs.getInt("settings_theme", MODE_NIGHT_FOLLOW_SYSTEM)
            set(value) {
                editor.putInt("settings_theme", value).apply()
            }

        // The accent colour
        var accent: Int
            get() = sharedPrefs.getInt("settings_accent", ACCENT_DEFAULT)
            set(value) {
                editor.putInt("settings_accent", value).apply()
            }
    }
}

const val ACCENT_DEFAULT: Int = -1
val ACCENT_BLUE: Int = 0
val ACCENT_CRIMSON: Int = 1
val ACCENT_GREEN: Int = 2
val ACCENT_LIGHT_BLUE: Int = 3
val ACCENT_ORANGE: Int = 4
val ACCENT_PINK: Int = 5
val ACCENT_RED: Int = 6
val ACCENT_TEAL: Int = 7
val ACCENT_VIOLET: Int = 8
val ACCENT_YELLOW: Int = 9
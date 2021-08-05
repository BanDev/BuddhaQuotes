package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import org.bandev.buddhaquotes.core.Accent.DEFAULT

class Prefs(context: Context) {

    private var sharedPrefs: SharedPreferences = getDefaultSharedPreferences(context)
    private var editor: SharedPreferences.Editor = sharedPrefs.edit()
    private val vibrateBoolKey = "VIBRATE_BOOLEAN_KEY"
    private val soundKey = "END_SOUND_KEY"
    private val notifBoolKey = "SHOW_NOTIFICATION_KEY"
    private val themeKey = "THEME_KEY"
    private val accentKey = "ACCENT_KEY"

    inner class Timer {
        // Should the app vibrate
        var vibrateSecond: Boolean
            get() = sharedPrefs.getBoolean(vibrateBoolKey, false)
            set(value) {
                editor.putBoolean(vibrateBoolKey, value).apply()
            }

        // Play a sound at the end
        var endSound: Boolean
            get() = sharedPrefs.getBoolean(soundKey, true)
            set(value) {
                editor.putBoolean(soundKey, value).apply()
            }

        // Show a notification with progress?
        var showNotificaton: Boolean
            get() = sharedPrefs.getBoolean(notifBoolKey, false)
            set(value) {
                editor.putBoolean(notifBoolKey, value).apply()
            }
    }

    inner class Settings {
        // The theme for the app
        var theme: Int
            get() = sharedPrefs.getInt(themeKey, MODE_NIGHT_FOLLOW_SYSTEM)
            set(value) {
                editor.putInt(themeKey, value).apply()
            }

        // The accent colour
        var accent: Int
            get() = sharedPrefs.getInt(accentKey, DEFAULT)
            set(value) {
                editor.putInt(accentKey, value).apply()
            }
    }
}

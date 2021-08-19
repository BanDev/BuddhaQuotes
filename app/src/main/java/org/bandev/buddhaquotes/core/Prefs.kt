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

package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import org.bandev.buddhaquotes.core.Accent.convertAccentColorToInt
import org.bandev.buddhaquotes.core.Accent.convertToAccentColor

class Prefs(context: Context) {

    private var sharedPrefs: SharedPreferences = getDefaultSharedPreferences(context)
    private var editor: SharedPreferences.Editor = sharedPrefs.edit()

    inner class Timer {
        private val vibrateBoolKey = "VIBRATE_BOOLEAN_KEY"
        private val soundKey = "END_SOUND_KEY"
        private val notifBoolKey = "SHOW_NOTIFICATION_KEY"

        // Should the app vibrate
        var vibrateSecond: Boolean
            get() = sharedPrefs.getBoolean(vibrateBoolKey, false)
            set(value) = editor.putBoolean(vibrateBoolKey, value).apply()

        // Play a sound at the end
        var endSound: Boolean
            get() = sharedPrefs.getBoolean(soundKey, true)
            set(value) = editor.putBoolean(soundKey, value).apply()

        // Show a notification with progress?
        var showNotificaton: Boolean
            get() = sharedPrefs.getBoolean(notifBoolKey, false)
            set(value) = editor.putBoolean(notifBoolKey, value).apply()
    }

    inner class Settings {
        private val themeKey = "THEME_KEY"
        private val accentKey = "ACCENT_KEY"

        // The theme for the app
        var theme: Int
            get() = sharedPrefs.getInt(themeKey, MODE_NIGHT_FOLLOW_SYSTEM)
            set(value) = editor.putInt(themeKey, value).apply()

        // The accent colour
        var accent: AccentColor
            get() = convertToAccentColor(sharedPrefs.getInt(accentKey, DEFAULT))
            set(value) = editor.putInt(accentKey, convertAccentColorToInt(value)).apply()
    }

    inner class Images {
        private val quoteImageKey = "QUOTE_IMAGE_KEY"

        var quoteImage: Int
            get() = sharedPrefs.getInt(quoteImageKey, DEFAULT)
            set(value) = editor.putInt(quoteImageKey, value).apply()
    }

    companion object {
        const val DEFAULT: Int = 0
    }
}

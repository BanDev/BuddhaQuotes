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
import org.bandev.buddhaquotes.R

/**
 * Timer helps with the meditation timer
 */

class Timer {
    inner class Settings(context: Context) {
        // Setup the sharedPrefs & editor
        private var sharedPrefs: SharedPreferences = context.getSharedPreferences("Timer", 0)
        private var editor: SharedPreferences.Editor = sharedPrefs.edit()

        // Vibrate the phone on each second?
        var vibrateSecond: Boolean
            get() = sharedPrefs.getBoolean("vibrateSecond", false)
            set(value) {
                editor.putBoolean("vibrateSecond", value)
                editor.commit()
            }

        // Play a sound at the end?
        var endSoundID: Int
            get() = sharedPrefs.getInt("endSoundID", 0)
            set(value) {
                editor.putInt("endSoundID", value)
                editor.commit()
            }

        // Show a notification with progress?
        var showNotificaton: Boolean
            get() = sharedPrefs.getBoolean("showNotificaton", false)
            set(value) {
                editor.putBoolean("showNotificaton", value)
                editor.commit()
            }
    }
}
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

class TimerStore(context: Context) {

    private var sharedPref: SharedPreferences = context.getSharedPreferences("TimerStore", 0)
    private var editor: SharedPreferences.Editor = sharedPref.edit()

    var active: Boolean
        get() = sharedPref.getBoolean("active", false)
        set(value) {
            editor.putBoolean("active", value)
            editor.commit()
        }

    var duration: Long
        get() = sharedPref.getLong("duration", 0L)
        set(value) {
            editor.putLong("duration", value)
            editor.commit()
        }

    var progress: Long
        get() = sharedPref.getLong("progress", 0L)
        set(value) {
            editor.putLong("progress", value)
            editor.commit()
        }

    fun resumeTimers(): Boolean {
        return (duration != 0L && progress != 0L && active)
    }
}
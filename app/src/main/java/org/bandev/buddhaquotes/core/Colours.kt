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
import android.content.res.Configuration
import android.content.res.Resources
import android.util.TypedValue
import android.view.Window
import androidx.core.content.ContextCompat.getColor
import androidx.preference.PreferenceManager
import org.bandev.buddhaquotes.R

/**
 * Colours is the class used to manage custom accent colours in Buddha Quotes. It saves data in
 * shared preferences using the key "accent_color"
 *
 * @author jack.txt & Fennec_exe
 * @since v1.5.0
 * @updated 09/12/2020
 */
class Colours {

    /**
     * Sets activity's theme based off setting from preferences
     * @param [context] context of activity (Context)
     */

    fun setAccentColour(context: Context) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)

        //Set the accent
        when (sharedPrefs.getString("accent_color", "original")) {
            "blue" -> context.setTheme(R.style.AppTheme_Blue)
            "green" -> context.setTheme(R.style.AppTheme_Green)
            "orange" -> context.setTheme(R.style.AppTheme_Orange)
            "yellow" -> context.setTheme(R.style.AppTheme_Yellow)
            "teal" -> context.setTheme(R.style.AppTheme_Teal)
            "violet" -> context.setTheme(R.style.AppTheme_Violet)
            "pink" -> context.setTheme(R.style.AppTheme_Pink)
            "lightBlue" -> context.setTheme(R.style.AppTheme_LightBlue)
            "red" -> context.setTheme(R.style.AppTheme_Red)
            "lime" -> context.setTheme(R.style.AppTheme_Lime)
            "crimson" -> context.setTheme(R.style.AppTheme_Crimson)
            else -> context.setTheme(R.style.AppTheme_Original)
        }
    }

    /**
     * Sets status bar as accent colour when in light mode or a dark grey when in dark mode
     * @param [context] context of activity (Context)
     * @param [window] context of window (Window)
     */

    fun setStatusBar(context: Context, window: Window) {
        when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> window.statusBarColor = getAccentColourAsInt(context)
            Configuration.UI_MODE_NIGHT_YES -> window.statusBarColor = getColor(context, R.color.darkModeBar)
        }
    }

    /**
     * Returns the accent colour when in light mode or a dark grey when in dark mode
     * @param [context] context of activity (Context)
     * @return The integer of the colour (Int)
     */

    fun toolbarColour(context: Context): Int {
        when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> return getAccentColourAsInt(context)
            Configuration.UI_MODE_NIGHT_YES -> return getColor(context, R.color.darkModeBar)
        }
        return -1
    }

    /**
     * Gets the current colour and returns it as a string
     * @param [context] context of activity (Context)
     * @return The name of the colour (String)
     */

    fun getAccentColourAsString(context: Context): String {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPrefs.getString("accent_color", "original").toString()
    }

    /**
     * Gets the current colour and returns it as an int
     *
     * @param [context] context of activity (Context)
     * @return The accent colour (Int)
     */

    fun getAccentColourAsInt(context: Context): Int {
        val typedValue = TypedValue()
        val theme: Resources.Theme = context.theme
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        return typedValue.data
    }
}
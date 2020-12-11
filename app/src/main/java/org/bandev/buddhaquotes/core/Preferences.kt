package org.bandev.buddhaquotes.core

import android.content.Context
import androidx.preference.PreferenceManager

/**
 * The Preferences class allows our app to more easily interact with shared preferences
 *
 * @author Fennec_exe
 * @since 1.5.0
 * @updated 10/12/2020
 */

class Preferences {

    /**
     * Returns a Boolean, true if Shapes Mode is on, false if it's not on
     * @param [context] context of activity (Context)
     * @return true if Shapes Mode is on, false if it's not on (Boolean)
     */

    fun shapesMode(context: Context): Boolean {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPrefs.getBoolean("shapes_mode", false)
    }

    /**
     * Returns 0 for light mode, 1 for dark mode, 2 for system default
     * @param [context] context of activity (Context)
     * @return 0 for light mode, 1 for dark mode, 2 for system default (Int)
     */

    fun appThemeInt(context: Context): Int {
        val pref = context.getSharedPreferences("Settings", 0)
        return pref.getInt("appThemeInt", 2)
    }
}
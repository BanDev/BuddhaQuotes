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

}
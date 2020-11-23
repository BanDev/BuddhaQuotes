package org.bandev.buddhaquotes.core

import android.content.Context
import androidx.preference.PreferenceManager

class Preferences {

    fun shapesMode(context: Context): Boolean{
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPrefs.getBoolean("shapes_mode", false)
    }
}
package org.bandev.buddhaquotes.core

import android.content.Context

class Theme {

    /**
     * Returns 0 for light mode, 1 for dark mode, 2 for system default
     * @param [context] context of activity (Context)
     * @return 0 for light mode, 1 for dark mode, 2 for system default (Int)
     */

    fun getAppTheme(context: Context): Int {
        val pref = context.getSharedPreferences("Settings", 0)
        return pref.getInt("appThemeInt", 2)
    }
}
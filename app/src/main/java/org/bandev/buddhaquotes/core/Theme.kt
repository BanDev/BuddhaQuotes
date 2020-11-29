package org.bandev.buddhaquotes.core

import android.content.Context

class Theme {

    fun getAppTheme(context: Context): Int {
        val pref = context.getSharedPreferences("Settings", 0)
        return pref.getInt("appThemeInt", 2)
    }

}
package org.bandev.buddhaquotes.core

import android.content.Context
import com.afollestad.materialdialogs.utils.MDUtil.getStringArray
import org.bandev.buddhaquotes.R

class Theme {

    fun getAppTheme(context: Context) : Int{
        val pref = context.getSharedPreferences("Settings", 0)
        return pref.getInt("appThemeInt", 2)
    }

}
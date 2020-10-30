package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.Window
import androidx.annotation.ColorInt
import androidx.preference.PreferenceManager
import org.bandev.buddhaquotes.R

class Colours {

    /**
     * Sets activity's theme based off setting from preferences
     * @param [context] context of activity (Context)
     * @param [window] context of window (Window)
     * @author jack.txt
     * @added [1007] v1.5.0 - 29/10/2020
     */

    fun setAccentColor(context: Context, window: Window){
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val accent = sharedPrefs.getString("accent_color", "original")
        when (accent) {
            "blue" -> context.setTheme(R.style.AppTheme_blue)
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
            else -> context.setTheme(R.style.AppTheme)
        }
        val typedValue = TypedValue()
        val theme: Resources.Theme = context.getTheme()
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        val color = typedValue.data
        window.statusBarColor = color
    }
}
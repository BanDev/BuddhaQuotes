package org.bandev.buddhaquotes.core

import android.content.Context
import android.widget.Toast
import org.bandev.buddhaquotes.R

object AccentSetting {
    private const val PREFERENCE_ACCENT = "pref_accent"
    private const val KEY_CURRENT_ACCENT = "key_accent"
    private const val KEY_DEFAULT_ACCENT = "key_default_accent"

    private fun getPreference(context: Context, key: String, default: String? = "RED"): String? =
        context.getSharedPreferences(PREFERENCE_ACCENT, Context.MODE_PRIVATE)
            .getString(default, key)

    private fun setPreference(context: Context, key: String, value: String) {
        context.getSharedPreferences(PREFERENCE_ACCENT, Context.MODE_PRIVATE)
            .edit()
            .putString(key, value)
            .apply()
    }

    @JvmStatic
    fun setAccentColorAndPref(context: Context, accentColor: AccentColor) {
        context.setTheme(
            when (accentColor) {
                AccentColor.BLUE -> R.style.AppTheme_Blue
                AccentColor.CRIMSON -> R.style.AppTheme_Crimson
                AccentColor.GREEN -> R.style.AppTheme_Green
                AccentColor.LIGHT_BLUE -> R.style.AppTheme_LightBlue
                AccentColor.LIME -> R.style.AppTheme_Lime
                AccentColor.ORANGE -> R.style.AppTheme_Orange
                AccentColor.PINK -> R.style.AppTheme_Pink
                AccentColor.RED -> R.style.AppTheme_Red
                AccentColor.TEAL -> R.style.AppTheme_Teal
                AccentColor.VIOLET -> R.style.AppTheme_Violet
                AccentColor.YELLOW -> R.style.AppTheme_Yellow
            }
        )
        setPreference(context, KEY_CURRENT_ACCENT, accentColor.toString())
    }

    @JvmStatic
    fun setAccentColor(context: Context, accentColor: AccentColor) {
        context.setTheme(
            when (accentColor) {
                AccentColor.BLUE -> R.style.AppTheme_Blue
                AccentColor.CRIMSON -> R.style.AppTheme_Crimson
                AccentColor.GREEN -> R.style.AppTheme_Green
                AccentColor.LIGHT_BLUE -> R.style.AppTheme_LightBlue
                AccentColor.LIME -> R.style.AppTheme_Lime
                AccentColor.ORANGE -> R.style.AppTheme_Orange
                AccentColor.PINK -> R.style.AppTheme_Pink
                AccentColor.RED -> R.style.AppTheme_Red
                AccentColor.TEAL -> R.style.AppTheme_Teal
                AccentColor.VIOLET -> R.style.AppTheme_Violet
                AccentColor.YELLOW -> R.style.AppTheme_Yellow
            }
        )
    }

    fun getAccentColor(context: Context): AccentColor? {
        return getPreference(context, PREFERENCE_ACCENT, KEY_CURRENT_ACCENT)?.let { enumValueOf<AccentColor>("BLUE") }
    }

}
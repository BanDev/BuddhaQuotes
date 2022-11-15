package org.bandev.buddhaquotes.core

import android.content.Context
import android.widget.Toast
import org.bandev.buddhaquotes.R

object AccentSetting {
    private const val PREFERENCE_ACCENT = "pref_accent"
    private const val KEY_CURRENT_ACCENT = "key_accent"

    private val accentColorMap: Map<AccentColor, Int> = mapOf(
        AccentColor.BLUE to R.style.AppTheme_Blue, AccentColor.CRIMSON to R.style.AppTheme_Crimson,
        AccentColor.GREEN to R.style.AppTheme_Green, AccentColor.LIGHT_BLUE to R.style.AppTheme_LightBlue,
        AccentColor.LIME to R.style.AppTheme_Lime, AccentColor.ORANGE to R.style.AppTheme_Orange,
        AccentColor.PINK to R.style.AppTheme_Red, AccentColor.TEAL to R.style.AppTheme_Teal,
        AccentColor.VIOLET to R.style.AppTheme_Violet, AccentColor.YELLOW to R.style.AppTheme_Yellow,
        AccentColor.ORIGINAL to R.style.AppTheme_Original
    )

    private fun getPreference(context: Context, key: String, default: String? = AccentColor.ORIGINAL.toString()): String? =
        context.getSharedPreferences(PREFERENCE_ACCENT, Context.MODE_PRIVATE)
            .getString(key, default)

    private fun setPreference(context: Context, key: String, value: String) {
        context.getSharedPreferences(PREFERENCE_ACCENT, Context.MODE_PRIVATE)
            .edit()
            .putString(key, value)
            .apply()
    }

    @JvmStatic
    fun setAccentColorAndPref(context: Context, accentColor: AccentColor) {
        accentColorMap[accentColor]?.let { context.setTheme(it) }
        setPreference(context, KEY_CURRENT_ACCENT, accentColor.toString())
    }

    @JvmStatic
    fun setAccentColor(context: Context, accentColor: AccentColor) {
        accentColorMap[accentColor]?.let { context.setTheme(it) }
    }

    fun getAccentColor(context: Context): AccentColor? {
        return getPreference(context, KEY_CURRENT_ACCENT)?.let { enumValueOf<AccentColor>(it) }
    }

}
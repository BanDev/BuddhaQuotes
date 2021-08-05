package org.bandev.buddhaquotes.core

import android.content.Context
import org.bandev.buddhaquotes.R

object Accent {
    fun Context.setAccentColour() {

        val accent = Prefs(this).Settings().accent

        this.setTheme(
            when (accent) {
                BLUE -> R.style.AppTheme_Blue
                CRIMSON -> R.style.AppTheme_Crimson
                GREEN -> R.style.AppTheme_Green
                LIGHT_BLUE -> R.style.AppTheme_LightBlue
                LIME -> R.style.AppTheme_Lime
                ORANGE -> R.style.AppTheme_Orange
                PINK -> R.style.AppTheme_Pink
                RED -> R.style.AppTheme_Red
                TEAL -> R.style.AppTheme_Teal
                VIOLET -> R.style.AppTheme_Violet
                YELLOW -> R.style.AppTheme_Yellow
                else -> R.style.AppTheme_Original
            }
        )
    }

    const val DEFAULT: Int = -1
    const val BLUE: Int = 0
    const val CRIMSON: Int = 1
    const val GREEN: Int = 2
    const val LIGHT_BLUE: Int = 3
    const val LIME: Int = 4
    const val ORANGE: Int = 5
    const val PINK: Int = 6
    const val RED: Int = 7
    const val TEAL: Int = 8
    const val VIOLET: Int = 9
    const val YELLOW: Int = 10
}
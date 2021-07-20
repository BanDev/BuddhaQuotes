/**

Buddha Quotes
Copyright (C) 2021  BanDev

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewConfiguration
import android.view.Window
import android.view.WindowInsetsController
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.items.ListIcon
import org.bandev.buddhaquotes.items.Quote

/**
 * Sets navigation bar colour based off android version
 */

@Suppress("DEPRECATION")
fun Window.setNavigationBarColourDefault() {
    if (!ViewConfiguration.get(this.context).hasPermanentMenuKey()) {
        when (this.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) this.decorView.windowInsetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, // value
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS // mask
                )
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) this.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) this.navigationBarColor =
                    Color.WHITE
                else this.navigationBarColor = Color.GRAY
            }
            Configuration.UI_MODE_NIGHT_YES -> this.navigationBarColor =
                getColor(this.context, R.color.background)
        }
    }
}

/**
 * Sets navigation bar colour based off android version only for Main
 * @param [context] context of activity (Context)
 */

@Suppress("DEPRECATION")
fun Window.setNavigationBarColourMain() {
    if (!ViewConfiguration.get(this.context).hasPermanentMenuKey()) {
        when (this.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) this.decorView.windowInsetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, // value
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS // mask
                )
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) this.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) this.navigationBarColor =
                    Color.WHITE
                else this.navigationBarColor = Color.GRAY
            }
            Configuration.UI_MODE_NIGHT_YES -> this.navigationBarColor =
                getColor(this.context, R.color.abbBackgroundColor)
        }
    }
}

@Suppress("DEPRECATION")
fun Window.setDarkStatusIcons() {
    when (this.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_NO -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                this.decorView.windowInsetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, // value
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS // mask
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}

/**
 * Sets activity's theme based off setting from preferences
 * @param [context] context of activity (Context)
 */

fun setAccentColour(context: Context) {
    val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)

    when (sharedPrefs.getString("accent_color", "original")) {
        "blue" -> context.setTheme(R.style.AppTheme_Blue)
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
        else -> context.setTheme(R.style.AppTheme_Original)
    }
}

/**
 * Gets the current colour and returns it as a string
 * @param [context] context of activity (Context)
 * @return The name of the colour (String)
 */

fun getAccentColourAsString(context: Context): String {
    val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
    return sharedPrefs.getString("accent_color", "original").toString()
}

/**
 * A function to draw behind system bars
 * @param [customInsets] The customInsets
 */
fun Window.fitSystemBars(customInsets: CustomInsets) {
    WindowCompat.setDecorFitsSystemWindows(this, false)

    setOnApplyWindowInsetsListener(this.decorView) { _, insets ->
        customInsets.setCustomInsets(insets)
        insets
    }
}

/**
 * Custom Insets interface for each activity to get custom insets
 */

interface CustomInsets {
    fun setCustomInsets(insets: WindowInsetsCompat)
}

private fun Context.resolveThemeAttr(@AttrRes attrRes: Int): TypedValue {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue
}

@ColorInt
fun Context.resolveColorAttr(@AttrRes colorAttr: Int): Int {
    val resolvedAttr = resolveThemeAttr(colorAttr)
    val colorRes = if (resolvedAttr.resourceId != 0) resolvedAttr.resourceId else resolvedAttr.data
    return getColor(this, colorRes)
}

fun Context.shareQuote(quote: Quote) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            getString(quote.resource) + "\n\n" + getString(R.string.attribution_buddha)
        )
        type = "text/plain"
    }
    startActivity(Intent.createChooser(sendIntent, null))
}

fun listIcons(): List<ListIcon> {
    return listOf(
        ListIcon(
            0,
            R.drawable.ic_add,
            0xFF0067f4.toInt()
        ),
        ListIcon(
            1,
            R.drawable.ic_heart_outline,
            0xFF349334.toInt()
        ),
        ListIcon(
            2,
            R.drawable.ic_arrow_back,
            0xFFFF5733.toInt()
        ),
        ListIcon(
            3,
            R.drawable.ic_menu,
            0xFFFF33DA.toInt()
        ),
        ListIcon(
            4,
            R.drawable.ic_tune,
            0xFF0A7AA7.toInt()
        )
    )
}

fun defaultIcon(): ListIcon {
    return ListIcon(
        0,
        R.drawable.ic_heart_outline,
        0xFF0067f4.toInt()
    )
}
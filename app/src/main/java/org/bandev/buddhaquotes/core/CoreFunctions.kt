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
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.*
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.preference.PreferenceManager
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.paddingDp
import com.mikepenz.iconics.utils.sizeDp
import nl.joery.animatedbottombar.AnimatedBottomBar
import org.bandev.buddhaquotes.R

/**
 * Sets navigation bar colour based off android version
 * @param [context] context of activity (Context)
 */

@Suppress("DEPRECATION")
fun Window.setNavigationBarColourDefault(context: Context) {
    if (!ViewConfiguration.get(context).hasPermanentMenuKey()) {
        when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
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
                ContextCompat.getColor(context, R.color.background)
        }
    }
}

/**
 * Sets navigation bar colour based off android version only for Main
 * @param [context] context of activity (Context)
 */

@Suppress("DEPRECATION")
fun Window.setNavigationBarColourMain(context: Context) {
    if (!ViewConfiguration.get(context).hasPermanentMenuKey()) {
        when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
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
                ContextCompat.getColor(context, R.color.abbBackgroundColor)
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
 * Sets status bar as accent colour when in light mode or a dark grey when in dark mode
 * @param [context] context of activity (Context)
 */

fun Window.setStatusBarAsAccentColour(context: Context) {
    when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_NO -> this.statusBarColor =
            context.resolveColorAttr(R.attr.colorPrimary)
        Configuration.UI_MODE_NIGHT_YES -> this.statusBarColor =
            ContextCompat.getColor(context, R.color.darkModeBar)
    }
}

/**
 * Returns the accent colour when in light mode or a dark grey when in dark mode
 * @param [context] context of activity (Context)
 * @return The integer of the colour (Int)
 */

fun toolbarColour(context: Context): Int {
    when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_NO -> return context.resolveColorAttr(R.attr.colorPrimary)
        Configuration.UI_MODE_NIGHT_YES -> return ContextCompat.getColor(
            context,
            R.color.darkModeBar
        )
    }
    return -1
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
 * @param [view] The view
 * @param [window] The window
 * @param [customInsets] The customInsets
 */
fun fitSystemBars(view: View, window: Window, customInsets: CustomInsets) {
    WindowCompat.setDecorFitsSystemWindows(window, false)

    ViewCompat.setOnApplyWindowInsetsListener(view.rootView) { _, insets ->
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
    return ContextCompat.getColor(this, colorRes)
}

fun Context.hamburgerMenuIcon(): IconicsDrawable =
    IconicsDrawable(this, RoundedGoogleMaterial.Icon.gmr_menu).apply {
        colorInt = Color.WHITE
        sizeDp = 18
    }

fun Context.backIcon(): IconicsDrawable =
    IconicsDrawable(this, RoundedGoogleMaterial.Icon.gmr_arrow_back).apply {
        colorInt = Color.WHITE
        sizeDp = 16
    }

fun Context.addIcon(): IconicsDrawable =
    IconicsDrawable(this, RoundedGoogleMaterial.Icon.gmr_add).apply {
        colorInt = Color.WHITE
        sizeDp = 16
    }

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
import android.view.View
import android.view.ViewConfiguration
import android.view.Window
import android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
import androidx.core.content.ContextCompat.getColor
import org.bandev.buddhaquotes.R

/**
 * The compatibility class ensures that Buddha Quotes works well on any android version that our
 * app supports.
 *
 * @author Fennec_exe
 * @since v1.5.0
 * @updated 13/03/2021
 */

@Suppress("DEPRECATION")
class Compatibility {

    /**
     * Sets navigation bar colour based off android version
     * @param [context] context of activity (Context)
     * @param [window] context of window (Window)
     */

    fun setNavigationBarColourDefault(context: Context, window: Window) {
        // Only run the code if the user has a navigation bar
        if (!ViewConfiguration.get(context).hasPermanentMenuKey()) {
            when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    // If using >= Android 11, set the navigation bar icons to be dark using the newer function
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        window.decorView.windowInsetsController?.setSystemBarsAppearance(
                            APPEARANCE_LIGHT_NAVIGATION_BARS, // value
                            APPEARANCE_LIGHT_NAVIGATION_BARS // mask
                        )
                    }
                    // If using Android 8-10, set the navigation bar icons to be dark with the deprecated function
                    else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                    }
                    // If using >= Android 8, set the navigation bar colour to white to match dark icons
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        window.navigationBarColor = Color.WHITE
                    }
                    // If using < Android 8, set the navigation bar colour to gray as dark icons were not supported before Android 8
                    else window.navigationBarColor = Color.GRAY
                }
                // If using dark mode, set the navigation bar colour to the colour of the background
                Configuration.UI_MODE_NIGHT_YES -> window.navigationBarColor =
                    getColor(context, R.color.background)
            }
        }
    }

    /**
     * Sets navigation bar colour based off android version only for Main
     * @param [context] context of activity (Context)
     * @param [window] context of window (Window)
     */

    fun setNavigationBarColourMain(context: Context, window: Window) {
        // Only run the code if the user has a navigation bar
        if (!ViewConfiguration.get(context).hasPermanentMenuKey()) {
            when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    // If using >= Android 11, set the navigation bar icons to be dark using the newer function
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        window.decorView.windowInsetsController?.setSystemBarsAppearance(
                            APPEARANCE_LIGHT_NAVIGATION_BARS, // value
                            APPEARANCE_LIGHT_NAVIGATION_BARS // mask
                        )
                    }
                    // If using Android 8-10, set the navigation bar icons to be dark with the deprecated function
                    else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                    }
                    // If using >= Android 8, set the navigation bar colour to white to match dark icons
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        window.navigationBarColor = Color.WHITE
                    }
                    // If using < Android 8, set the navigation bar colour to gray as dark icons were not supported before Android 8
                    else window.navigationBarColor = Color.GRAY
                }
                // If using dark mode, set the navigation bar colour to the same colour as the bottom bar on the Main Activity
                Configuration.UI_MODE_NIGHT_YES -> window.navigationBarColor =
                    getColor(context, R.color.abbBackgroundColor)
            }
        }
    }
}

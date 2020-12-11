package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController

/**
 * The compatibility class ensures that Buddha Quotes works well on any android version that our
 * app supports.
 *
 * @author Fennec_exe
 * @since v1.5.0
 * @updated 29/10/2020
 */

class Compatibility {

    /**
     * Sets navigation bar colour based off android version
     * @param [context] context of activity (Context)
     * @param [window] context of window (Window)
     */

    fun setNavigationBarColour(context: Context, window: Window) {
        when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    @Suppress("DEPRECATION")
                    window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    window.navigationBarColor = Color.TRANSPARENT
                }
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                window.navigationBarColor = Color.TRANSPARENT
            }
        }
    }
}
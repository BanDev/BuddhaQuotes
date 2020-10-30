package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.core.content.res.ResourcesCompat
import org.bandev.buddhaquotes.R

class Compatability {

    /**
     * Sets navigation bar based off android version
     * @param [context] context of activity (Context)
     * @param [window] context of window (Window)
     * @param [resources] context of resources (Resources)
     * @author Fennec_exe
     * @added [1007] v1.5.0 - 29/10/2020
     */

    fun setNavigationBarColour(context: Context, window: Window, resources: Resources){
        when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    window.navigationBarColor =
                        ResourcesCompat.getColor(resources, R.color.transparent, null)
                } else {
                    window.navigationBarColor =
                        ResourcesCompat.getColor(resources, R.color.dark_nav_bar, null)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                window.navigationBarColor = ResourcesCompat.getColor(resources, R.color.transparent, null)
            }
        }
    }
}
package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat.getColor
import org.bandev.buddhaquotes.R

object Bars {

    fun Window.updateNavbarColour(ctx: Context = this.context) {
        if(!inDarkMode(ctx)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                this.decorView.windowInsetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, // value
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS // mask
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
            this.navigationBarColor = getColor(ctx, R.color.navbar)
        }
    }

    private fun inDarkMode(context: Context): Boolean {
        context.resources.configuration.also {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                it.isNightModeActive
            } else {
                it.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
            }
        }
    }

}
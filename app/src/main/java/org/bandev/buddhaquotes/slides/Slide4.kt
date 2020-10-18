package org.bandev.buddhaquotes.slides

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import org.bandev.buddhaquotes.MainActivity
import org.bandev.buddhaquotes.R

class Slide4 : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_done)

        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            Configuration.UI_MODE_NIGHT_UNDEFINED -> window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.transparent, null)

        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
            } // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor =
                ResourcesCompat.getColor(resources, R.color.transparent, null)
        } else {
            window.navigationBarColor =
                ResourcesCompat.getColor(resources, R.color.black, null)
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        val view = View(this)
        view.doOnLayout {
            view.windowInsetsController?.show(WindowInsets.Type.ime())
            // You can also access it from Window
            window.insetsController?.show(WindowInsets.Type.ime())
        }

        view.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = insets.systemWindowInsetBottom)
            insets
        }

        var navBarHeight = 0
        val resourceId2 = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId2 > 0) {
            navBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        // val param2 = (favourite ?: return).layoutParams as ViewGroup.MarginLayoutParams
        //   param2.setMargins(0, 0, 0, navBarHeight)
        //   (favourite ?: return).layoutParams = param2

        //    val param3 = (refresh ?: return).layoutParams as ViewGroup.MarginLayoutParams
        //     param3.setMargins(0, 0, 0, navBarHeight)
        //    (refresh ?: return).layoutParams = param3

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            val myIntent = Intent(this@Slide4, MainActivity::class.java)
            this@Slide4.startActivity(myIntent)
        }

    }
}

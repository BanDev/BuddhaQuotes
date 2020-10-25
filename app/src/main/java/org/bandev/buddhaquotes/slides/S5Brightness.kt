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
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import org.bandev.buddhaquotes.R

class S5Brightness : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s5brightness)

        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    window.navigationBarColor =
                        ResourcesCompat.getColor(resources, R.color.transparent, null)
                } else {
                    window.navigationBarColor =
                        ResourcesCompat.getColor(resources, R.color.dark_nav_bar, null)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window.statusBarColor =
                        ResourcesCompat.getColor(resources, R.color.transparent, null)
                } else {
                    window.statusBarColor =
                        ResourcesCompat.getColor(resources, R.color.colorTop, null)
                }
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                window.navigationBarColor =
                    ResourcesCompat.getColor(resources, R.color.transparent, null)
                window.statusBarColor =
                    ResourcesCompat.getColor(resources, R.color.transparent, null)
            }
        }

        val night: Button = findViewById(R.id.night)
        val day: Button = findViewById(R.id.day)

        night.setOnClickListener {
            val pref = this.getSharedPreferences("Settings", 0)
            val editor = pref.edit()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            editor.putBoolean("dark_mode", true)
            editor.putBoolean("sys", false)
            editor.apply()

            val myIntent = Intent(this@S5Brightness, S6TextSize::class.java)
            this@S5Brightness.startActivity(myIntent)
            overridePendingTransition(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left
            )
        }

        day.setOnClickListener {
            val pref = this.getSharedPreferences("Settings", 0)
            val editor = pref.edit()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            editor.putBoolean("dark_mode", false)
            editor.putBoolean("sys", false)
            editor.apply()

            val myIntent = Intent(this@S5Brightness, S6TextSize::class.java)
            this@S5Brightness.startActivity(myIntent)
            overridePendingTransition(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left
            )
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
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
    }
}

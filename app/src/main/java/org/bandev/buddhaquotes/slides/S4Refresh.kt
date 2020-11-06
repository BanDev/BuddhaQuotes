package org.bandev.buddhaquotes.slides

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.bandev.buddhaquotes.R

class S4Refresh : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s4refresh)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

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

        WindowCompat.setDecorFitsSystemWindows(window, false)

        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        val view = View(this)
        view.doOnLayout {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                view.windowInsetsController?.show(WindowInsets.Type.ime())
            }
            // You can also access it from Window
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                window.insetsController?.show(WindowInsets.Type.ime())
            }
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

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            val myIntent = Intent(this@S4Refresh, S5Brightness::class.java)
            this@S4Refresh.startActivity(myIntent)
            overridePendingTransition(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left
            )
        }

        val favourite: FloatingActionButton = findViewById(R.id.favourite)

        val text: TextView = findViewById(R.id.text)

        var counter = 1
        val rotateAnimation = RotateAnimation(
            0F,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )

        var noanim = true
        favourite.setOnClickListener {
            if (noanim) {
                rotateAnimation.duration = 2.toLong() * 250
                favourite.startAnimation(rotateAnimation)
                noanim = false
            } else if (rotateAnimation.hasEnded()) {
                rotateAnimation.duration = 2.toLong() * 250
                favourite.startAnimation(rotateAnimation)
                noanim = true
            }

            if (counter < 50) {
                text.text =
                    getString(R.string.more_counter_left, counter)
                counter + 1
            } else {
                text.text =
                    getString(R.string.more_lots_of_refreshes, counter)
                counter + 1
            }
            counter++
        }

    }
}

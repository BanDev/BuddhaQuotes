package org.bandev.buddhaquotes.slides

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import com.google.android.material.slider.Slider
import org.bandev.buddhaquotes.R

class S6TextSize : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s6textsize)

        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    window.navigationBarColor =
                        ResourcesCompat.getColor(resources, R.color.transparent, null)
                } else {
                    window.navigationBarColor =
                        ResourcesCompat.getColor(resources, R.color.colorTop, null)
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }
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

        val slider: Slider = findViewById(R.id.slider)
        val button: Button = findViewById(R.id.button)
        val text2: TextView = findViewById(R.id.text)

        slider.addOnChangeListener { slider, value, fromUser ->
            // Responds to when slider's value is changed
            val pref = this.getSharedPreferences("Settings", 0)
            val editor = pref.edit()
            val fontsize = when (value) {
                0f -> 25f
                20f -> 35f
                else -> 30f
            }

            text2.textSize = fontsize

            val string: String = when (fontsize) {
                25f -> "sm"
                35f -> "lg"
                else -> "md"
            }
            editor.putString("text_size", string)
            editor.apply()
        }

        button.setOnClickListener {
            if (slider.value == 10f) {
                val pref = this.getSharedPreferences("Settings", 0)
                val editor = pref.edit()
                editor.putString("text_size", "md")
                editor.apply()
            }

            val myIntent = Intent(this@S6TextSize, S7End::class.java)
            this@S6TextSize.startActivity(myIntent)
            overridePendingTransition(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left
            )
        }
    }
}

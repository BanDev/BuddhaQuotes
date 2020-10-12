package org.bandev.buddhaquotes

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import com.google.android.material.slider.Slider

class Slide3 : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_3)

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
                0f -> 30f
                20f -> 50f
                else -> 40f
            }

            text2.textSize = fontsize

            val string: String = when (fontsize) {
                30f -> "sm"
                50f -> "lg"
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

            val myIntent = Intent(this@Slide3, Slide4::class.java)
            this@Slide3.startActivity(myIntent)
            overridePendingTransition(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left
            )
        }

        window.navigationBarColor = ResourcesCompat.getColor(resources, R.color.transparent, null)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
    }
}

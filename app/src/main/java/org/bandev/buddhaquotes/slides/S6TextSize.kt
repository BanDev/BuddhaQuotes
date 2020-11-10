package org.bandev.buddhaquotes.slides

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.WindowInsets
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import com.google.android.material.slider.Slider
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.Compatibility

class S6TextSize : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s6textsize)
        Compatibility().slideCompatability(this, window, resources)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        WindowCompat.setDecorFitsSystemWindows(window, false)

        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        val view = View(this)
        view.doOnLayout {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                view.windowInsetsController?.show(WindowInsets.Type.ime())
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
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
            window.decorView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            this@S6TextSize.startActivity(myIntent)
            overridePendingTransition(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left
            )
        }
    }
}

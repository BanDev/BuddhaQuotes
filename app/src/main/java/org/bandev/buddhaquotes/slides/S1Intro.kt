package org.bandev.buddhaquotes.slides

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.WindowInsets
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import org.bandev.buddhaquotes.R

class S1Intro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s1intro)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            window.decorView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            val myIntent =
                Intent(this@S1Intro, S2Promise::class.java)
            this@S1Intro.startActivity(myIntent)
            overridePendingTransition(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left
            )
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.navigationBarColor = ResourcesCompat.getColor(resources, R.color.transparent, null)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.transparent, null)

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
    }
}

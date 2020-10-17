package org.bandev.buddhaquotes

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class More : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            Configuration.UI_MODE_NIGHT_UNDEFINED -> window.decorView.systemUiVisibility =
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

        // val param2 = (favourite ?: return).layoutParams as ViewGroup.MarginLayoutParams
        //   param2.setMargins(0, 0, 0, navBarHeight)
        //   (favourite ?: return).layoutParams = param2

        //    val param3 = (refresh ?: return).layoutParams as ViewGroup.MarginLayoutParams
        //     param3.setMargins(0, 0, 0, navBarHeight)
        //    (refresh ?: return).layoutParams = param3

        window.navigationBarColor = ResourcesCompat.getColor(resources, R.color.transparent, null)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.transparent, null)

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            val myIntent = Intent(this@More, Slide2::class.java)
            this@More.startActivity(myIntent)
            overridePendingTransition(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left
            )
        }

        val favourite: FloatingActionButton = findViewById(R.id.favourite)

        val text: TextView = findViewById(R.id.text)

        var counter = 0

        favourite.setOnClickListener {
            val rotateAnimation = RotateAnimation(
                0F,
                360f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            if (counter < 50) {
                text.text =
                    getString(R.string.more_counter_left) + (counter + 1)
            } else {
                text.text = getString(R.string.more_lots_of_refreshes) + (counter + 1)
            }
            counter++
            rotateAnimation.duration = 2.toLong() * 250
            favourite.startAnimation(rotateAnimation)
        }
    }
}

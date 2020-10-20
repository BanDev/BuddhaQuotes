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
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.plattysoft.leonids.ParticleSystem
import org.bandev.buddhaquotes.R

class S3Favourite : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s3favourite)

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

        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.transparent, null)

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            val myIntent = Intent(this@S3Favourite, S4Refresh::class.java)
            this@S3Favourite.startActivity(myIntent)
            overridePendingTransition(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left
            )
        }

        val favourite: FloatingActionButton = findViewById(R.id.favourite)

        var done = false

        favourite.setOnClickListener {
            if (!done) {
                val like = ParticleSystem(this, 5, R.drawable.heart_full_red, 600)
                like.setSpeedRange(0.0750f, 0.0750f)
                like.setFadeOut(100)
                like.setScaleRange(0.5f, 1f)
                like.oneShot(favourite, 5)
                favourite.isEnabled = false
                // If It Is Not Liked Already
                favourite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@S3Favourite,
                        R.drawable.heart_full_white
                    )
                )
                favourite.isEnabled = true
                done = true
            } else {
                // If It Is Already Liked
                // val like = ParticleSystem(this, 5, R.drawable.heart_white, 600)
                // like.setSpeedRange(0.0625f, 0.0625f)
                // like.setFadeOut(100)
                // like.oneShot(favourite, 5);
                favourite.isEnabled = false
                done = false
                favourite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@S3Favourite,
                        R.drawable.heart_white
                    )
                )
                favourite.isEnabled = true
            }
        }
    }
}
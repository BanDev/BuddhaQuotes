package org.bandev.buddhaquotes.slides

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.WindowInsets
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.plattysoft.leonids.ParticleSystem
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.Compatibility

class S3Favourite : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s3favourite)
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
            // You can also access it from Window
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

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            window.decorView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
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
            window.decorView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
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

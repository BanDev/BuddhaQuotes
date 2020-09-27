@file:Suppress("SpellCheckingInspection")

package org.bandev.buddhaquotes

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_about.*
import nl.dionsegijn.konfetti.models.Size


class About : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        var done = false

        val scrollview: ScrollView = findViewById(R.id.scroll)
        scrollview.viewTreeObserver
            .addOnScrollChangedListener {
                if (scrollview.getChildAt(0).bottom
                    <= scrollview.height + scrollview.scrollY
                    && !done
                ) {
                    viewKonfetti.build()
                        .addColors(
                            Color.parseColor("#a864fd"),
                            Color.parseColor("#29cdff"),
                            Color.parseColor("#78ff44"),
                            Color.parseColor("#ff718d"),
                            Color.parseColor("#fdff6a")
                        )
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(
                            nl.dionsegijn.konfetti.models.Shape.RECT,
                            nl.dionsegijn.konfetti.models.Shape.CIRCLE
                        )
                        .addSizes(Size(10))
                        .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
                        .streamFor(100, 1000L)
                    vibratePhone()
                    vibratePhone()
                    val contextView = findViewById<View>(R.id.context_view)

                    Snackbar.make(contextView, "Thanks for reading!", Snackbar.LENGTH_SHORT)
                        .show()
                    done = true
                } else {
                    //scroll view is not at bottom
                }
            }


        val myToolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(myToolbar)
        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)


        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        val param = myToolbar.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0, statusBarHeight, 0, 0)
        myToolbar.layoutParams = param

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


    }

    private fun vibratePhone() {
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


}




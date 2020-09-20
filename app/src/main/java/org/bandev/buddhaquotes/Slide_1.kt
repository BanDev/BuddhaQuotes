package org.bandev.buddhaquotes

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Slide_1 : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_1)

        var next: FloatingActionButton = findViewById(R.id.next)

        next.setOnClickListener {
            val myIntent =
                Intent(this@Slide_1, Slide_2::class.java)
            this@Slide_1.startActivity(myIntent)
            overridePendingTransition(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left
            )
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

        //If Using Night Mode, Change Some Stuff
        // when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        //        Configuration.UI_MODE_NIGHT_NO -> window.decorView.systemUiVisibility =
        //           View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        //      Configuration.UI_MODE_NIGHT_UNDEFINED -> window.decorView.systemUiVisibility =
        //          View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        //   }
        window.navigationBarColor = resources.getColor(R.color.transparent)
        window.statusBarColor = resources.getColor(R.color.colorAccent)

    }
}
package org.bandev.buddhaquotes

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.TextView
import androidx.annotation.RawRes
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import java.io.*

class License : AppCompatActivity() {


    //Say that you need a snazzy phone to use the app (Android Ver > R)
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)

        //Setup toolbar which is the top bar thingy
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
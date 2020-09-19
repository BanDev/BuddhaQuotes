package org.bandev.buddhaquotes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
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


class About : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(
            R.anim.anim_slide_in_left,
            R.anim.anim_slide_out_left
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val scrollview: ScrollView = findViewById(R.id.scroll)
        scrollview.viewTreeObserver
            .addOnScrollChangedListener {
                if (scrollview.getChildAt(0).bottom
                    <= scrollview.height + scrollview.scrollY
                ) {

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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val i = Intent(this, settings::class.java)
                val mBundle = Bundle()
                mBundle.putString("quote", "0")
                i.putExtras(mBundle)
                startActivity(i)
                overridePendingTransition(
                    R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right
                )
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        val i = Intent(this, settings::class.java)
        val mBundle = Bundle()
        mBundle.putString("quote", "0")
        i.putExtras(mBundle)
        startActivity(i)
        overridePendingTransition(
            R.anim.anim_slide_in_right,
            R.anim.anim_slide_out_right
        )
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val i = Intent(this, settings::class.java)
        val mBundle = Bundle()
        mBundle.putString("quote", "0")
        i.putExtras(mBundle)
        startActivity(i)
        overridePendingTransition(
            R.anim.anim_slide_in_right,
            R.anim.anim_slide_out_right
        )
        finish()
        return true
    }


}




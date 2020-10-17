package org.bandev.buddhaquotes

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class FullLicense : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_license)
        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {

            } // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
        }
        // Toolbar
        val myToolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(myToolbar)
        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)
        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)
        window.statusBarColor = ContextCompat.getColor(this@FullLicense, R.color.colorTop)
        (supportActionBar ?: return).setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        // Webview
        val webview = findViewById<View>(R.id.webview) as WebView
        webview.loadUrl("file:///android_asset/Licenses/app.txt")
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
        // Add your animation
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

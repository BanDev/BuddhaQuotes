package org.bandev.buddhaquotes

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class FullLicense : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_license)

        // Toolbar
        val myToolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(myToolbar)
        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)
        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)

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

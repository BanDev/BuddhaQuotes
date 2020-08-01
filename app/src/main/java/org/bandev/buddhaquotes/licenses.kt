package org.bandev.buddhaquotes

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature

class licenses : AppCompatActivity() {
    var from_settings = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_licenses)
        val url2: String
        val b = intent.extras // or other values
        when (b!!.getString("from")) {
            "Kotlin" -> url2 = "file:///android_asset/kotlin.txt"
            "Androidx" -> url2 = "file:///android_asset/androidx.txt"
            "Material Design Icons" -> url2 = "file:///android_asset/mdi.txt"
            else -> {
                url2 = "file:///android_asset/license.txt"
                from_settings = true
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.colorPrimary)
        }
        val myToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(myToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val actionBar = supportActionBar
        assert(supportActionBar != null //null check
        )
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val browser = findViewById<View>(R.id.webview) as WebView
        val progress = findViewById<ProgressBar>(R.id.loader)
        val nightModeFlags = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                WebSettingsCompat.setForceDark(browser.settings, WebSettingsCompat.FORCE_DARK_ON)
            }
            Configuration.UI_MODE_NIGHT_NO -> window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            Configuration.UI_MODE_NIGHT_UNDEFINED -> window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        browser.loadUrl(url2)
        browser.visibility = View.GONE
        browser.settings.javaScriptEnabled = true
        browser.settings.javaScriptCanOpenWindowsAutomatically = true
        browser.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                browser.visibility = View.VISIBLE
                progress.visibility = View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (from_settings) {
            val i = Intent(this, settings::class.java)
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        } else {
            val i = Intent(this, oss_libraries::class.java)
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        if (from_settings) {
            val i = Intent(this, settings::class.java)
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        } else {
            val i = Intent(this, oss_libraries::class.java)
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (from_settings) {
                    val i = Intent(this, settings::class.java)
                    startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                } else {
                    val i = Intent(this, oss_libraries::class.java)
                    startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
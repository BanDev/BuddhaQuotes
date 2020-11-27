package org.bandev.buddhaquotes

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages

class FullLicense : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Colours().setAccentColor(this, window)
        Compatibility().setNavigationBarColour(this, window, resources)
        Languages().setLanguage(this)
        val language = Languages().getLanguage(this)
        setContentView(R.layout.activity_full_license)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Webview
        val webview = findViewById<View>(R.id.webview) as WebView
        when (language) {
            "ru" -> {
                webview.loadUrl("file:///android_asset/Licenses/russian.txt")
            }
            else -> {
                webview.loadUrl("file:///android_asset/Licenses/english.txt")
            }
        }

        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    WebSettingsCompat.setForceDark(
                        webview.settings,
                        WebSettingsCompat.FORCE_DARK_OFF
                    )
                }
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    WebSettingsCompat.setForceDark(
                        webview.settings,
                        WebSettingsCompat.FORCE_DARK_ON
                    )
                }
            }
        }

        val myToolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(myToolbar)
        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)
        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)
        (supportActionBar ?: return).setHomeAsUpIndicator(R.drawable.ic_arrow_back)

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
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

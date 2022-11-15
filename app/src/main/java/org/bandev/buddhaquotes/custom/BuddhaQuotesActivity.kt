package org.bandev.buddhaquotes.custom

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate
import com.akexorcist.localizationactivity.core.OnLocaleChangedListener
import org.bandev.buddhaquotes.core.*
import java.util.*

abstract class BuddhaQuotesActivity: AppCompatActivity, OnLocaleChangedListener {
    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    private val localizationDelegate: LocalizationActivityDelegate by lazy {
        LocalizationActivityDelegate(this)
    }

    private val accentDelegate: AccentDelegate by lazy {
        AccentDelegate(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        localizationDelegate.addOnLocaleChangedListener(this)
        localizationDelegate.onCreate()
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        accentDelegate.getAccentColor(this)?.let { accentDelegate.setAccentColor(this, it) }
        localizationDelegate.onResume(this)
    }

    override fun attachBaseContext(newBase: Context) {
        applyOverrideConfiguration(localizationDelegate.updateConfigurationLocale(newBase))
        super.attachBaseContext(newBase)
    }

    override fun getBaseContext(): Context {
        return localizationDelegate.getApplicationContext(super.getBaseContext())
    }

    override fun getApplicationContext(): Context {
        return localizationDelegate.getApplicationContext(super.getApplicationContext())
    }

    override fun getResources(): Resources {
        return localizationDelegate.getResources(super.getResources())
    }

    override fun onBeforeLocaleChanged() {}

    override fun onAfterLocaleChanged() {}

    fun setLanguage(language: String) {
        localizationDelegate.setLanguage(this, language)
    }

    fun setLanguage(language: String, country: String) {
        localizationDelegate.setLanguage(this, language, country)
    }

    fun setLanguage(locale: Locale) {
        localizationDelegate.setLanguage(this, locale)
    }

    fun setLanguageWithoutNotification(language: String) {
        localizationDelegate.setLanguageWithoutNotification(this, language)
    }

    fun setLanguageWithoutNotification(language: String, country: String) {
        localizationDelegate.setLanguageWithoutNotification(this, language, country)
    }

    fun setLanguageWithoutNotification(locale: Locale) {
        localizationDelegate.setLanguageWithoutNotification(this, locale)
    }

    fun getCurrentLanguage(): Locale {
        return localizationDelegate.getLanguage(this)
    }
}
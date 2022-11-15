package org.bandev.buddhaquotes

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate
import org.bandev.buddhaquotes.core.Prefs
import org.bandev.buddhaquotes.migrations.MigrationFrom1013To1014
import java.util.*

class BuddhaQuotesApplication : Application() {
    private val localizationDelegate = LocalizationApplicationDelegate()

    override fun attachBaseContext(base: Context) {
        localizationDelegate.setDefaultLanguage(base, Locale.ENGLISH)
        super.attachBaseContext(localizationDelegate.attachBaseContext(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localizationDelegate.onConfigurationChanged(this)
    }

    override fun getApplicationContext(): Context {
        return localizationDelegate.getApplicationContext(super.getApplicationContext())
    }

    override fun getResources(): Resources {
        return localizationDelegate.getResources(baseContext, super.getResources())
    }

    override fun onCreate() {
        super.onCreate()
        val prefs = Prefs(this).Settings()
        val theme = prefs.theme
        prefs.bottomBar = 0
        AppCompatDelegate.setDefaultNightMode(theme)
        migrate()
        createMeditationTimerChannel()

    }

    fun migrate() {
        MigrationFrom1013To1014(this, this).migrate()
    }

    private fun createMeditationTimerChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                "BQ.Timer",
                "Meditation Timer",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Meditation Timer Stuff"
            }.also { channel ->
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }

        }
    }
}
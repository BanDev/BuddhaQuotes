package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.res.Configuration
import androidx.preference.PreferenceManager
import java.util.*

class Languages {

    fun setLanguage(context: Context){
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val languageToLoad = sharedPrefs.getString("app_language", "en")

        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics
        )
    }
}
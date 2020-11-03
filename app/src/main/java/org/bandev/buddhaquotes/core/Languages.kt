package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.res.Configuration
import androidx.preference.PreferenceManager
import java.util.*

class Languages {

    /**
     * Sets the activity's langauge to the one selected by user
     * @param [context] context of activity (Context)
     * @author jack.txt
     * @added [1008] v1.5.0 - 30/10/2020
     */

    fun setLanguage(context: Context) {
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

    /**
     * Gets the current langauge from shared preferneces
     * @param [context] context of activity (Context)
     * @return string of iso code e.g. en or fr or ru
     * @author jack.txt
     * @added [1008] v1.5.0 - 03/11/2020
     */

    fun getLanguage(context: Context): String {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPrefs.getString("app_language", "en").toString()
    }
}
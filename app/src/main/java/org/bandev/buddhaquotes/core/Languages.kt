package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.res.Configuration
import androidx.preference.PreferenceManager
import java.util.*

/**
 * The Languages class manages the languages that Buddha Quotes supports. The language in ISO 639
 * format is stored in shared preferences with the key "app_language"
 *
 * @author jack.txt
 * @since v1.5.0
 * @updated 30/10/2020
 */

class Languages {

    /**
     * Sets the activity's langauge to the one selected by user
     * @param [context] context of activity (Context)
     */

    fun setLanguage(context: Context) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val languageToLoad = sharedPrefs.getString("app_language", "en").toString()

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
     * Gets the current langauge from shared preferences
     * @param [context] context of activity (Context)
     * @return index of the langauge in the array (Int)
     */

    fun getLanguageAsInt(context: Context): Int {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPrefs.getInt("app_language_int", 0)
    }
}
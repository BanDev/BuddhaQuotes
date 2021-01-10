/**

Buddha Quotes
Copyright (C) 2021  BanDev

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

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
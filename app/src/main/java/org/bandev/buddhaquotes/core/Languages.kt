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

import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import androidx.preference.PreferenceManager
import java.util.*

/**
 * The Languages class manages the languages that Buddha Quotes supports. The language in ISO 639
 * format is stored in shared preferences with the key "app_language"
 *
 * @author jack.txt & Fennec_exe
 * @since v1.5.0
 * @updated 16/01/2021
 */

@Suppress("DEPRECATION")
class Languages(base: Context?) : ContextWrapper(base) {
    companion object {
        fun wrap(context: Context): ContextWrapper {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            val language = sharedPrefs.getString("app_language", "en").toString()
            val config = context.resources.configuration
            val sysLocale: Locale?
            sysLocale = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                getSystemLocale(config)
            } else {
                getSystemLocaleLegacy(config)
            }
            if (language != "" && sysLocale.language != language) {
                val locale = Locale(language)
                Locale.setDefault(locale)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setSystemLocale(config, locale)
                } else {
                    setSystemLocaleLegacy(config, locale)
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.createConfigurationContext(config)
            } else {
                context.resources.updateConfiguration(config, context.resources.displayMetrics)
            }
            return Languages(context)
        }

        private fun getSystemLocaleLegacy(config: Configuration): Locale {
            return config.locale
        }

        @TargetApi(Build.VERSION_CODES.N)
        fun getSystemLocale(config: Configuration): Locale {
            return config.locales[0]
        }

        private fun setSystemLocaleLegacy(config: Configuration, locale: Locale?) {
            config.locale = locale
        }

        @TargetApi(Build.VERSION_CODES.N)
        fun setSystemLocale(config: Configuration, locale: Locale?) {
            config.setLocale(locale)
        }
    }

    fun setLanguage() {
        val context: Context =
            wrap(this)
        resources.updateConfiguration(
            context.resources.configuration,
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
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

package org.bandev.buddhaquotes.widgets

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.core.Accent.setAccentColour
import org.bandev.buddhaquotes.core.Insets.applyInsets
import org.bandev.buddhaquotes.databinding.WidgetConfigureBinding

/**
 * The configuration screen for the [MainWidget] AppWidget.
 */
class MainWidgetConfigureActivity : LocalizationActivity() {
    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var binding: WidgetConfigureBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_CANCELED)

        window.apply {
            statusBarColor = Color.TRANSPARENT
            setNavigationBarColourDefault()
            setDarkStatusIcons()
            setDecorFitsSystemWindows(this, false)
        }
        setAccentColour()

        binding = WidgetConfigureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.apply {
            setSupportActionBar(this)
            applyInsets(InsetType.STATUS_BARS)
            setNavigationOnClickListener { onBackPressed() }
        }

        binding.saveWidget.apply {
            backgroundTintList = ColorStateList.valueOf(resolveColorAttr(R.attr.colorAccent))
            applyInsets(InsetType.NAVIGATION_BARS)
            setOnClickListener { view ->
                Feedback.virtualKey(view)

                saveTitlePref(context, appWidgetId, 1)

                AppWidgetManager.getInstance(context).also { updateAppWidget(context, it, appWidgetId) }

                Intent().apply { putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId) }.also {
                    setResult(RESULT_OK, it)
                    finish()
                }
            }
        }

        intent.extras?.run {
            appWidgetId = this.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }
    }
}

private const val PREFS_NAME = "org.bandev.buddhaquotes.MainWidgetWidget"
private const val PREF_PREFIX_KEY = "appwidget_"

fun saveTitlePref(context: Context, appWidgetId: Int, option: Int) {
    context.getSharedPreferences(PREFS_NAME, 0).edit().run {
        putInt(PREF_PREFIX_KEY + appWidgetId, option)
        apply()
    }
}

fun loadTitlePref(context: Context, appWidgetId: Int): String {
    context.getSharedPreferences(PREFS_NAME, 0).run {
        getString(PREF_PREFIX_KEY + appWidgetId, null).run {
            return this ?: context.getString(R.string.appwidget_text)
        }
    }
}

fun deleteTitlePref(context: Context, appWidgetId: Int) {
    context.getSharedPreferences(PREFS_NAME, 0).edit().run {
        remove(PREF_PREFIX_KEY + appWidgetId)
        apply()
    }
}
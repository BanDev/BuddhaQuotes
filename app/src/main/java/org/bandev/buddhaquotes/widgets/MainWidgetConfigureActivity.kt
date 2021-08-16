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
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import androidx.lifecycle.ViewModelProvider
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.architecture.ViewModel
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.core.Accent.setAccentColour
import org.bandev.buddhaquotes.core.Insets.applyInsets
import org.bandev.buddhaquotes.databinding.WidgetConfigureBinding
import org.bandev.buddhaquotes.items.Quote

/**
 * The configuration screen for the [MainWidget] AppWidget.
 */
class MainWidgetConfigureActivity : LocalizationActivity() {
    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var binding: WidgetConfigureBinding
    private lateinit var model: ViewModel
    private lateinit var quote: Quote
    private var widgetTheme: WidgetTheme = WidgetTheme.LIGHT
    private var widgetTranslucency = WidgetTranslucency.TRANSPARENT

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

        model = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application)
            .create(ViewModel::class.java)

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

                context.savePref(appWidgetId, widgetTheme, widgetTranslucency)

                AppWidgetManager.getInstance(context).also { updateAppWidget(context, it, appWidgetId) }

                Intent().apply { putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId) }.also {
                    setResult(RESULT_OK, it)
                    finish()
                }
            }
        }

        model.Quotes().getRandom { binding.widgetLayout.widgetText.text = getString(it.resource) }

        intent.extras?.run {
            appWidgetId = getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        binding.themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_light -> widgetTheme = WidgetTheme.LIGHT.also { updateWidgetTheme(theme = it) }
                R.id.radio_dark -> widgetTheme = WidgetTheme.DARK.also { updateWidgetTheme(theme = it) }
                R.id.radio_follow_app -> widgetTheme = WidgetTheme.APP.also { updateWidgetTheme(theme = it) }
            }
        }

        binding.transparencyRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_transparent -> widgetTranslucency = WidgetTranslucency.TRANSPARENT.also { updateWidgetTheme(translucency = it) }
                R.id.radio_opaque -> widgetTranslucency = WidgetTranslucency.OPQAUE.also { updateWidgetTheme(translucency = it) }
            }
        }
    }

    private fun updateWidgetTheme(theme: WidgetTheme = widgetTheme, translucency: WidgetTranslucency = widgetTranslucency) {
        val appDark = inDarkMode(this)
        var layoutColor = Color.TRANSPARENT
        var textColor = Color.WHITE
        when {
            theme == WidgetTheme.LIGHT && translucency == WidgetTranslucency.TRANSPARENT -> {
                layoutColor = Color.TRANSPARENT
                textColor = Color.WHITE
            }
            theme == WidgetTheme.LIGHT && translucency == WidgetTranslucency.OPQAUE -> {
                layoutColor = Color.WHITE
                textColor = Color.BLACK
            }
            theme == WidgetTheme.DARK && translucency == WidgetTranslucency.TRANSPARENT -> {
                layoutColor = getColor(this, R.color.translucent_black)
                textColor = Color.WHITE
            }
            theme == WidgetTheme.DARK && translucency == WidgetTranslucency.OPQAUE -> {
                layoutColor = Color.BLACK
                textColor = Color.WHITE
            }
            theme == WidgetTheme.APP && translucency == WidgetTranslucency.TRANSPARENT -> {
                layoutColor = if (appDark) getColor(this, R.color.translucent_black) else Color.TRANSPARENT
                textColor = Color.WHITE
            }
            theme == WidgetTheme.APP && translucency == WidgetTranslucency.OPQAUE -> {
                layoutColor = if (appDark) Color.BLACK else Color.WHITE
                textColor = if (appDark) Color.WHITE else Color.BLACK
            }
        }
        with(binding.widgetLayout) {
            root.background = ShapeDrawable(
                RoundRectShape(
                    floatArrayOf(40f, 40f, 40f, 40f, 40f, 40f, 40f, 40f),
                    null,
                    null
                )
            ).apply { paint.color = layoutColor }
            widgetTitle.apply {
                setTextColor(textColor)
                compoundDrawableTintList = ColorStateList.valueOf(textColor)
            }
            widgetText.setTextColor(textColor)
        }
    }
}

private const val PREFS_NAME = "org.bandev.buddhaquotes.MainWidgetWidget"
private const val PREF_PREFIX_KEY = "appwidget_"

fun Context.savePref(appWidgetId: Int, theme: WidgetTheme, translucency: WidgetTranslucency) {
    this.getSharedPreferences(PREFS_NAME, 0).edit().run {
        putInt(PREF_PREFIX_KEY + appWidgetId, WidgetDataItem(theme, translucency).toPrefsInt()).apply()
    }
}

fun Context.loadWidgetPref(appWidgetId: Int): WidgetDataItem {
    this.getSharedPreferences(PREFS_NAME, 0).run {
        getInt(PREF_PREFIX_KEY + appWidgetId, 0).let {
            return it.toWidgetData()
        }
    }
}

fun Context.deleteWidgetPref(appWidgetId: Int) {
    this.getSharedPreferences(PREFS_NAME, 0).edit().run {
        remove(PREF_PREFIX_KEY + appWidgetId).apply()
    }
}

private fun WidgetDataItem.toPrefsInt(): Int {
    return when {
        this.theme == WidgetTheme.LIGHT && this.translucency == WidgetTranslucency.OPQAUE -> 1
        this.theme == WidgetTheme.DARK && this.translucency == WidgetTranslucency.TRANSPARENT -> 2
        this.theme == WidgetTheme.DARK && this.translucency == WidgetTranslucency.OPQAUE -> 3
        this.theme == WidgetTheme.APP && this.translucency == WidgetTranslucency.TRANSPARENT -> 4
        this.theme == WidgetTheme.APP && this.translucency == WidgetTranslucency.OPQAUE -> 5
        else -> 0
    }
}

private fun Int.toWidgetData(): WidgetDataItem {
    return when(this) {
        1 -> WidgetDataItem(WidgetTheme.LIGHT, WidgetTranslucency.OPQAUE)
        2 -> WidgetDataItem(WidgetTheme.DARK, WidgetTranslucency.TRANSPARENT)
        3 -> WidgetDataItem(WidgetTheme.DARK, WidgetTranslucency.OPQAUE)
        4 -> WidgetDataItem(WidgetTheme.APP, WidgetTranslucency.TRANSPARENT)
        5 -> WidgetDataItem(WidgetTheme.APP, WidgetTranslucency.OPQAUE)
        else -> WidgetDataItem(WidgetTheme.LIGHT, WidgetTranslucency.TRANSPARENT)
    }
}

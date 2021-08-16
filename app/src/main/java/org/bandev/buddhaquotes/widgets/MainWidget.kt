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

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.RemoteViews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.architecture.Db
import org.bandev.buddhaquotes.architecture.QuoteMapper
import org.bandev.buddhaquotes.core.Images.heart
import org.bandev.buddhaquotes.items.Quote

/** The widget **/
class MainWidget : AppWidgetProvider() {

    var newQuote: String = "action.NEW_QUOTE"

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { appWidgetId ->
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action.equals(newQuote)) {
            getRandom(context) {
                RemoteViews(context.packageName, R.layout.widget_light).apply {
                    setTextViewText(R.id.widget_text, context.getString(it.resource))
                    setTextViewCompoundDrawables(R.id.widget_text, 0, 0, 0, heart(it.liked))
                }.also { views ->
                    AppWidgetManager.getInstance(context).also { appWidgetManager ->
                        appWidgetManager.getAppWidgetIds(
                            ComponentName(
                                context,
                                MainWidget::class.java
                            )
                        ).also { appWidgetIds ->
                            appWidgetManager.updateAppWidget(appWidgetIds, views)
                        }
                    }
                }
            }
        }
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        appWidgetIds?.forEach { appWidgetId ->
            context?.deleteWidgetPref(appWidgetId)
        }
    }
}

fun getRandom(context: Context, after: (quote: Quote) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        Db.getInstance(context)?.quote()?.let { QuoteMapper.convert(it.get((1..237).random())).run(after) }
    }
}

fun getPenIntent(context: Context, action: String): PendingIntent {
    val intent = Intent(context, MainWidget::class.java).apply { this.action = action }
    return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
) {
    getRandom(context) { quote ->
        context.loadWidgetPref(appWidgetId).also {
            RemoteViews(context.packageName, R.layout.widget_light).apply {
                setTextViewText(R.id.widget_text, context.getString(quote.resource))
                setTextViewCompoundDrawables(R.id.widget_text, 0, 0, 0, heart(quote.liked))
                setOnClickPendingIntent(R.id.layout, getPenIntent(context, MainWidget().newQuote))
                setTextColor(R.id.widget_text, if (it.theme == WidgetTheme.LIGHT && it.translucency == WidgetTranslucency.OPQAUE) Color.BLACK else Color.WHITE)
            }.let { appWidgetManager.updateAppWidget(appWidgetId, it) }
        }
    }
}

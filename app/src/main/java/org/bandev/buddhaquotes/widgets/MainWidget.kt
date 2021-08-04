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
import android.widget.RemoteViews
import org.bandev.buddhaquotes.R

/** The widget **/
class MainWidget : AppWidgetProvider() {

    var widgetButton: String = "org.bandev.buddhaquotes.WIDGET_BUTTON"
    private var widgetLike: String = "org.bandev.buddhaquotes.WIDGET_LIKE"
    private var quoteCurrent: String = "Test"
    private var x = 0

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action.equals(widgetButton, ignoreCase = true)) {
            val views = RemoteViews(context.packageName, R.layout.widget)
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(
                ComponentName(
                    context,
                    MainWidget::class.java
                )
            )
            x++
            quoteCurrent = "test"
            views.setTextViewText(R.id.appwidget_text, quoteCurrent)
            appWidgetManager.updateAppWidget(appWidgetIds, views)
        } else if (intent.action.equals(widgetLike, ignoreCase = true)) {
            val views = RemoteViews(context.packageName, R.layout.widget)
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(
                ComponentName(
                    context,
                    MainWidget::class.java
                )
            )
            x++
            views.setImageViewResource(R.id.like, R.drawable.ic_heart_red)
            appWidgetManager.updateAppWidget(appWidgetIds, views)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
// Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.widget)
    with(views) {
        setImageViewResource(R.id.refresh, R.drawable.ic_refresh_black)
        setOnClickPendingIntent(R.id.refresh, getPenIntent(context))
        setImageViewResource(R.id.logo, R.drawable.ic_buddha)
        setTextViewText(R.id.appwidget_text, "test")
    }

    getPenIntent(context)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

fun getPenIntent(context: Context): PendingIntent {
    val intent = Intent(context, MainWidget::class.java)
    intent.action = MainWidget().widgetButton
    return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
}

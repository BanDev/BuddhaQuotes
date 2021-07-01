package org.bandev.buddhaquotes.architecture

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat.getColor
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.Icons
import org.bandev.buddhaquotes.items.ListIcon

class ListIconManager(application: Application) {

    private val context: Context = application.applicationContext
    private val icons: Icons = Icons(context)
    private val listIcons: MutableList<ListIcon> = mutableListOf()

    init {
        listIcons.add(ListIcon(0, icons.heartOutline(), getColor(context, R.color.colorAccent)))
        listIcons.add(ListIcon(1, icons.list(), getColor(context, R.color.blueAccent)))
        listIcons.add(ListIcon(2, icons.list(), getColor(context, R.color.blueAccent)))
        listIcons.add(ListIcon(3, icons.list(), getColor(context, R.color.blueAccent)))
    }

    fun associate(id: Int) = listIcons[id]

}
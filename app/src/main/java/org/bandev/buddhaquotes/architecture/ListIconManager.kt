package org.bandev.buddhaquotes.architecture

import android.app.Application
import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat.getColor
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.Icons
import org.bandev.buddhaquotes.databinding.ListIconBinding
import org.bandev.buddhaquotes.items.ListIcon

class ListIconManager(application: Application) {

    private val context: Context = application.applicationContext
    private val icons: Icons = Icons(context)
    private val listIcons: MutableList<ListIcon> = mutableListOf()

    init {
        listIcons.add(ListIcon(0, icons.heartOutline(), getColor(context, R.color.colorAccent)))
        listIcons.add(ListIcon(1, icons.list(), getColor(context, R.color.blueAccent)))
        listIcons.add(ListIcon(2, icons.palette(), getColor(context, R.color.greenAccent)))
        listIcons.add(ListIcon(3, icons.memory(), getColor(context, R.color.yellowAccent)))

    }

    fun associate(id: Int): ListIcon = listIcons[id]
    
    fun getAll(): List<ListIcon> = listIcons

    fun draw(holder: ImageView, icon: ListIcon) {
        holder.setImageDrawable(icon.drawable)
        holder.background = icons.circle()
        holder.backgroundTintList = ColorStateList.valueOf(icon.colour)
    }

}
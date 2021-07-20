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

package org.bandev.buddhaquotes.architecture

import android.app.Application
import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.content.ContextCompat.getColor
import coil.load
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.items.ListIcon

class ListIconManager(application: Application) {

    private val context: Context = application.applicationContext
    private val listIcons: MutableList<ListIcon> = mutableListOf()

    init {
        listIcons.add(ListIcon(0, R.drawable.ic_tune, getColor(context, R.color.colorAccent)))
        listIcons.add(ListIcon(1, R.drawable.ic_down_arrow, getColor(context, R.color.blueAccent)))
        listIcons.add(ListIcon(2, R.drawable.ic_arrow_back, getColor(context, R.color.greenAccent)))
        listIcons.add(ListIcon(3, R.drawable.ic_menu, getColor(context, R.color.yellowAccent)))
    }

    fun associate(id: Int): ListIcon = listIcons[id]

    fun getAll(): List<ListIcon> = listIcons

    fun draw(holder: ImageView, icon: ListIcon) {
        with(holder) {
            load(icon.drawable)
            backgroundTintList = ColorStateList.valueOf(icon.colour)
        }
    }

}
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

package org.bandev.buddhaquotes.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.bandev.buddhaquotes.databinding.ListIconBinding
import org.bandev.buddhaquotes.items.ListIcon

/**
 * Scroll through list icons
 */

class ListIconAdapter(

    private val list: List<ListIcon>,
    private val listener: Listener,

    ) : RecyclerView.Adapter<ListIconAdapter.ViewHolder>() {

    class ViewHolder(binding: ListIconBinding) : RecyclerView.ViewHolder(binding.root) {
        val listIcon: ImageView = binding.listIcon
        val selected: ImageView = binding.selected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListIconBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder.listIcon) {
            if (item.selected) holder.selected.visibility = View.VISIBLE
            setImageResource(item.drawable)
            backgroundTintList = ColorStateList.valueOf(item.colour)
            setOnClickListener {
                Toast.makeText(holder.listIcon.context, position.toString(), Toast.LENGTH_SHORT).show()
                holder.selected.visibility = View.VISIBLE
                listener.select(item)
                for (icon in list) {
                    icon.selected = false
                    notifyItemChanged(icon.id)
                }
                list[list.indexOf(item)].selected = true
                notifyItemRangeChanged(0, list.lastIndex)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    interface Listener {
        fun select(icon: ListIcon)
    }
}


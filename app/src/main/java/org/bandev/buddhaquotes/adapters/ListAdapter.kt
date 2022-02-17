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

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import org.bandev.buddhaquotes.databinding.ListAdapterItemBinding
import org.bandev.buddhaquotes.items.List

/**
 * Recycler adapter for QouteList class
 */

class ListAdapter(
    private val listener: Listener

) : androidx.recyclerview.widget.ListAdapter<List,ListAdapter.ViewHolder>(DiffUtil()) {

    class ViewHolder(binding: ListAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: List?, listener: Listener) {
            if (item != null) {
                title.text = if (item.id != 0) item.title else title.context.getString(org.bandev.buddhaquotes.R.string.favourites)
            }
            if (item != null) {
                iconIcon.setImageResource(item.icon.drawable)
            }
            if (item != null) {
                iconBack.backgroundTintList = android.content.res.ColorStateList.valueOf(item.icon.colour)
            }
            if (item != null) {
                summary.text = summary.context.getString(
                    if (item.count == 1) org.bandev.buddhaquotes.R.string.quote_count else org.bandev.buddhaquotes.R.string.quotes_count,
                    item.count
                )
            }
                root.setOnClickListener {
                    if (item != null) {
                        listener.select(item)
                    }
                }
                options.setOnClickListener {
                    org.bandev.buddhaquotes.core.Feedback.virtualKey(it)
                    if (item != null) {
                        listener.options(item)
                    }
                }

        }

        val title: TextView = binding.list
        val iconIcon: ImageView = binding.iconIcon
        val iconBack: ConstraintLayout = binding.iconBack
        val summary: TextView = binding.summary
        val options: ImageButton = binding.options
        val root: CardView = binding.root

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListAdapterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,listener)
    }

    interface Listener {
        fun select(list: List)
        fun options(list: List)
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<List>() {
        override fun areItemsTheSame(
            oldItem: List,
            newItem: List
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: List,
            newItem: List
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}

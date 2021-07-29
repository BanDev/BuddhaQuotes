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

import android.app.Application
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.architecture.ListMapper
import org.bandev.buddhaquotes.databinding.ListAdapterItemBinding
import org.bandev.buddhaquotes.items.List

/**
 * Recycler adapter for QouteList class
 */

class ListAdapter(

    private val list: MutableList<List>,
    private val listener: Listener

) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    class ViewHolder(binding: ListAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.list
        val iconIcon: ImageView = binding.iconIcon
        val iconBack: ConstraintLayout = binding.iconBack
        val summary: TextView = binding.summary
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
        val item = list[position]

        with(holder) {
            title.text =
                if (item.id != 0) item.title else holder.root.context.getString(R.string.favourites)
            summary.text = root.context.getString(
                if (item.count == 1) R.string.quote else R.string.quotes,
                item.count
            )
            root.setOnClickListener { listener.select(item) }
        }

        holder.iconIcon.setImageResource(item.icon.drawable)
        holder.iconBack.backgroundTintList = ColorStateList.valueOf(item.icon.colour)
    }

    override fun getItemCount(): Int = list.size

    interface Listener {
        fun select(list: List)
    }
}

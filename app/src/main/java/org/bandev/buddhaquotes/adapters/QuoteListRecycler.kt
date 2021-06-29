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
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.bandev.buddhaquotes.databinding.CardListsFragmentBinding
import org.bandev.buddhaquotes.items.QuoteList

/**
 * Recycler adapter for QouteList class
 */

class QuoteListRecycler(

    private val list: List<QuoteList>,
    private val listener: Listener,

    ) : RecyclerView.Adapter<QuoteListRecycler.ViewHolder>() {

    class ViewHolder(binding: CardListsFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.titleText
        val summary: TextView = binding.summaryText
        val binIcon: ImageView = binding.binIconButton
        val root: CardView = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardListsFragmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.title.text = item.title
        holder.summary.text = item.title

        if (item.system) {
            holder.binIcon.visibility = View.GONE
        }
        holder.root.setOnClickListener { listener.select(item) }
    }

    override fun getItemCount(): Int = list.size

    interface Listener {
        fun select(list: QuoteList)
    }
}

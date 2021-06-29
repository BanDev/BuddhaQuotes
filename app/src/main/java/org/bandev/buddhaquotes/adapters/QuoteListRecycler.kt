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
import androidx.recyclerview.widget.RecyclerView
import org.bandev.buddhaquotes.databinding.CardListsFragmentBinding
import org.bandev.buddhaquotes.fragments.ListsFragment
import org.bandev.buddhaquotes.items.ListItem
import org.bandev.buddhaquotes.items.QuoteList

/**
 * Recycler adapter for QouteList class
 */

class QuoteListRecycler(

    private val list: List<QuoteList>,
    private val listener: ListsFragment,

    ) : RecyclerView.Adapter<QuoteListRecycler.ScrollingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollingViewHolder {
        val binding = CardListsFragmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ScrollingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ScrollingViewHolder, position: Int) {
        val item = list[position]

        holder.binding.titleText.text = item.title
        holder.binding.summaryText.text = item.title

        if (item.system) {
            holder.binding.binIconButton.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = list.size

    inner class ScrollingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener,
        View.OnLongClickListener {
        val binding: CardListsFragmentBinding = CardListsFragmentBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onCardClick(position)
                }
            }

            binding.binIconButton.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onBinClick(position, binding.titleText.text.toString())
                }
            }
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLikeClick(position, binding.titleText.toString())
            }
        }

        override fun onLongClick(view: View): Boolean {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLikeClick(position, binding.titleText.toString())
            }
            // Return true to indicate the click was handled
            return true
        }
    }
}

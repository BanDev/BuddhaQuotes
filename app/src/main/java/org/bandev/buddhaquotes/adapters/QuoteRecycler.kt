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
import org.bandev.buddhaquotes.items.QuoteItem
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.databinding.CardQuoteFragmentBinding

class QuoteRecycler(

    private val scrollingList: List<QuoteItem>,
    private val listener: OnItemClickFinder,

    ) : RecyclerView.Adapter<QuoteRecycler.ScrollingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollingViewHolder {
        val binding = CardQuoteFragmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ScrollingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ScrollingViewHolder, position: Int) {
        val currentItem = scrollingList[position]

        holder.binding.quote.text = currentItem.quote
        holder.binding.like.setImageResource(currentItem.resource)

        if (currentItem.no_like) {
            holder.binding.like.setImageResource(R.drawable.heart_full_red)
        }
    }

    override fun getItemCount(): Int = scrollingList.size

    inner class ScrollingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val binding: CardQuoteFragmentBinding = CardQuoteFragmentBinding.bind(itemView)

        init {
            binding.like.setOnClickListener(this)
            binding.more.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onCardClick(position)
                }
            }

            binding.bin.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onBinClick(position, binding.quote.text.toString())
                }
            }
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLikeClick(position, binding.quote.text.toString())
            }
        }

        override fun onLongClick(view: View): Boolean {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLikeClick(position, binding.quote.text.toString())
            }
            // Return true to indicate the click was handled
            return true
        }
    }

    interface OnItemClickFinder {
        fun onLikeClick(position: Int, text: String)

        fun onCardClick(position: Int)

        fun onBinClick(position: Int, text: String)
    }
}


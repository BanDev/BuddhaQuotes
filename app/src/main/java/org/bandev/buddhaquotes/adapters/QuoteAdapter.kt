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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.core.Feedback
import org.bandev.buddhaquotes.core.find
import org.bandev.buddhaquotes.databinding.QuoteAdapterItemBinding
import org.bandev.buddhaquotes.items.Quote

/**
 * Scroll a list of quotes. Used in
 * ListActivity.
 *
 * @date 28/7/21
 */

class QuoteAdapter(

    private val quotes: MutableList<Quote>,
    private val listener: Listener,
    private val id: Int

) : RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {

    class ViewHolder(binding: QuoteAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val quote: TextView = binding.quote
        val like: ImageView = binding.like
        val bin: ImageView = binding.bin
        val root: CardView = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = QuoteAdapterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = quotes[position]

        holder.quote.text = holder.quote.context.getString(item.resource)

        if (item.liked) holder.like.setImageResource(R.drawable.ic_heart_red)

        holder.like.setOnClickListener {
            Feedback.virtualKey(it)
            if (id == 0) {
                notifyItemRemoved(quotes.find(item))
                quotes.remove(item)
                listener.onQuoteRemoved(item)
            } else {
                if (item.liked) {
                    listener.onQuoteUnliked(item)
                    holder.like.setImageResource(R.drawable.ic_heart_outline)
                } else {
                    listener.onQuoteLiked(item)
                    holder.like.setImageResource(R.drawable.ic_heart_red)
                }
                item.liked = !item.liked
            }
        }

        holder.bin.setOnClickListener {
            Feedback.virtualKey(it)
            notifyItemRemoved(quotes.find(item))
            quotes.remove(item)
            listener.onQuoteRemoved(item)
        }
    }

    override fun getItemCount(): Int = quotes.size

    interface Listener {
        fun onQuoteLiked(quote: Quote)
        fun onQuoteUnliked(quote: Quote)
        fun onQuoteRemoved(quote: Quote)
    }

}


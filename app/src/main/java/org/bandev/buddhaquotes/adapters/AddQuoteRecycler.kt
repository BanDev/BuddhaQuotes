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
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.bandev.buddhaquotes.databinding.AddQuoteItemBinding
import org.bandev.buddhaquotes.items.AddQuoteItem
import java.util.*
import kotlin.collections.ArrayList

/**
 * Recycler adapter for the [RecyclerView] used in the [AddToList]
 * activity. This adapter is filterable, updated for view binding
 * and supports item clicks.
 *
 * @author Jack Devey
 * @param addquoteList [MutableList<AddQuoteItem>]
 * @param listener [ClickListener]
 */
class AddQuoteRecycler(

    private val addquoteList: MutableList<AddQuoteItem>,
    private val listener: ClickListener

) : RecyclerView.Adapter<AddQuoteRecycler.ViewHolder>(), Filterable {

    private val addquoteListFull = ArrayList(addquoteList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AddQuoteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = addquoteList[position]
        holder.quote.text = currentItem.quote
    }

    override fun getItemCount(): Int = addquoteList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val binding: AddQuoteItemBinding = AddQuoteItemBinding.bind(itemView)
        val quote: TextView = binding.quote

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(quote.text.toString())
        }
    }

    interface ClickListener {
        fun onClick(quote: String)
    }

    override fun getFilter(): Filter = exampleFilter

    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<AddQuoteItem> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(addquoteListFull)
            } else {
                val filterPattern =
                    constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
                for (item in addquoteListFull) {
                    if (item.quote.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            addquoteList.clear()
            addquoteList.addAll(results.values as Collection<AddQuoteItem>)
            notifyDataSetChanged()
        }
    }
}
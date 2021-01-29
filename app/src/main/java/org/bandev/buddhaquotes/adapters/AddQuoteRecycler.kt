package org.bandev.buddhaquotes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.bandev.buddhaquotes.databinding.AddQuoteItemBinding
import org.bandev.buddhaquotes.items.AddQuoteItem

class AddQuoteRecycler(

    private val addquoteList: List<AddQuoteItem>,
    private val listener: ClickListener

) : RecyclerView.Adapter<AddQuoteRecycler.ViewHolder>() {

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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
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
}
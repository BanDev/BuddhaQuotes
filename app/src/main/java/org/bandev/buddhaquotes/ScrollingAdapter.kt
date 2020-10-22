package org.bandev.buddhaquotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recylcer_card.view.*
import java.util.regex.Pattern.quote

class ScrollingAdapter(

    private val scrollingList: List<ExampleItem>,
    private val listener: OnItemClickFinder,

    ) : RecyclerView.Adapter<ScrollingAdapter.ScrollingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollingViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recylcer_card,
            parent, false)

        return ScrollingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScrollingViewHolder, position: Int) {
        val currentItem = scrollingList[position]

        holder.text_quote.text = currentItem.quote
        holder.icon_like.setImageResource(currentItem.resource)
    }

    override fun getItemCount() = scrollingList.size

    inner class ScrollingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
        val text_quote: TextView = itemView.quote
        val icon_like: ImageView = itemView.like
        val share: ImageView = itemView.share
        val bin: ImageView = itemView.bin

        init {
            icon_like.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            share.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onShareClick(position)
                }
            }

            bin.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onBinClick(position)
                }
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLikeClick(position)
            }
        }

        override fun onLongClick(view: View): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLikeClick(position)
            }
            // Return true to indicate the click was handled
            return true
        }
    }

    interface OnItemClickFinder {
        fun onLikeClick(position: Int)

        fun onShareClick(position: Int)

        fun onBinClick(position: Int)
    }

}


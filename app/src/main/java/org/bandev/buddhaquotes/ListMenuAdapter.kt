package org.bandev.buddhaquotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_card.view.bin
import kotlinx.android.synthetic.main.recycler_card_2.view.*

class ListMenuAdapter(

    private val scrollingList: List<ListMenuItem>,
    private val listener: Favourites,

    ) : RecyclerView.Adapter<ListMenuAdapter.ScrollingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollingViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_card_2,
            parent, false
        )

        return ScrollingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScrollingViewHolder, position: Int) {
        val currentItem = scrollingList[position]

        holder.textTitle.text = currentItem.title
        holder.textSummary.text = currentItem.summary

        if (currentItem.special) {
            holder.iconBin.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = scrollingList.size

    inner class ScrollingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val textTitle: TextView = itemView.title
        val textSummary: TextView = itemView.summary
        val iconBin: ImageView = itemView.bin

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onShareClick(position)
                }
            }

            itemView.bin.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onBinClick(position, textTitle.text.toString())
                }
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLikeClick(position, textTitle.toString())
            }
        }

        override fun onLongClick(view: View): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLikeClick(position, textTitle.toString())
            }
            // Return true to indicate the click was handled
            return true
        }

    }

    interface OnItemClickFinder {
        fun onCardClick(position: Int)
    }

}


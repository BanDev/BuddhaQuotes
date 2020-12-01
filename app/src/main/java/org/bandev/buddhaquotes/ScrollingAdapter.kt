package org.bandev.buddhaquotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.bandev.buddhaquotes.databinding.LayoutRecyclerCardBinding

class ScrollingAdapter(

    private val scrollingList: List<ExampleItem>,
    private val listener: OnItemClickFinder,

    ) : RecyclerView.Adapter<ScrollingAdapter.ScrollingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollingViewHolder {
        val binding = LayoutRecyclerCardBinding.inflate(
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
        val binding: LayoutRecyclerCardBinding = LayoutRecyclerCardBinding.bind(itemView)

        init {
            binding.like.setOnClickListener(this)
            binding.share.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onShareClick(position)
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

        fun onShareClick(position: Int)

        fun onBinClick(position: Int, text: String)
    }
}


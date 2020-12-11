package org.bandev.buddhaquotes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.bandev.buddhaquotes.items.ListItem
import org.bandev.buddhaquotes.databinding.CardListsFragmentBinding
import org.bandev.buddhaquotes.fragments.ListsFragment

class ListRecycler(

    private val scrollingLists: List<ListItem>,
    private val listener: ListsFragment,

    ) : RecyclerView.Adapter<ListRecycler.ScrollingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollingViewHolder {
        val binding = CardListsFragmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ScrollingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ScrollingViewHolder, position: Int) {
        val currentItem = scrollingLists[position]

        holder.binding.title.text = currentItem.title
        holder.binding.summary.text = currentItem.summary

        if (currentItem.special) {
            holder.binding.bin.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = scrollingLists.size

    inner class ScrollingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val binding: CardListsFragmentBinding = CardListsFragmentBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onCardClick(position)
                }
            }

            binding.bin.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onBinClick(position, binding.title.text.toString())
                }
            }
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLikeClick(position, binding.title.toString())
            }
        }

        override fun onLongClick(view: View): Boolean {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLikeClick(position, binding.title.toString())
            }
            // Return true to indicate the click was handled
            return true
        }

    }

}


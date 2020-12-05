package org.bandev.buddhaquotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.bandev.buddhaquotes.databinding.LayoutRecyclerCard2Binding
import org.bandev.buddhaquotes.ui.dashboard.DashboardFragment

class ListMenuAdapter(

    private val scrollingList: List<ListMenuItem>,
    private val listener: DashboardFragment,

    ) : RecyclerView.Adapter<ListMenuAdapter.ScrollingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollingViewHolder {
        val binding = LayoutRecyclerCard2Binding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ScrollingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ScrollingViewHolder, position: Int) {
        val currentItem = scrollingList[position]

        holder.binding.title.text = currentItem.title
        holder.binding.summary.text = currentItem.summary

        if (currentItem.special) {
            holder.binding.bin.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = scrollingList.size

    inner class ScrollingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val binding: LayoutRecyclerCard2Binding = LayoutRecyclerCard2Binding.bind(itemView)

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onShareClick(position)
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


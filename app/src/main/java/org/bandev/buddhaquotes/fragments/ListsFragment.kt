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

package org.bandev.buddhaquotes.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxkeppeler.sheets.core.IconButton
import kotlinx.coroutines.runBlocking
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.activities.ListActivity
import org.bandev.buddhaquotes.adapters.ListAdapter
import org.bandev.buddhaquotes.architecture.ViewModel
import org.bandev.buddhaquotes.bus.Bus
import org.bandev.buddhaquotes.bus.Message
import org.bandev.buddhaquotes.bus.MessageType
import org.bandev.buddhaquotes.core.find
import org.bandev.buddhaquotes.custom.ListOptionsSheet
import org.bandev.buddhaquotes.databinding.FragmentListsBinding
import org.bandev.buddhaquotes.items.List
import kotlin.concurrent.thread

/**
 * Shows a list of lists to the user
 */

class ListsFragment : Fragment(), ListAdapter.Listener {

    private lateinit var binding: FragmentListsBinding
    private lateinit var model: ViewModel
    private lateinit var lists: MutableList<List>
    private lateinit var bus: Bus

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Attach to viewmodel
        model = ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(ViewModel::class.java)

        // Setup the RecyclerView
        model.Lists().getAll {
            lists = it
            binding.listsRecycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ListAdapter(lists, this@ListsFragment)
                setHasFixedSize(false)
            }
        }

        bus = Bus(this, "ListsFragment")

        bus.attachRouter {
            if (this::lists.isInitialized) {
                when(it) {
                    MessageType.LIKE_UPDATE -> this::onLikeUpdate
                    MessageType.NEW_LIST -> this::onNewList
                    MessageType.UPDATE_LIST -> this::onUpdateList
                }
            } else null
        }

        bus.listen()
    }

    override fun select(list: List) {
        startActivity(Intent(context, ListActivity::class.java).putExtra("id", list.id))
    }

    override fun options(list: List) {
        ListOptionsSheet().show(requireContext(), requireActivity().application) {
            attachVariables(model, list)
            onListIconSelected { icon ->
                model.Lists().updateIcon(list.id, icon) {
                    bus.broadcast(Message(MessageType.UPDATE_LIST, it))
                }
            }
            onListRemoved { list ->
                model.Lists().delete(list.id)
                val position = lists.indexOf(list)
                lists.remove(list)
                binding.listsRecycler.adapter?.notifyItemRemoved(position)
            }
            onListRenamed { name ->
                model.Lists().rename(list.id, name)
                val position = lists.indexOf(list)
                lists[position].title = name
                binding.listsRecycler.adapter?.notifyItemChanged(position)
            }
        }
    }

    private fun onLikeUpdate(message: Message<*>) {
        val change = message.data as Int
        lists[0].count = lists[0].count + change
        binding.listsRecycler.adapter?.notifyItemChanged(0)
    }

    private fun onUpdateList(message: Message<*>) {
        val data = message.data as List
        var position = -1
        lists.forEachIndexed { index, (id) ->
            if (id == data.id) position = index
        }
        lists[position] = data
        binding.listsRecycler.adapter?.notifyItemChanged(position)
    }

    private fun onNewList(message: Message<*>) {
        val data = message.data as List
        lists.add(data)
        binding.listsRecycler.adapter?.notifyItemInserted(lists.indexOf(data))
    }

    override fun onDestroy() {
        super.onDestroy()
        bus.mute()
    }

    companion object {
        fun newInstance(position: Int): ListsFragment {
            return ListsFragment().apply {
                arguments = Bundle().apply { putInt("position", position) }
            }
        }
    }
}

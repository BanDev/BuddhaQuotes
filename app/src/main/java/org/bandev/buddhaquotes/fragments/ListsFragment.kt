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
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.bandev.buddhaquotes.activities.ListActivity
import org.bandev.buddhaquotes.adapters.ListAdapter
import org.bandev.buddhaquotes.architecture.ViewModel
import org.bandev.buddhaquotes.core.MessageTypes
import org.bandev.buddhaquotes.databinding.FragmentListsBinding
import org.bandev.buddhaquotes.items.List
import uk.bandev.services.bus.Bus
import uk.bandev.services.bus.Message

/**
 * Shows a list of lists to the user
 */

class ListsFragment : Fragment(), ListAdapter.Listener, Bus.Listener {

    private lateinit var binding: FragmentListsBinding
    private lateinit var model: ViewModel
    private lateinit var list: MutableList<List>
    private lateinit var bus: Bus
    private var prevMessage: Message<*>? = null

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

        // Setup the recyclerview
        setupRecycler()

        bus = Bus(this, "ListsFragment")
        bus.listen()
    }

    /**
     * Get some quotes from the db and
     * show them in the recycler
     */

    private fun setupRecycler() {
        model.Lists().getAll {
            list = it
            binding.listsRecycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ListAdapter(list, this@ListsFragment)
                setHasFixedSize(false)
            }
        }
    }

    override fun select(list: List) {
        startActivity(Intent(context, ListActivity::class.java).putExtra("id", list.id))
    }

    override fun delete(list: List) {
        model.Lists().delete(list.id)
        var position = -1
        this.list.forEachIndexed { index, (id) ->
            if (id == list.id) position = index
        }
        binding.listsRecycler.adapter?.notifyItemRemoved(position)
        this.list.remove(list)
    }

    override fun onMessageReceived(message: Message<*>) {
        if (this::list.isInitialized) {
            prevMessage = if (prevMessage != null) {
                if (message == prevMessage) return
                else message
            } else message
            when (message.type) {
                MessageTypes.NEW_LIST -> {
                    val data = message.data as List
                    list.add(data)
                    binding.listsRecycler.adapter?.notifyItemInserted(list.indexOf(data))
                }
                MessageTypes.UPDATE_LIST -> {
                    val data = message.data as List
                    var position = -1
                    list.forEachIndexed { index, (id) ->
                        if (id == data.id) position = index
                    }
                    list[position] = data
                    binding.listsRecycler.adapter?.notifyItemChanged(position)
                }
                MessageTypes.LIKE_UPDATE -> {
                    val change = message.data as Int
                    list[0].count = list[0].count + change
                    binding.listsRecycler.adapter?.notifyItemChanged(0)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bus.mute()
    }

    companion object {
        fun newInstance(position: Int): ListsFragment {
            val instance = ListsFragment()
            val args = Bundle()
            args.putInt("position", position)
            instance.arguments = args
            return instance
        }
    }
}

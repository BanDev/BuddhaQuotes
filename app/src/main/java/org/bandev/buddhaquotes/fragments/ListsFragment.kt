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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import me.kosert.flowbus.EventsReceiver
import me.kosert.flowbus.bindLifecycle
import me.kosert.flowbus.subscribe
import org.bandev.buddhaquotes.activities.ListActivity
import org.bandev.buddhaquotes.adapters.QuoteListRecycler
import org.bandev.buddhaquotes.architecture.ListViewModel
import org.bandev.buddhaquotes.core.UpdateLists
import org.bandev.buddhaquotes.databinding.FragmentListsBinding
import org.bandev.buddhaquotes.items.QuoteList

/**
 * Shows a list of lists to the user
 */

class ListsFragment : Fragment(), QuoteListRecycler.Listener {

    private lateinit var binding: FragmentListsBinding
    private lateinit var model: ListViewModel
    private val receiver = EventsReceiver()

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
            .create(ListViewModel::class.java)

        // Setup the recyclerview
        setupRecycler()
    }

    /**
     * Get some quotes from the db and
     * show them in the recycler
     */

    private fun setupRecycler() {
        model.getAllNoQuotes {
            with(binding.listsRecycler) {
                layoutManager = LinearLayoutManager(context)
                adapter = QuoteListRecycler(it, this@ListsFragment, requireActivity().application)
                setHasFixedSize(false)
            }
        }
    }

    override fun select(list: QuoteList) {
        startActivity(Intent(context, ListActivity::class.java).putExtra("id", list.id))
    }

    override fun onStart() {
        super.onStart()
        receiver.bindLifecycle(this)
            .subscribe { _: UpdateLists ->
                setupRecycler()
            }
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

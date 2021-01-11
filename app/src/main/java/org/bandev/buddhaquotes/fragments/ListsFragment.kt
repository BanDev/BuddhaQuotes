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
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.activities.ScrollingActivity
import org.bandev.buddhaquotes.adapters.ListRecycler
import org.bandev.buddhaquotes.adapters.QuoteRecycler
import org.bandev.buddhaquotes.core.Fragments
import org.bandev.buddhaquotes.core.Lists
import org.bandev.buddhaquotes.databinding.FragmentListsBinding
import org.bandev.buddhaquotes.items.ListItem

/**
 * ListsFragment is the fragment that allows users to manage their lists.
 * It is the second item in the [FragmentAdapter] on MainActivity
 * @author jack.txt
 * @since 1.7.0
 * @updated 11/12/2020
 */

class ListsFragment : Fragment(), QuoteRecycler.OnItemClickFinder {

    private var _binding: FragmentListsBinding? = null
    private val binding get() = _binding!!
    private lateinit var masterListFinal: ArrayList<ListItem>
    private lateinit var masterlist: List<String>

    /**
     * Sets the correct view of the Fragment
     * @param inflater [LayoutInflater]
     * @param container [ViewGroup]
     * @param savedInstanceState [Bundle]
     * @return [View]
     */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called when view is full made
     * @param view [View]
     * @param savedInstanceState [Bundle]
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        masterlist = Lists().getMasterList(requireContext())

        masterListFinal = generateMasterList(masterlist.size, masterlist)

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = ListRecycler(masterListFinal, this@ListsFragment)
            setHasFixedSize(false)
        }
    }

    /**
     * Generates a list of lists with the ListItem custom data type
     */

    private fun generateMasterList(max: Int, listIn: List<String>): ArrayList<ListItem> {
        val list = ArrayList<ListItem>()
        var i = 0
        while (i != max) {
            var special = false
            val pref = requireContext().getSharedPreferences("List_system", 0)
            val array2 = pref.getString(listIn[i], "")!!.split("//")
            val count: Int = array2.size - 1
            if (listIn[i] == "Favourites") {
                special = true
            }
            var summary: String
            summary = if (count != 1) {
                "$count items"
            } else {
                "$count item"
            }
            val item = ListItem(listIn[i], summary, special)
            list += item
            i++
        }
        return list
    }

    companion object {

        /**
         * Called on new instance request
         * @param position [Int]
         * @return [Lists]
         */

        fun newInstance(position: Int): ListsFragment {
            val instance = ListsFragment()
            val args = Bundle()
            args.putInt("position", position)
            instance.arguments = args
            return instance
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onLikeClick(position: Int, text: String) {
    }

    override fun onCardClick(position: Int) {
        val intent = Intent(context, ScrollingActivity::class.java)
        val bundle = Bundle()
        bundle.putString("list", masterlist[position])
        bundle.putInt("from", Fragments.LISTS)
        intent.putExtras(bundle)
        this.startActivity(intent)
        activity?.finish()
        activity?.overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
    }

    override fun onBinClick(position: Int, text: String) {
        Lists().removeList(text, requireContext())
        masterListFinal.removeAt(position)
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }
}

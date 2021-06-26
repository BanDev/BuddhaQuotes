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
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxkeppeler.sheets.core.IconButton
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.Validation
import com.maxkeppeler.sheets.input.type.InputEditText
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial
import com.mikepenz.iconics.utils.paddingDp
import com.mikepenz.iconics.utils.sizeDp
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.activities.ScrollingActivity
import org.bandev.buddhaquotes.adapters.ListRecycler
import org.bandev.buddhaquotes.adapters.QuoteRecycler
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.FragmentListsBinding
import org.bandev.buddhaquotes.items.ListItem
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import kotlin.collections.ArrayList

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
    private var toolbarMenu: Menu? = null

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

    private fun setupRecycler() {
        masterlist = ListsV2(requireContext()).getMasterList()

        masterListFinal = generateMasterList(masterlist.size, masterlist)

        with(binding.listsRecycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = ListRecycler(masterListFinal, this@ListsFragment)
            setHasFixedSize(false)
        }
    }

    @Subscribe
    fun onEventReceive(event: SendEvent) {
        binding.listsRecycler.adapter?.notifyItemChanged(0)
    }

    /**
     * Called when view is full made
     * @param view [View]
     * @param savedInstanceState [Bundle]
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecycler()
    }

    /**
     * Generates a list of lists with the ListItem custom data type
     */

    private fun generateMasterList(max: Int, listIn: List<String>): ArrayList<ListItem> {
        val listNames = mutableListOf<String>()
        val list = ArrayList<ListItem>()
        val lists = ListsV2(requireContext())
        var i = 0
        while (i != max) {
            var special = false
            val individualList = lists.getList(listIn[i])
            val count: Int = individualList.size - 1
            if (listIn[i] == "Favourites") {
                listNames.add(getString(R.string.favourites))
                special = true
            } else listNames.add(listIn[i])
            val summary: String = if (count != 1) "$count items" else "$count item"
            val item = ListItem(listNames[i], summary, special)
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
        toolbarMenu = menu
        menu.findItem(R.id.add).icon = requireContext().addIcon()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                toolbarMenu?.findItem(R.id.add)?.isEnabled = false
                val lists = ListsV2(requireContext()).getMasterList()
                val closeDrawable = IconicsDrawable(
                    requireContext(),
                    RoundedGoogleMaterial.Icon.gmr_expand_more
                ).apply {
                    sizeDp = 24
                    paddingDp = 6
                }
                InputSheet().show(requireContext()) {
                    title(R.string.createNewList)
                    closeIconButton(IconButton(closeDrawable))
                    with(
                        InputEditText {
                            required()
                            hint(R.string.insertName)
                            validationListener { value ->
                                when {
                                    value.contains("//") -> Validation.failed(getString(R.string.validationRule1))
                                    value.lowercase(Locale.ROOT) == getString(R.string.favourites).lowercase(
                                        Locale.ROOT
                                    ) -> Validation.failed(getString(R.string.validationRule2))
                                    lists.contains(value.lowercase(Locale.ROOT)) -> Validation.failed(
                                        getString(R.string.validationRule3) + " $value"
                                    )
                                    else -> Validation.success()
                                }
                            }
                            resultListener { value ->
                                ListsV2(requireContext()).newEmptyList(value.toString())
                                setupRecycler()
                            }
                        }
                    )
                    onNegative(R.string.cancel) {
                        binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    }
                    onPositive(R.string.add) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                        } else {
                            binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        }
                    }
                    onClose { toolbarMenu?.findItem(R.id.add)?.isEnabled = true }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onLikeClick(position: Int, text: String) {
    }

    override fun onCardClick(position: Int) {
        val intent = Intent(context, ScrollingActivity::class.java)
        val bundle = Bundle()
        bundle.putString("list", masterlist[position])
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onBinClick(position: Int, text: String) {
        binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        ListsV2(requireContext()).removeList(text)
        masterListFinal.removeAt(position)
        binding.listsRecycler.adapter?.notifyItemRemoved(position)
    }

    override fun onResume() {
        super.onResume()
        setupRecycler()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}

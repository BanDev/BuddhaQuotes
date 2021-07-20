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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxkeppeler.sheets.core.IconButton
import com.maxkeppeler.sheets.core.Ratio
import com.maxkeppeler.sheets.info.InfoSheet
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.Validation
import com.maxkeppeler.sheets.input.type.InputEditText
import com.maxkeppeler.sheets.lottie.LottieAnimation
import com.maxkeppeler.sheets.lottie.withCoverLottieAnimation
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.activities.ListActivity
import org.bandev.buddhaquotes.adapters.QuoteListRecycler
import org.bandev.buddhaquotes.architecture.ListViewModel
import org.bandev.buddhaquotes.core.Event
import org.bandev.buddhaquotes.core.Feedback
import org.bandev.buddhaquotes.databinding.FragmentListsBinding
import org.bandev.buddhaquotes.items.QuoteList
import org.greenrobot.eventbus.Subscribe

/**
 * Shows a list of lists to the user
 */

class ListsFragment : Fragment(), QuoteListRecycler.Listener {

    private lateinit var binding: FragmentListsBinding
    private lateinit var model: ListViewModel
    private var toolbarMenu: Menu? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
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

    @Subscribe
    fun onEventReceive(event: Event) {
        if (event is Event.ToListFragment) binding.listsRecycler.adapter?.notifyItemChanged(0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.lists_menu, menu)
        toolbarMenu = menu
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                toolbarMenu?.findItem(R.id.add)?.isEnabled = false
                toolbarMenu?.findItem(R.id.help)?.isEnabled = false
                InputSheet().show(requireContext()) {
                    title(R.string.createNewList)
                    closeIconButton(IconButton(R.drawable.ic_down_arrow))
                    with(
                        InputEditText {
                            required()
                            hint(R.string.insertName)
                            validationListener { value ->
                                when {
                                    value.lowercase() == getString(R.string.favourites).lowercase() -> {
                                        Validation.failed(
                                            getString(R.string.validationRule) + " " + getString(
                                                R.string.favourites
                                            ).lowercase()
                                        )
                                    }
                                    else -> Validation.success()
                                }
                            }
                            resultListener { value ->
                                model.newList(value.toString()) {
                                    setupRecycler()
                                }
                            }
                        }
                    )
                    onNegative(R.string.cancel) { Feedback.virtualKey(binding.root) }
                    onPositive(R.string.add) { Feedback.confirm(binding.root) }
                    onClose {
                        toolbarMenu?.findItem(R.id.add)?.isEnabled = true
                        toolbarMenu?.findItem(R.id.help)?.isEnabled = true
                    }
                }
                true
            }
            R.id.help -> {
                toolbarMenu?.findItem(R.id.help)?.isEnabled = false
                toolbarMenu?.findItem(R.id.add)?.isEnabled = false
                InfoSheet().show(requireContext()) {
                    title("Title")
                    content("You can do this to meditate")
                    closeIconButton(IconButton(R.drawable.ic_down_arrow))
                    displayButtons(false)
                    withCoverLottieAnimation(LottieAnimation {
                        ratio(Ratio(2, 1))
                        setupAnimation {
                            setAnimation(R.raw.lists)
                        }
                    })
                    onClose {
                        toolbarMenu?.findItem(R.id.help)?.isEnabled = true
                        toolbarMenu?.findItem(R.id.add)?.isEnabled = true
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        setupRecycler()
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

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
import com.maxkeppeler.sheets.core.Ratio
import com.maxkeppeler.sheets.core.SheetStyle
import com.maxkeppeler.sheets.core.TopStyle
import com.maxkeppeler.sheets.info.InfoSheet
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.Validation
import com.maxkeppeler.sheets.input.type.InputEditText
import com.maxkeppeler.sheets.lottie.LottieAnimation
import com.maxkeppeler.sheets.lottie.LottieAnimationRequest
import com.maxkeppeler.sheets.lottie.withCoverLottieAnimation
import kotlinx.coroutines.runBlocking
import me.kosert.flowbus.GlobalBus
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.activities.ListActivity
import org.bandev.buddhaquotes.adapters.ListAdapter
import org.bandev.buddhaquotes.architecture.ViewModel
import org.bandev.buddhaquotes.bus.Bus
import org.bandev.buddhaquotes.bus.Message
import org.bandev.buddhaquotes.bus.MessageType
import org.bandev.buddhaquotes.core.Feedback
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
            when(it) {
                MessageType.LIKE_UPDATE -> this::onLikeUpdate
                MessageType.NEW_LIST -> this::onNewList
                MessageType.UPDATE_LIST -> this::onUpdateList
                else -> null
            }
        }

        bus.listen()
    }

    override fun select(list: List) {
        startActivity(Intent(context, ListActivity::class.java).putExtra("id", list.id))
    }

    override fun options(list: List) {
        ListOptionsSheet().show(requireContext(), requireActivity().application) {
            title("List Options")
            closeIconButton(IconButton(R.drawable.ic_down_arrow))
            if (list.id != 0) withIconButton(IconButton(R.drawable.ic_delete)) {
                Feedback.virtualKey(requireView())
                dismiss()
                InfoSheet().show(requireContext()) {
                    title("Are you sure?")
                    closeIconButton(IconButton(R.drawable.ic_down_arrow))
                    style(SheetStyle.DIALOG)
                    topStyle(TopStyle.BELOW_COVER)
                    withCoverLottieAnimation(LottieAnimation {
                        ratio(Ratio(5, 2))
                        setupAnimation {
                            setAnimation(R.raw.recycle_bin)
                            setRepeatCount(LottieAnimationRequest.INFINITE)
                        }
                    })
                    content("This will permanently delete this list and all quotes inside it.")
                    onNegative(R.string.cancel)
                    onPositive(R.string.okay) {
                        model.Lists().delete(list.id)
                        val position = lists.indexOf(list)
                        lists.remove(list)
                        binding.listsRecycler.adapter?.notifyItemRemoved(position)
                    }
                }
            }
            if (list.id != 0) withIconButton(IconButton(R.drawable.ic_edit)) {
                Feedback.virtualKey(requireView())
                dismiss()
                InputSheet().show(requireContext()) {
                    title(R.string.rename_list)
                    closeIconButton(IconButton(R.drawable.ic_down_arrow))
                    with(
                        InputEditText {
                            required()
                            hint(R.string.rename_list)
                            defaultValue(list.title)
                            resultListener { value ->
                                model.Lists().rename(list.id, value.toString())
                                val position = lists.indexOf(list)
                                lists[position].title = value.toString()
                                binding.listsRecycler.adapter?.notifyItemChanged(position)
                            }
                        }
                    )
                    onNegative(R.string.cancel) { Feedback.virtualKey(requireView()) }
                    onPositive(R.string.add) { Feedback.confirm(requireView()) }
                }
            }
            displayPositiveButton(false)
            displayNegativeButton(false)
            attachVariables(model, list)
            onListIconSelected { icon ->
                model.Lists().updateIcon(list.id, icon) {
                    bus.broadcast(Message(MessageType.UPDATE_LIST, it))
                }
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

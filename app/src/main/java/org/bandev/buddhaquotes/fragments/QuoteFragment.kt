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

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.maxkeppeler.sheets.options.DisplayMode
import com.maxkeppeler.sheets.options.Option
import com.maxkeppeler.sheets.options.OptionsSheet
import me.kosert.flowbus.GlobalBus
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.architecture.ViewModel
import org.bandev.buddhaquotes.bus.Message
import org.bandev.buddhaquotes.core.Feedback
import org.bandev.buddhaquotes.core.MessageTypes
import org.bandev.buddhaquotes.core.resolveColorAttr
import org.bandev.buddhaquotes.core.shareQuote
import org.bandev.buddhaquotes.custom.DoubleClickListener
import org.bandev.buddhaquotes.databinding.FragmentQuoteBinding
import org.bandev.buddhaquotes.items.Quote

/**
 * QuoteFragment shows quotes to the user with refresh, like & share buttons.
 */

class QuoteFragment : Fragment() {

    private lateinit var binding: FragmentQuoteBinding
    private lateinit var model: ViewModel
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var quote: Quote
    private val quoteImagePref: String = "QUOTE_FRAGMENT_IMAGE"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called when view is full made
     * @param view [View]
     * @param savedInstanceState [Bundle]
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Attach to viewmodel
        model = ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(ViewModel::class.java)

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        editor = sharedPrefs.edit()

        // Start with a quote
        randomQuote()

        with(binding) {
            swipeToRefresh.apply {
                setOnRefreshListener {
                    randomQuote()
                    isRefreshing = false
                }
            }
            like.setOnClickListener { onLikeClicked() }
            next.setOnClickListener {
                Feedback.virtualKey(it)
                randomQuote()
            }
            more.setOnClickListener { showOptionsSheet(it) }
            quoteFragmentImage.apply {
                setQuoteImage(sharedPrefs.getInt(quoteImagePref, 0))
                setOnClickListener(object : DoubleClickListener() {
                    override fun onSingleClick(view: View?) {}
                    override fun onDoubleClick(view: View?) {
                        onLikeClicked()
                    }
                })
                setOnLongClickListener {
                    changeImageSheet()
                    true
                }
            }
            content.setOnClickListener(object : DoubleClickListener() {
                override fun onSingleClick(view: View?) {}
                override fun onDoubleClick(view: View?) {
                    onLikeClicked()
                }
            })
        }
    }

    private fun randomQuote() {
        model.Quotes().getRandom {
            quote = it
            //binding.number.text = getString(R.string.quote_number, quote.id)
            binding.quote.text = getString(quote.resource)
            binding.like.setImageResource(heart(quote.liked))
        }
    }

    internal fun onLikeClicked() {
        Feedback.virtualKey(binding.root)
        quote.liked = !quote.liked
        binding.like.setImageResource(heart(quote.liked))
        if (quote.liked) {
            binding.likeAnimator.likeAnimation()
            GlobalBus.post(Message(MessageTypes.LIKE_UPDATE, +1))
        } else GlobalBus.post(Message(MessageTypes.LIKE_UPDATE, -1))
        model.Quotes().setLike(quote.id, quote.liked)
    }

    private fun showOptionsSheet(view: View) {
        Feedback.virtualKey(view)
        view.isEnabled = false
        OptionsSheet().show(requireContext()) {
            displayMode(DisplayMode.LIST)
            displayToolbar(false)
            displayHandle(true)
            with(
                Option(R.drawable.ic_share, R.string.share),
                Option(R.drawable.ic_add_circle_outline, R.string.addToList)
            )
            onPositive { index: Int, _: Option ->
                Feedback.virtualKey(binding.root)
                if (index == 0) context?.shareQuote(quote) else showSecondBottomSheet()
            }
            onClose { view.isEnabled = true }
        }
    }

    private fun showSecondBottomSheet() {
        model.Lists().getAll { it ->
            it.removeAt(0)
            if (it.isNotEmpty()) {
                val options = mutableListOf<Option>()

                it.forEach { list ->
                    options.add(Option(list.icon.drawable, list.title))
                }

                OptionsSheet().show(requireContext()) {
                    displayMode(DisplayMode.LIST)
                    displayToolbar(false)
                    displayHandle(true)
                    with(options)
                    onPositive { index: Int, _: Option ->
                        Feedback.virtualKey(view ?: return@onPositive)
                        val list = it[index]
                        model.ListQuotes().exists(quote, list) { has ->
                            if (has) {
                                Snackbar.make(
                                    binding.root,
                                    "This quote is already in that list",
                                    LENGTH_SHORT
                                ).show()
                            } else {
                                model.ListQuotes().addTo(list.id, quote)
                                list.count++
                                GlobalBus.post(Message(MessageTypes.UPDATE_LIST, list))
                            }
                        }
                    }
                }
            } else Snackbar.make(binding.root, getString(R.string.no_list), LENGTH_SHORT).show()
        }
    }

    private fun ImageView.setQuoteImage(int: Int) {
        setImageResource(
            when (int) {
                1 -> R.drawable.image_monk
                2 -> R.drawable.image_dharma_wheel
                3 -> R.drawable.image_anahata
                4 -> R.drawable.image_mandala
                5 -> R.drawable.image_endless_knot
                6 -> R.drawable.image_elephant
                7 -> R.drawable.image_temple
                8 -> R.drawable.image_lamp
                9 -> R.drawable.image_shrine
                10 -> R.drawable.image_lotus
                11 -> R.drawable.image_lotus_water
                else -> R.drawable.image_buddha
            }
        )
        contentDescription = getString(
            when (int) {
                1 -> R.string.monk
                2 -> R.string.dharma_wheel
                3 -> R.string.anahata
                4 -> R.string.mandala
                5 -> R.string.endless_knot
                6 -> R.string.elephant
                7 -> R.string.temple
                8 -> R.string.lamp
                9 -> R.string.shrine
                10 -> R.string.lotus
                11 -> R.string.water_lotus
                else -> R.string.buddha
            }
        )
    }

    private fun changeImageSheet() {
        OptionsSheet().show(requireContext()) {
            displayMode(DisplayMode.GRID_VERTICAL)
            with(
                Option(R.drawable.sheet_buddha, R.string.buddha),
                Option(R.drawable.sheet_monk, R.string.monk),
                Option(R.drawable.sheet_dharma_wheel, R.string.dharma_wheel),
                Option(R.drawable.sheet_anahata, R.string.anahata),
                Option(R.drawable.sheet_mandala, R.string.mandala),
                Option(R.drawable.sheet_endless_knot, R.string.endless_knot),
                Option(R.drawable.sheet_elephant, R.string.elephant),
                Option(R.drawable.sheet_temple, R.string.temple),
                Option(R.drawable.sheet_lamp, R.string.lamp),
                Option(R.drawable.sheet_shrine, R.string.shrine),
                Option(R.drawable.sheet_lotus, R.string.lotus),
                Option(R.drawable.sheet_lotus_water, R.string.water_lotus)
            )

            onPositive { index: Int, _: Option ->
                Feedback.confirm(view ?: return@onPositive)
                binding.quoteFragmentImage.setQuoteImage(index)
                editor.putInt(quoteImagePref, index).apply()
            }
        }
    }

    private fun heart(liked: Boolean): Int {
        return if (liked) R.drawable.ic_heart_red else R.drawable.ic_heart_outline
    }

    override fun onResume() {
        super.onResume()
        binding.swipeToRefresh.setColorSchemeColors(requireContext().resolveColorAttr(R.attr.colorPrimary))
    }

    companion object {
        fun newInstance(position: Int): QuoteFragment {
            val instance = QuoteFragment()
            val args = Bundle()
            args.putInt("position", position)
            instance.arguments = args
            return instance
        }
    }
}

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

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.maxkeppeler.sheets.core.Ratio
import com.maxkeppeler.sheets.info.InfoSheet
import com.maxkeppeler.sheets.lottie.LottieAnimation
import com.maxkeppeler.sheets.lottie.withCoverLottieAnimation
import com.maxkeppeler.sheets.options.DisplayMode
import com.maxkeppeler.sheets.options.Option
import com.maxkeppeler.sheets.options.OptionsSheet
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.architecture.QuoteViewModel
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.custom.DoubleClickListener
import org.bandev.buddhaquotes.databinding.FragmentQuoteBinding
import org.bandev.buddhaquotes.items.Quote

/**
 * QuoteFragment shows quotes to the user with refresh, like & share buttons.
 */

class QuoteFragment : Fragment() {

    private lateinit var binding: FragmentQuoteBinding
    private lateinit var model: QuoteViewModel
    private lateinit var icons: Icons
    private lateinit var quote: Quote
    private var toolbarMenu: Menu? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
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
            .create(QuoteViewModel::class.java)

        // Get icons class
        icons = Icons(requireContext())

        // Start with a quote
        randomQuote()

        with(binding) {
            swipeToRefresh.also {
                it.setOnRefreshListener {
                    randomQuote()
                    it.isRefreshing = false
                }
            }
            like.setOnClickListener { onLikeClicked() }
            more.setOnClickListener { showOptionsSheet() }
            quoteFragmentImage.setImageResource(R.drawable.ic_lotus)
        }
        binding.content.setOnClickListener(object : DoubleClickListener() {
            override fun onSingleClick(view: View?) {}
            override fun onDoubleClick(view: View?) {
                if (!quote.liked) onLikeClicked()
            }
        })
    }

    private fun randomQuote() {
        model.getRandom {
            quote = it
            binding.number.text = getString(R.string.quote_number, quote.id)
            binding.quote.text = getString(quote.resource)
            binding.like.setImageDrawable(icons.heart(quote.liked))
        }
    }

    internal fun onLikeClicked() {
        quote.liked = !quote.liked
        binding.like.setImageDrawable(icons.heart(quote.liked))
        if (quote.liked) binding.likeAnimator.likeAnimation()
        model.setLike(quote.id, quote.liked)
    }

    private fun showOptionsSheet() {
        binding.more.isEnabled = false
        OptionsSheet().show(requireContext()) {
            displayMode(DisplayMode.LIST)
            displayToolbar(false)
            displayHandle(true)
            with(
                Option(icons.share(), R.string.share),
                Option(icons.addCircle(), R.string.addToList)
            )
            onPositive { index: Int, _: Option ->
                Feedback.virtualKey(binding.root)
                if (index == 0) context?.shareQuote(quote)
                else showSecondBottomSheet()
            }
            onClose { binding.more.isEnabled = true }
        }
    }

    private fun showSecondBottomSheet() {
        OptionsSheet().show(requireContext()) {
            displayMode(DisplayMode.LIST)
            displayToolbar(false)
            displayHandle(true)
            with(Option(icons.heart(false), R.string.favourites))
            onPositive { index: Int, _: Option ->
                Feedback.virtualKey(binding.root)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.quote_menu, menu)
        toolbarMenu = menu
        menu.findItem(R.id.options).icon = icons.tune()
        menu.findItem(R.id.help).icon = icons.help()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.options -> {
                toolbarMenu?.findItem(R.id.options)?.isEnabled = false
                OptionsSheet().show(requireContext()) {
                    displayMode(DisplayMode.GRID_HORIZONTAL)
                    with(
                        Option(R.drawable.ic_seokguram, "Buddha"),
                        Option(icons.lotus(), "Lotus"),
                        Option(icons.lotus(), "Lotus"),
                        Option(icons.lotus(), "Lotus")
                    )
                    onPositive { index: Int, _: Option ->
                        binding.quoteFragmentImage.also {
                            when (index) {
                                0 -> it.setImageResource(R.drawable.ic_lotus)
                                1 -> it.setImageResource(R.drawable.ic_buddha_no_background)
                            }
                        }
                    }
                    onClose { toolbarMenu?.findItem(R.id.options)?.isEnabled = true }
                }
                true
            }
            R.id.help -> {
                toolbarMenu?.findItem(R.id.help)?.isEnabled = false
                InfoSheet().show(requireContext()) {
                    title("Team Collaboration")
                    content("In the world of software projects, it is inevitable...")
                    closeIconButton(icons.closeSheet())
                    displayButtons(false)
                    withCoverLottieAnimation(LottieAnimation {
                        ratio(Ratio(2, 1))
                        setupAnimation {
                            setAnimation(R.raw.lotus)
                        }
                    })
                    onClose { toolbarMenu?.findItem(R.id.help)?.isEnabled = true }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

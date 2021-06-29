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
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
 * It is the first item in the [FragmentAdapter] on MainActivity
 */

class QuoteFragment : Fragment() {

    private lateinit var binding: FragmentQuoteBinding
    private lateinit var model: QuoteViewModel
    private lateinit var icons: Icons
    private lateinit var quote: Quote

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
            .create(QuoteViewModel::class.java)

        // Get icons class
        icons = Icons(requireContext())

        // On refresh get a new quote
        binding.swipeRefresh.setOnRefreshListener {
            randomQuote()
            binding.swipeRefresh.isRefreshing = false
        }

        // On content liked
        binding.like.setOnClickListener {
            onLikeClicked()
        }

        // When more is pressed
        binding.more.setOnClickListener { showOptionsSheet() }

        // When screen double tapped
        binding.content.setOnClickListener(object : DoubleClickListener() {
            override fun onSingleClick(view: View?) {}
            override fun onDoubleClick(view: View?) {
                if (!quote.liked) onLikeClicked()
            }
        })

        // Start with a quote
        randomQuote()
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
                binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
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
                binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.swipeRefresh.setColorSchemeColors(requireContext().resolveColorAttr(R.attr.colorPrimary))
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

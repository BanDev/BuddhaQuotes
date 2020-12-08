package org.bandev.buddhaquotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.bandev.buddhaquotes.Quotes
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.databinding.FragmentQuoteBinding
import org.bandev.buddhaquotes.databinding.FragmentSampleBinding

/**
 * QuoteFragment shows quotes to the user with refresh, like & share buttons.
 * Is the first item in the [FragmentAdapter] on MainActivity
 * @author jack.txt
 * @since 1.7.0
 */

class QuoteFragment : Fragment() {

    private var _binding: FragmentQuoteBinding? = null
    private val binding get() = _binding!!
    private var quotes = Quotes()

    /**
     * Sets the correct view of the Fragment
     * @param inflater [LayoutInflater]
     * @param container [ViewGroup]
     * @param savedInstanceState [Bundle]
     * @return [View]
     */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called when view is full made
     * @param view [View]
     * @param savedInstanceState [Bundle]
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.quote.text = quotes.random(0, requireContext())
        binding.number.text = getString(R.string.quote_number, quotes.quotenumberglobal)
    }

    companion object {

        /**
         * Called on new instance request
         * @param position [Int]
         * @return [QuoteFragment]
         */

        fun newInstance(position: Int): QuoteFragment {
            val instance = QuoteFragment()
            val args = Bundle()
            args.putInt("position", position)
            instance.arguments = args
            return instance
        }
    }
}
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

package org.bandev.buddhaquotes.custom

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxkeppeler.sheets.core.Sheet
import org.bandev.buddhaquotes.adapters.AddQuoteSheetAdapter
import org.bandev.buddhaquotes.databinding.AddQuoteSheetBinding
import org.bandev.buddhaquotes.items.Quote

typealias AddItemListener = (quote: Quote) -> Unit

@Suppress("unused")
class AddQuoteSheet : Sheet() {

    private lateinit var binding: AddQuoteSheetBinding
    private lateinit var quotes: List<Quote>
    private var listener: AddItemListener? = null

    private val adapterListener = object : Listener {
        override fun select(quote: Quote) {
            listener?.invoke(quote)
            dismiss()
        }
    }

    fun onPositive(listener: AddItemListener) {
        this.listener = listener
    }

    fun onPositive(@StringRes positiveRes: Int, listener: AddItemListener? = null) {
        this.positiveText = windowContext.getString(positiveRes)
        this.listener = listener
    }

    fun onPositive(positiveText: String, listener: AddItemListener? = null) {
        this.positiveText = positiveText
        this.listener = listener
    }

    override fun onCreateLayoutView(): View {
        return AddQuoteSheetBinding.inflate(LayoutInflater.from(activity))
            .also { binding = it }.root
    }

    fun with(list: List<Quote>) {
        quotes = list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayButtonsView(false)

        val addQuoteSheetAdapter = AddQuoteSheetAdapter(requireContext(), quotes, adapterListener)

        with(binding.exampleRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = addQuoteSheetAdapter
        }
    }

    fun build(ctx: Context, width: Int? = null, func: AddQuoteSheet.() -> Unit): AddQuoteSheet {
        this.windowContext = ctx
        this.width = width
        this.func()
        return this
    }

    fun show(ctx: Context, width: Int? = null, func: AddQuoteSheet.() -> Unit): AddQuoteSheet {
        this.windowContext = ctx
        this.width = width
        this.func()
        this.show()
        return this
    }

    interface Listener {
        fun select(quote: Quote)
    }
}
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
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxkeppeler.sheets.core.Sheet
import org.bandev.buddhaquotes.adapters.AddQuoteSheetAdapter
import org.bandev.buddhaquotes.core.Quotes
import org.bandev.buddhaquotes.databinding.AddQuoteSheetBinding
import org.bandev.buddhaquotes.items.Quote

private typealias PositiveListener = () -> Unit

@Suppress("unused")
class AddQuoteSheet : Sheet() {

    private lateinit var binding: AddQuoteSheetBinding
    private lateinit var quotes: List<Quote>

    fun onPositive(positiveListener: PositiveListener) {
        this.positiveListener = positiveListener
    }

    fun onPositive(@StringRes positiveRes: Int, positiveListener: PositiveListener? = null) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveListener = positiveListener
    }

    fun onPositive(positiveText: String, positiveListener: PositiveListener? = null) {
        this.positiveText = positiveText
        this.positiveListener = positiveListener
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

        val addQuoteSheetAdapter = AddQuoteSheetAdapter(requireContext(), quotes) { position ->
            dismiss()
        }

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
}
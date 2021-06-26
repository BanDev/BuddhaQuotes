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
import org.bandev.buddhaquotes.database.Database
import org.bandev.buddhaquotes.databinding.AddQuoteSheetBinding

private typealias PositiveListener = () -> Unit

@Suppress("unused")
class AddQuoteSheet : Sheet() {

    private lateinit var binding: AddQuoteSheetBinding

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayButtonsView(false)

        val addQuoteSheetAdapter = AddQuoteSheetAdapter(requireContext()) { position ->
            Toast.makeText(
                context,
                Database(requireContext()).getQuote(position + 1).quote,
                Toast.LENGTH_SHORT
            ).show()
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
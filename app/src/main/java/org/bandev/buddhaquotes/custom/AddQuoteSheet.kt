package org.bandev.buddhaquotes.custom

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.maxkeppeler.sheets.core.Sheet
import org.bandev.buddhaquotes.core.Quotes
import org.bandev.buddhaquotes.databinding.AddQuoteSheetBinding

class AddQuoteSheet : Sheet() {

    private lateinit var binding: AddQuoteSheetBinding

    override fun onCreateLayoutView(): View {
        return AddQuoteSheetBinding.inflate(LayoutInflater.from(activity))
            .also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



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
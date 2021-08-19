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

import android.app.Application
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maxkeppeler.sheets.core.IconButton
import com.maxkeppeler.sheets.core.Sheet
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.ListIconAdapter
import org.bandev.buddhaquotes.architecture.ListMapper
import org.bandev.buddhaquotes.architecture.ViewModel
import org.bandev.buddhaquotes.core.onClick
import org.bandev.buddhaquotes.databinding.ListOptionsSheetBinding
import org.bandev.buddhaquotes.items.List
import org.bandev.buddhaquotes.items.ListIcon

class ListOptionsSheet : Sheet() {

    private lateinit var binding: ListOptionsSheetBinding
    private lateinit var listIconManager: ListMapper

    private lateinit var model: ViewModel
    private lateinit var list: List

    private lateinit var onListIconSelected: (ListIcon) -> Any
    private lateinit var onListRemoved: (List) -> Any
    private lateinit var onListRenamed: (String) -> Any

    fun attachVariables(model: ViewModel, list: List) {
        this.model = model
        this.list = list
    }

    override fun onCreateLayoutView(): View {
        title("List Options")
        closeIconButton(IconButton(R.drawable.ic_down_arrow))
        displayPositiveButton(false)
        displayNegativeButton(false)
        binding = ListOptionsSheetBinding.inflate(layoutInflater)

        binding.listIconRecycler.apply {
            adapter = ListIconAdapter(listIconManager.listIcons, onListIconSelected)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.remove.onClick {
            onListRemoved(list)
            dismiss()
        }

        binding.renameButton.onClick {
            onListRenamed(binding.rename.editableText.toString())
            dismiss()
        }

        return binding.root
    }

    fun onListIconSelected(func: (ListIcon) -> Unit) {
        onListIconSelected = func
    }

    fun onListRemoved(func: (List) -> Unit) {
        onListRemoved = func
    }

    fun onListRenamed(func: (String) -> Unit) {
        onListRenamed = func
    }

    fun build(
        ctx: Context,
        application: Application,
        width: Int? = null,
        func: ListOptionsSheet.() -> Unit
    ): ListOptionsSheet {
        this.windowContext = ctx
        this.listIconManager = ListMapper(application)
        this.width = width
        this.func()
        return this
    }

    fun show(
        ctx: Context,
        application: Application,
        width: Int? = null,
        func: ListOptionsSheet.() -> Unit
    ): ListOptionsSheet {
        this.windowContext = ctx
        this.listIconManager = ListMapper(application)
        this.width = width
        this.func()
        this.show()
        return this
    }
}
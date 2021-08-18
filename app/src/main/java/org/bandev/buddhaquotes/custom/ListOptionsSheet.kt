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
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxkeppeler.sheets.core.IconButton
import com.maxkeppeler.sheets.core.Sheet
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.ListIconAdapter
import org.bandev.buddhaquotes.architecture.ListMapper
import org.bandev.buddhaquotes.architecture.ViewModel
import org.bandev.buddhaquotes.databinding.CustomiseListSheetBinding
import org.bandev.buddhaquotes.items.ListIcon

class ListOptionsSheet : Sheet(), ListIconAdapter.Listener {

    private lateinit var binding: CustomiseListSheetBinding

    private lateinit var model: ViewModel
    private lateinit var listIconManager: ListMapper
    private var listId = 0

    private lateinit var onListIconSelected: (icon: ListIcon) -> Any

    fun attachVariables(_model: ViewModel, _listId: Int) {
        model = _model
        listId = _listId
    }

    override fun onCreateLayoutView(): View {
        title("List Options")
        closeIconButton(IconButton(R.drawable.ic_down_arrow))
        displayPositiveButton(false)
        displayNegativeButton(false)
        binding = CustomiseListSheetBinding.inflate(layoutInflater)

        binding.listIconRecycler.apply {
            adapter = ListIconAdapter(listIconManager.listIcons, this@ListOptionsSheet)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        return binding.root
    }

    fun onListIconSelected(func: (icon: ListIcon) -> Any) {
        onListIconSelected = func
    }

    override fun select(icon: ListIcon) {
        onListIconSelected(icon)
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
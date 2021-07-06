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
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxkeppeler.sheets.core.Sheet
import org.bandev.buddhaquotes.adapters.ListIconRecycler
import org.bandev.buddhaquotes.architecture.ListIconManager
import org.bandev.buddhaquotes.architecture.ListViewModel
import org.bandev.buddhaquotes.databinding.CustomiseListSheetBinding
import org.bandev.buddhaquotes.items.ListIcon

class CustomiseListSheet : Sheet(), ListIconRecycler.Listener {

    private lateinit var binding: CustomiseListSheetBinding
    private lateinit var model: ListViewModel
    private lateinit var listIconManager: ListIconManager
    private var listId = 0

    fun attachVariables(_model: ListViewModel, _listId: Int) {
        model = _model
        listId = _listId
    }

    override fun onCreateLayoutView(): View {
        binding = CustomiseListSheetBinding.inflate(layoutInflater)

        with(binding.listIconRecycler) {
            adapter = ListIconRecycler(listIconManager.getAll(), this@CustomiseListSheet)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        return binding.root
    }

    override fun select(icon: ListIcon) {
        model.updateIcon(listId, icon) {
            Toast.makeText(context, "list icon updated", Toast.LENGTH_SHORT).show()
        }
    }

    fun build(
        ctx: Context,
        application: Application,
        width: Int? = null,
        func: CustomiseListSheet.() -> Unit
    ): CustomiseListSheet {
        this.windowContext = ctx
        this.listIconManager = ListIconManager(application)
        this.width = width
        this.func()
        return this
    }

    fun show(
        ctx: Context,
        application: Application,
        width: Int? = null,
        func: CustomiseListSheet.() -> Unit
    ): CustomiseListSheet {
        this.windowContext = ctx
        this.listIconManager = ListIconManager(application)
        this.width = width
        this.func()
        this.show()
        return this
    }
}
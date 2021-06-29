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

package org.bandev.buddhaquotes.architecture

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.items.QuoteList
import org.bandev.buddhaquotes.items.QuoteListWithQuotes

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private var listRepository: ListRepository = ListRepository(application)

    /** Get a List & Quotes using its key */
    fun get(id: Int, after: (quoteList: QuoteListWithQuotes) -> Unit) {
        viewModelScope.launch {
            after(listRepository.get(id))
        }
    }

    /** Get all Lists and Quotes */
    fun getAll(after: (quote: List<QuoteListWithQuotes>) -> Unit) {
        viewModelScope.launch {
            after(listRepository.getAll())
        }
    }

    /** Get all Lists without their Quotes */
    fun getAllNoQuotes(after: (quote: List<QuoteList>) -> Unit) {
        viewModelScope.launch {
            after(listRepository.getAllNoQuotes())
        }
    }
}

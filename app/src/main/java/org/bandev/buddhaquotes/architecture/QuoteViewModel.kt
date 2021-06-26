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
import kotlinx.coroutines.runBlocking
import org.bandev.buddhaquotes.items.Quote

class QuoteViewModel(application: Application) : AndroidViewModel(application) {

    var repository: Repository = Repository(application)

    /** Get a Quote using its key */
    fun get(id: Int, after: (quote: Quote) -> Unit) {
        runBlocking {
            after(repository.get(id))
        }
    }

    /** Count the number of quotes */
    fun getRandom(after: (quote: Quote) -> Unit) {
        count { size ->
            get((0..size).random()) { quote -> after(quote) }
        }
    }

    /** Get all Quotes */
    fun getAll(after: (quote: List<Quote>) -> Unit) {
        runBlocking {
            after(repository.getAll())
        }
    }

    /** Count the number of quotes */
    private fun count(after: (num: Int) -> Unit) {
        runBlocking {
            after(repository.count())
        }
    }

    /** Define if a quote is liked or not */
    fun setLike(id: Int, like: Boolean) {
        runBlocking {
            repository.setLike(id, like)
        }
    }

}
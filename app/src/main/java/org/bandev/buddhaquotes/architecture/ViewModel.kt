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
import org.bandev.buddhaquotes.items.List
import org.bandev.buddhaquotes.items.ListIcon
import org.bandev.buddhaquotes.items.Quote

/**
 * A level of abstraction between the ui
 * and the db layers. Launches coroutines
 * to the repositiory.
 *
 * @author Jack Devey
 * @date 27/07/21
 */

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: Repository = Repository(application)

    /**
     * Interact with the quote table of the
     * database to allow for querying quotes
     * and liking or unliking them.
     */

    inner class Quotes {

        private val quotes: Repository.Quotes = repo.Quotes()

        /** Get one singular quote */
        fun get(id: Int, after: (quote: Quote) -> Unit) {
            viewModelScope.launch {
                after(quotes.get(id))
            }
        }

        /** Get all quotes */
        fun getAll(after: (quote: MutableList<Quote>) -> Unit) {
            viewModelScope.launch {
                after(quotes.getAll())
            }
        }

        /** Get a random quote */
        fun getRandom(after: (quote: Quote) -> Unit) {
            count { size ->
                get((1..size).random()) { quote -> after(quote) }
            }
        }

        /** Set the like state of a quote */
        fun setLike(id: Int, liked: Boolean) {
            viewModelScope.launch {
                if (liked) quotes.like(id) else quotes.unlike(id)
            }
        }

        /** Count the number of quotes */
        private fun count(after: (size: Int) -> Unit) {
            viewModelScope.launch {
                after(quotes.count())
            }
        }

    }

    /**
     * Interact with the list table of the
     * database to allow for adding or removing
     * list records and renaming and updating
     * some UI elements like title and icon.
     */

    inner class Lists {

        private val lists: Repository.Lists = repo.Lists()

        /** Get one singular list */
        fun get(id: Int, after: (list: List) -> Unit) {
            viewModelScope.launch {
                after(lists.get(id))
            }
        }

        /** Get all lists */
        fun getAll(after: (quote: MutableList<List>) -> Unit) {
            viewModelScope.launch {
                after(lists.getAll())
            }
        }

        /** Rename a list */
        fun rename(id: Int, title: String) {
            viewModelScope.launch {
                lists.rename(id, title)
            }
        }

        /** Update a list's icon */
        fun updateIcon(id: Int, icon: ListIcon) {
            viewModelScope.launch {
                lists.updateIcon(id, icon)
            }
        }

        /** New empty list */
        fun new(title: String, after: (list: List) -> Unit) {
            viewModelScope.launch {
                after(lists.new(title))
            }
        }

        /** Delete a list */
        fun delete(id: Int) {
            viewModelScope.launch {
                lists.delete(id)
            }
        }

    }

    /**
     * Intereact with the database's record
     * linking table to add, remove and count
     * up all of the quotes that a list has.
     */

    inner class ListQuotes {

        private val listQuotes: Repository.ListQuotes = repo.ListQuotes()

        /** Get just one list */
        fun getFrom(id: Int, after: (quotes: MutableList<Quote>) -> Unit) {
            viewModelScope.launch {
                after(listQuotes.getFrom(id))
            }
        }

        /** If the quote exists */
        fun exists(quote: Quote, list: List, after: (has: Boolean) -> Any) {
            viewModelScope.launch {
                after(listQuotes.has(quote.id, list.id))
            }
        }

        /** Add a quote to a list */
        fun addTo(id: Int, quote: Quote) {
            viewModelScope.launch {
                listQuotes.addTo(id, quote)
            }
        }

        /** Remove a quote from a list */
        fun removeFrom(id: Int, quote: Quote) {
            viewModelScope.launch {
                listQuotes.removeFrom(id, quote)
            }
        }

        /** Count the qoutes in a list */
        fun count(id: Int, after: (size: Int) -> Unit) {
            viewModelScope.launch {
                after(listQuotes.count(id))
            }
        }

    }

}
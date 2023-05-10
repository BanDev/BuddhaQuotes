package org.bandev.buddhaquotes.architecture

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import java.util.Calendar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bandev.buddhaquotes.architecture.quotes.QuoteStore
import org.bandev.buddhaquotes.items.ListData
import org.bandev.buddhaquotes.items.ListIcon
import org.bandev.buddhaquotes.items.QuoteItem

/**
 * A level of abstraction between the ui
 * and the db layers. Launches coroutines
 * to the repository.
 *
 * @author Jack Devey
 * @date 27/07/21
 */

class BuddhaQuotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository(application)

    /**
     * Interact with the quote table of the
     * database to allow for querying quotes
     * and liking or unliking them.
     */

    private var _selectedQuote = MutableStateFlow(QuoteItem())
    val selectedQuote: StateFlow<QuoteItem> = _selectedQuote.asStateFlow()

    private var _dailyQuote = MutableStateFlow(QuoteItem())
    val dailyQuote: StateFlow<QuoteItem> = _dailyQuote.asStateFlow()

    fun setNewQuote(quote: QuoteItem) {
        _selectedQuote.value = quote
    }

    fun toggleLikedOnSelectedQuote() {
        _selectedQuote.update { quote ->
            quote.copy(isLiked = !quote.isLiked)
        }
    }

    private val _quotes = MutableStateFlow<List<QuoteItem>>(emptyList())
    val quotes: StateFlow<List<QuoteItem>> = _quotes.asStateFlow()

    init {
        viewModelScope.launch {
            Quotes().run {
                _selectedQuote.value = getRandom()
                _dailyQuote.value = getDaily()
                _quotes.value = getAll()
            }
        }
    }

    inner class Quotes {

        private val quotes: Repository.Quotes = repository.Quotes()

        /** Get one singular quote */
        suspend fun get(id: Int): QuoteItem = withContext(Dispatchers.IO) {
            quotes.get(id)
        }

        /** Get all quotes */
        suspend fun getAll(): List<QuoteItem> = withContext(Dispatchers.IO) {
            quotes.getAll()
        }

        /** Get a random quote */
        suspend fun getRandom(): QuoteItem = get((0 until QuoteStore.quotes.size).random())

        /** Get the quote of the day */
        suspend fun getDaily(): QuoteItem {
            return get(Calendar.getInstance().get(Calendar.DAY_OF_YEAR) % quotes.count())
        }

        /** Set the like state of a quote */
        suspend fun setLike(id: Int, liked: Boolean) = withContext(Dispatchers.IO) {
            if (liked) quotes.like(id) else quotes.unlike(id)
        }
    }

    private val _lists = mutableStateListOf<ListData>()
    val lists: List<ListData> = _lists

    init {
        viewModelScope.launch {
            _lists += Lists().getAll()
        }
    }


    inner class Lists {
        private val _lists: Repository.Lists = repository.Lists()

        /** Get one singular list */
        suspend fun get(id: Int): ListData = withContext(Dispatchers.IO) {
            _lists.get(id)
        }

        /** Get all lists */
        suspend fun getAll(): List<ListData> = withContext(Dispatchers.IO) {
            _lists.getAll()
        }

        /** Rename a list */
        suspend fun rename(id: Int, title: String) = withContext(Dispatchers.IO) {
            _lists.rename(id, title)
        }

        /** Update a list's icon */
        suspend fun updateIcon(listId: Int, icon: ListIcon): ListData = withContext(Dispatchers.IO) {
            _lists.updateIcon(listId, icon)
            _lists.get(listId)
        }

        /** New empty list */
        suspend fun new(title: String): ListData = withContext(Dispatchers.IO) {
            _lists.new(title).also {
                this@BuddhaQuotesViewModel._lists += ListData(id = it.id, title = it.title)
            }
        }

        /** Delete a list */
        suspend fun delete(id: Int) = withContext(Dispatchers.IO) {
            if (id != 1) {
                _lists.delete(id)
                this@BuddhaQuotesViewModel._lists.removeAll { it.id == id }
            }
        }
    }

    inner class ListQuotes {

        private val listQuotes: Repository.ListQuotes = repository.ListQuotes()

        /** Get just one list */
        suspend fun getFrom(id: Int): List<QuoteItem> = listQuotes.getFrom(id)

        /** If the quote exists */
        fun exists(quote: QuoteItem, list: ListData, after: (has: Boolean) -> Any) {
            viewModelScope.launch(Dispatchers.IO) {
                after(listQuotes.has(quote.id, list.id))
            }
        }

        /** Add a quote to a list */
        fun addTo(id: Int, quote: QuoteItem) {
            viewModelScope.launch(Dispatchers.IO) {
                listQuotes.addTo(id, quote)
            }
        }

        /** Remove a quote from a list */
        fun removeFrom(id: Int, quote: QuoteItem) {
            viewModelScope.launch(Dispatchers.IO) {
                listQuotes.removeFrom(id, quote)
            }
        }

        /** Count the quotes in a list */
        fun count(id: Int, after: (size: Int) -> Unit) {
            viewModelScope.launch(Dispatchers.IO) {
                after(listQuotes.count(id))
            }
        }
    }
}
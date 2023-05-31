package org.bandev.buddhaquotes.architecture.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.architecture.quotes.Quote
import org.bandev.buddhaquotes.items.ListData
import org.bandev.buddhaquotes.items.ListIcon
import org.bandev.buddhaquotes.items.QuoteItem
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val listRepository: ListRepository
) : ViewModel() {
    suspend fun get(id: Int) = listRepository.get(id)

    fun getAll() = listRepository.getAll().asLiveData(viewModelScope.coroutineContext)

    /** Get just one list */
    fun getFrom(id: Int): LiveData<List<QuoteItem>> = listRepository
        .getFrom(id)
        .map { it.map(Quote::toUIQuote) }

    private fun like(quoteId: Int): Unit = addTo(1, quoteId)

    private fun unlike(quoteId: Int): Unit = removeFrom(1, quoteId)

    fun setLiked(quoteId: Int, liked: Boolean) {
        if (liked) like(quoteId) else unlike(quoteId)
    }

    suspend fun new(title: String): ListData = listRepository.new(title)

    suspend fun delete(listId: Int) = listRepository.delete(listId)

    /** If the quote exists */
    suspend fun exists(quote: QuoteItem, list: ListData): Boolean {
        return listRepository.has(quote.id, list.id)
    }

    fun addTo(id: Int, quoteId: Int) {
        viewModelScope.launch {
            listRepository.addTo(id, quoteId)
        }
    }

    /** Remove a quote from a list */
    fun removeFrom(id: Int, quoteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            listRepository.removeFrom(id, quoteId)
        }
    }

    fun updateIcon(listId: Int, icon: ListIcon) {
        viewModelScope.launch(Dispatchers.IO) {
            listRepository.updateIcon(listId, icon)
        }
    }
}

package org.bandev.buddhaquotes.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.model.Quote
import org.bandev.buddhaquotes.repository.QuoteRepository
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {
    val quotes: StateFlow<List<Quote>> = repository.getAllQuotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favourites: StateFlow<List<Quote>> = repository.getFavouriteQuotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentIndex = MutableStateFlow(0)
    val currentQuote: StateFlow<Quote?> = combine(quotes, _currentIndex) { list, index ->
        list.getOrNull(index)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val dailyQuote: StateFlow<Quote?> = quotes.map { quotes ->
        if (quotes.isEmpty()) {
            null
        } else {
            val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
            val index = dayOfYear % quotes.size
            quotes[index]
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun nextQuote() {
        val next = (_currentIndex.value + 1) % quotes.value.size.coerceAtLeast(1)
        _currentIndex.value = next
    }

    fun previousQuote() {
        val prev =
            (_currentIndex.value - 1 + quotes.value.size) % quotes.value.size.coerceAtLeast(1)
        _currentIndex.value = prev
    }

    fun setFavourite(quote: Quote) {
        viewModelScope.launch {
            repository.updateLiked(quote.id, true)
        }
    }

    fun toggleFavourite(quote: Quote) {
        viewModelScope.launch {
            repository.updateLiked(quote.id, !quote.isLiked)
        }
    }
}
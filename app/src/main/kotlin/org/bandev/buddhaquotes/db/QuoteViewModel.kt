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
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val favourites: StateFlow<List<Quote>?> = repository.getFavouriteQuotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _shuffledIndexes = MutableStateFlow<List<Int>>(emptyList())

    val dailyQuote: StateFlow<Quote?> = quotes.map { quotes ->
        val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        quotes.getOrNull(dayOfYear % quotes.size.coerceAtLeast(1))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _currentIndex = MutableStateFlow(0)

    val currentQuote: StateFlow<Quote?> =
        combine(quotes, _shuffledIndexes, _currentIndex) { list, shuffled, index ->
            list.getOrNull(shuffled.getOrElse(index) { 0 })
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    init {
        viewModelScope.launch {
            quotes.collect { quoteList ->
                if (quoteList.isNotEmpty() && _shuffledIndexes.value.isEmpty()) {
                    _shuffledIndexes.value = quoteList.indices.shuffled()
                }
            }
        }
    }

    fun nextQuote() {
        val maxIndex = _shuffledIndexes.value.size.coerceAtLeast(1)
        val next = (_currentIndex.value + 1) % maxIndex
        _currentIndex.value = next
    }

    fun previousQuote() {
        val maxIndex = _shuffledIndexes.value.size.coerceAtLeast(1)
        val prev = (_currentIndex.value - 1 + maxIndex) % maxIndex
        _currentIndex.value = prev
    }

    fun setFavourite(quote: Quote) = viewModelScope.launch {
        repository.updateLiked(quote.id, true)
    }

    fun toggleFavourite(quote: Quote) = viewModelScope.launch {
        repository.updateLiked(quote.id, !quote.isLiked)
    }
}
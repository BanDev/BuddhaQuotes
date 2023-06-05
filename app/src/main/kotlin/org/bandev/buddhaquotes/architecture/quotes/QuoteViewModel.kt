package org.bandev.buddhaquotes.architecture.quotes

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.items.QuoteItem

@SuppressLint("NullSafeMutableLiveData")
@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository
) : ViewModel() {
    private var _selectedQuote: MutableLiveData<QuoteItem> = MutableLiveData(QuoteItem())
    var selectedQuote: LiveData<QuoteItem> = _selectedQuote

    private var _allQuotes: MutableLiveData<List<QuoteItem>> = MutableLiveData(emptyList())
    var allQuotes: LiveData<List<QuoteItem>> = _allQuotes

    private var _dailyQuote: MutableLiveData<QuoteItem> = MutableLiveData(QuoteItem())
    var dailyQuote: LiveData<QuoteItem> = _dailyQuote

    init {
        viewModelScope.launch {
            _selectedQuote.postValue(getRandom())
            _dailyQuote.postValue(getDaily())
            _allQuotes.postValue(getAll())
        }
    }

    fun setNewQuote(quote: QuoteItem) {
        _selectedQuote.postValue(quote)
    }

    /** Get one singular quote */
    suspend fun get(id: Int): QuoteItem = quotesRepository.get(id)

    /** Get all quotes */
    private suspend fun getAll(): List<QuoteItem> = quotesRepository.getAll()

    /** Get a random quote */
    private suspend fun getRandom(): QuoteItem = get((0 until QuoteStore.quotesWithSources.size).random())

    /** Get the quote of the day */
    private suspend fun getDaily(): QuoteItem {
        return get(Calendar.getInstance().get(Calendar.DAY_OF_YEAR) % QuoteStore.quotesWithSources.size)
    }

    fun setLiked(isLiked: Boolean) {
        _selectedQuote.postValue(_selectedQuote.value?.copy(isLiked = isLiked))
    }
}

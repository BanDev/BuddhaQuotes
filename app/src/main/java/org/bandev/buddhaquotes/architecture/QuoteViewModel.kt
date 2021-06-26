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

    /** Get all Quotes */
    fun getAll(after: (quote: List<Quote>) -> Unit) {
        runBlocking {
            after(repository.getAll())
        }
    }

}
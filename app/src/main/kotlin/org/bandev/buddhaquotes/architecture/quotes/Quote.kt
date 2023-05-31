package org.bandev.buddhaquotes.architecture.quotes

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.bandev.buddhaquotes.items.QuoteItem

/**
 * A quote in the application.
 *
 * Each quote has a unique id, which is
 * used to get the translated quote in the
 * repository layer.
 *
 * Each quote also has a liked boolean, it
 * makes more sense to attempt it this way
 * rather than looking it up with a WHERE
 * search everytime we need to display a
 * red heart.
 */
@Entity(tableName = "quote")
data class Quote(
    @PrimaryKey(autoGenerate = true) val id: Int, // The unique quote id
) {
    fun toUIQuote() = QuoteItem(id, QuoteStore.quotesWithSources.keys.toList()[id - 1])
}

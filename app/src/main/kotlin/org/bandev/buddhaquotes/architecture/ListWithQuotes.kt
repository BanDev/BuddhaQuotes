package org.bandev.buddhaquotes.architecture

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import org.bandev.buddhaquotes.architecture.listquotes.ListQuote
import org.bandev.buddhaquotes.architecture.lists.ListOfQuotes
import org.bandev.buddhaquotes.architecture.quotes.Quote

data class ListWithQuotes(
    @Embedded val listOfQuotes: ListOfQuotes,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = ListQuote::class,
            parentColumn = "list_id",
            entityColumn = "quote_id"
        )
    )
    val quotes: List<Quote>
)

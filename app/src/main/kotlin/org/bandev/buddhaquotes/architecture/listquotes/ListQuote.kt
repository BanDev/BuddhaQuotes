package org.bandev.buddhaquotes.architecture.listquotes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.bandev.buddhaquotes.architecture.lists.ListOfQuotes
import org.bandev.buddhaquotes.architecture.quotes.Quote

@Entity(
    tableName = "list_quote",
    primaryKeys = ["list_id", "quote_id"],
    foreignKeys = [
        ForeignKey(
            entity = ListOfQuotes::class,
            parentColumns = ["id"],
            childColumns = ["list_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Quote::class,
            parentColumns = ["id"],
            childColumns = ["quote_id"],
            onDelete = CASCADE
        )
    ]
)
data class ListQuote(
    @ColumnInfo(name = "list_id", index = true) val listId: Int,
    @ColumnInfo(name = "quote_id", index = true) val quoteId: Int
)

package org.bandev.buddhaquotes.database

import androidx.room.*

@Entity(tableName = "lists")
data class QuoteList(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "system") val system: Int
)

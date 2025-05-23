package org.bandev.buddhaquotes.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    @Embedded val source: Source,
    @ColumnInfo(name = "is_liked", defaultValue = "0") val isLiked: Boolean = false
)

data class Source(
    val body: String,
    @ColumnInfo(name = "full_quote") val fullQuote: String? = null,
    val url: String
)

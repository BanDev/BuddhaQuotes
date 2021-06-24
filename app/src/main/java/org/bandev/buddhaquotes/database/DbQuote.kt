package org.bandev.buddhaquotes.database

import androidx.room.*

@Entity(tableName = "quotes")
data class DbQuote(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "like", defaultValue = "0") val like: Boolean,
)

package org.bandev.buddhaquotes.architecture.lists

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list")
data class ListOfQuotes(
    @PrimaryKey(autoGenerate = true) val id: Int, // The unique list id
    @ColumnInfo(typeAffinity = 2) val title: String, // The title of the list
    @ColumnInfo(defaultValue = "0") val icon: Int // The chosen icon of the list
)
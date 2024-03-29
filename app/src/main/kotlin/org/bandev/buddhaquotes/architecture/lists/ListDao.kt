package org.bandev.buddhaquotes.architecture.lists

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import org.bandev.buddhaquotes.architecture.ListWithQuotes

/**
     * Interact with the list table of the
     * database to allow for adding or removing
     * list records and renaming and updating
     * some UI elements like title and icon.
     */

    @Dao
    interface ListDao {
        /** Get just one list */
        @Query("SELECT * FROM list WHERE id = :id")
        suspend fun get(id: Int): ListOfQuotes

        /** Get every single list */
        @Query("SELECT * FROM list")
        fun getAll(): Flow<List<ListOfQuotes>>

        /** Get the latest list in the db */
        @Query("SELECT * FROM list ORDER BY ID DESC LIMIT 1")
        suspend fun getLast(): ListOfQuotes

        /** Rename a list */
        @Query("UPDATE list SET `title` = :name WHERE id = :id")
        suspend fun rename(id: Int, name: String)

        /** Update a list's icon */
        @Query("UPDATE list SET `icon` = :icon WHERE id = :id")
        suspend fun updateIcon(id: Int, icon: Int)

        /** New empty list */
        @Query("INSERT INTO list (`title`, `icon`) VALUES (:title, 1)")
        suspend fun create(title: String)

        /** Delete a list */
        @Query("DELETE FROM list WHERE id = :id")
        suspend fun delete(id: Int)

        /** Count lists */
        @Query("SELECT COUNT(id) FROM list")
        suspend fun count(): Int

        @Transaction
        @Query("SELECT * FROM list WHERE id = :id")
        fun getListWithQuotes(id: Int): LiveData<ListWithQuotes>
    }
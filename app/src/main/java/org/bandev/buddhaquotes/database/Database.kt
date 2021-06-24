package org.bandev.buddhaquotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.bandev.buddhaquotes.core.Quotes2

class Database(var context: Context) {

    private var db: RoomDatabase
    var quote: QuoteDao
    private var list: QuoteListDao

    init {
        db = Room.databaseBuilder(context, AppDb::class.java, "db")
            .createFromAsset("db.db")
            .allowMainThreadQueries()
            .build()
        quote = (db as AppDb).quote()
        list = (db as AppDb).list()
    }

    fun getQuote(id: Int): Quote {
        val dbQt = quote.get(id)
        return Quote(dbQt.id, Quotes2.getQuote(dbQt.id, context), dbQt.like)
    }

    fun getQuotes(): MutableList<Quote> {
        val qts = mutableListOf<Quote>()
        val dbQts = quote.getAll()
        for ((id, like) in dbQts) {
            qts.add(Quote(id, Quotes2.getQuote(id, context), like))
        }
        return qts
    }

    @Database(
        version = 1,
        entities = [DbQuote::class, QuoteList::class]
    )
    abstract class AppDb : RoomDatabase() {
        abstract fun quote(): QuoteDao
        abstract fun list(): QuoteListDao
    }

    data class Quote(val id: Int, val quote: String, val liked: Boolean)
}
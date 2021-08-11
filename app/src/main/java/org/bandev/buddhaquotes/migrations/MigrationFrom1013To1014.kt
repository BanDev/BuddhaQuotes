package org.bandev.buddhaquotes.migrations

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Looper
import android.widget.Toast
import androidx.preference.PreferenceManager
import org.bandev.buddhaquotes.architecture.ViewModel
import org.bandev.buddhaquotes.items.Quote

class MigrationFrom1013To1014(private val ctx: Context, application: Application) {

    private val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx)
    private val e: SharedPreferences.Editor = sp.edit()

    val l = Lists()
    private lateinit var discoveredLists: List<String>
    private var viewModel: ViewModel = ViewModel(application)
    private lateinit var quotes: MutableList<Quote>

    fun migrate() {
        if(sp.getBoolean("migrated_1013_1014", false)) {
            out("Already migrated 1013 -> 1014")
            return
        }

        out("Migration started")

        viewModel.Quotes().getAll {
            quotes = it

            Looper.prepare()

            discoveredLists = l.getMasterList(ctx)

            if(discoveredLists[discoveredLists.lastIndex] == "Favourites") {
                out("No lists discovered, filling with artificial lists")
                l.newList("Test list 1", ctx)
                l.addToList("I will not look at another’s bowl intent on finding fault: a training to be observed", "Test list 1", ctx)
                l.addToList("If anything is worth doing, do it with all your heart", "Test list 1", ctx)
                l.addToList("I will not look at another’s bowl intent on finding fault: a training to be observed", "Favourites", ctx)
                discoveredLists = l.getMasterList(ctx)
            }

            for(list in discoveredLists) {
                if(list == "Favourites") migrateFav()
                else migrateList(list)
            }

            out("Migration complete")
            e.putBoolean("migrated_1013_1014", true)
            e.apply()
        }

    }

    private fun migrateFav() {
        out("Begun migrating FAV")
        for(quote in l.getQuotes("Favourites", ctx)) {
            quotes.forEach {
                if (ctx.getString(it.resource) == quote) {
                    viewModel.Quotes().setLike(it.id, true)
                    out("Quote: $quote has been restored into FAV")
                }
            }
        }
    }

    private fun migrateList(name: String) {
        out("Begun migrating $name")
        viewModel.Lists().new(name) { list ->

            for(quote in l.getQuotes(name, ctx)) {
                quotes.forEach {
                    if (ctx.getString(it.resource) == quote) {
                        viewModel.ListQuotes().addTo(list.id, it.id)
                        out("Quote: $quote has been restored into $name")
                    }
                }
            }
        }
    }

    fun out(string: String) {
        Toast.makeText(ctx, string, Toast.LENGTH_SHORT).show()
    }

}
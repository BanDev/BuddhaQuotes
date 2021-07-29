/**

Buddha Quotes
Copyright (C) 2021  BanDev

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

package org.bandev.buddhaquotes.activities

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.QuoteAdapter
import org.bandev.buddhaquotes.architecture.ViewModel
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.custom.AddQuoteSheet
import org.bandev.buddhaquotes.custom.CustomiseListSheet
import org.bandev.buddhaquotes.databinding.ActivityListBinding
import org.bandev.buddhaquotes.items.Quote


/**
 * The activity where the user can see all the
 * quotes they have in their lists and make some
 * list adjustements.
 *
 * @date 28/7/21
 */

class ListActivity : LocalizationActivity(), QuoteAdapter.Listener {

    private lateinit var binding: ActivityListBinding
    private lateinit var menu: Menu
    private lateinit var vm: ViewModel
    private lateinit var quotes: MutableList<Quote>
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Bind to view
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Styling things
        setAccentColour(this)
        with(window) {
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
            setDarkStatusIcons()
        }
        Animations().toolbarShadowScroll(binding.recycler, binding.appBar)
        setDecorFitsSystemWindows(window, false)
        binding.add.backgroundTintList =
            ColorStateList.valueOf(resolveColorAttr(R.attr.colorAccent))

        // Do insets
        binding.toolbar.applyInsets(STATUSBARS)
        binding.add.applyInsets(NAVIGATIONBARS)

        // Create or get ViewModel
        vm = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application)
            .create(ViewModel::class.java)

        // Setup toolbar
        setSupportActionBar(binding.toolbar)

        // On back button pressed
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        // On add button pressed
        binding.add.setOnClickListener { add() }

        // Get id from intent extras
        id = (intent.extras ?: return).getInt("id")

        // Get metadata about the list from db
        vm.Lists().get(id) { list ->
            binding.toolbar.title = list.title
        }

        // Get quotes from the list
        vm.ListQuotes().getFrom(id) {
            quotes = it
            binding.recycler.adapter = QuoteAdapter(quotes, this, id)
            binding.recycler.layoutManager = LinearLayoutManager(this)
            binding.recycler.setHasFixedSize(false)
            checkLength()
        }
    }

    fun add() {
        // Show a sheet with all avaliable quotes
        vm.Quotes().getAll {
            AddQuoteSheet().show(this) {
                it.removeAll(quotes)
                displayToolbar(false)
                displayHandle(true)
                with(it)
                onPositive { quote ->
                    Feedback.confirm(binding.root)
                    onQuoteAdded(quote)
                }
            }
        }
    }

    fun settings() {
        // Show a sheet with some settings
        menu.findItem(R.id.tune).isEnabled = false
        CustomiseListSheet().show(this, application) {
            displayToolbar(false)
            displayHandle(true)
            displayPositiveButton(false)
            displayNegativeButton(false)
            attachVariables(vm, this@ListActivity.id)
            onClose { menu.findItem(R.id.tune).isEnabled = true }
        }
    }

    private fun checkLength() {
        if (quotes.isEmpty()) binding.empty.visibility = View.VISIBLE else View.GONE
    }

    override fun onQuoteLiked(quote: Quote) {
        // Like the quote in the db
        vm.Quotes().setLike(quote.id, true)
    }

    override fun onQuoteUnliked(quote: Quote) {
        // Unlike the quote in the db
        vm.Quotes().setLike(quote.id, false)
        if (id == 0) quotes.remove(quote)
        checkLength()
    }

    override fun onQuoteRemoved(quote: Quote) {
        // Remove the quote from the db
        vm.ListQuotes().removeFrom(id, quote)
        quotes.remove(quote)
        checkLength()
    }

    private fun onQuoteAdded(quote: Quote) {
        // Add a quote to the list
        if (id == 0) quote.liked = true
        vm.ListQuotes().addTo(id, quote)
        quotes.add(quote)
        binding.recycler.adapter?.notifyItemInserted(quotes.find(quote))
        binding.empty.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(_menu: Menu?): Boolean {
        // Inflate custom menu
        menuInflater.inflate(R.menu.list_activity_menu, _menu)
        menu = _menu!!
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle menu clicks
        when (item.itemId) {
            R.id.tune -> settings()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}

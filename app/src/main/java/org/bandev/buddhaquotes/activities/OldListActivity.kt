///**
//
//Buddha Quotes
//Copyright (C) 2021  BanDev
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <https://www.gnu.org/licenses/>.
//
// */
//
//package org.bandev.buddhaquotes.activities
//
//import android.graphics.Color
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuItem
//import android.view.View
//import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
//import androidx.lifecycle.ViewModelProvider
//import com.akexorcist.localizationactivity.ui.LocalizationActivity
//import com.google.android.material.snackbar.Snackbar
//import dev.chrisbanes.insetter.applyInsetter
//import org.bandev.buddhaquotes.R
//import org.bandev.buddhaquotes.adapters.QuoteAdapter
//import org.bandev.buddhaquotes.core.*
//import org.bandev.buddhaquotes.custom.CustomiseListSheet
//import org.bandev.buddhaquotes.databinding.ActivityListBinding
//import org.bandev.buddhaquotes.items.Quote
//import org.bandev.buddhaquotes.architecture.ViewModel
//
///**
// * The activity where the user can see all the quotes they have in their
// * lists
// */
//
//class OldListActivity : LocalizationActivity(), QuoteAdapter.Listener {
//
//    private lateinit var binding: ActivityListBinding
//    private var toolbarMenu: Menu? = null
//    private lateinit var viewModel: ViewModel
//    private lateinit var quotes: MutableList<Quote>
//    private var listId: Int = 0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setAccentColour(this)
//
//        with(window) {
//            statusBarColor = Color.TRANSPARENT
//            setNavigationBarColourDefault()
//            setDarkStatusIcons()
//        }
//        setDecorFitsSystemWindows(window, false)
//
//        // Setup view binding
//        binding = ActivityListBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//            .create(ViewModel::class.java)
//
//        // Setup toolbar
//        setSupportActionBar(binding.toolbar)
//        with(binding.toolbar) {
//            setNavigationOnClickListener { onBackPressed() }
//            applyInsetter {
//                type(statusBars = true) {
//                    margin(top = true)
//                }
//            }
//        }
//        with(binding.addQuote) {
//            applyInsetter {
//                type(navigationBars = true) {
//                    margin(bottom = true)
//                }
//            }
//            backgroundTintList = ColorStateList.valueOf(resolveColorAttr(R.attr.colorAccent))
//            setOnClickListener { view ->
//                Feedback.virtualKey(view)
//                viewModel.Quotes().getAll {
//                    val quotesFull = it.toMutableList()
//                    quotesFull.removeAll(quotes)
//                    AddQuoteSheet().show(this@OldListActivity) {
//                        displayToolbar(false)
//                        displayHandle(true)
//                        with(quotesFull)
//                        onPositive { quote ->
//                            Feedback.confirm(binding.root)
//                            quoteSelected(quote)
//                        }
//                    }
//                }
//            }
//        }
//
//        viewModel.Lists().get((intent.extras ?: return).getInt("id")) {
//            listId = it.id
//            binding.toolbar.title = it.title
//            setupRecycler(it.id)
//        }
//    }
//
//    private fun setupRecycler(id: Int) {
//        viewModel.ListQuotes().getFrom(id) {
//            quotes = it
//            with(binding) {
//                with(quotesRecycler) {
//                    applyInsetter {
//                        type(navigationBars = true) {
//                            margin(bottom = true)
//                        }
//                    }
//                    layoutManager = LinearLayoutManager(context)
//                    adapter = QuoteRecycler(quotes, this@OldListActivity, listId)
//                    setHasFixedSize(false)
//                }
//            }
//            checkLength(quotes)
//        }
//    }
//
//    private fun checkLength(list: MutableList<Quote>) {
//        if (list.isEmpty()) binding.noQuotesText.visibility = View.VISIBLE else View.GONE
//    }
//
//    override fun like(quote: Quote) {
//        viewModel.Quotes().setLike(quote.id, true)
//        Snackbar.make(binding.root, "iked", Snackbar.LENGTH_SHORT).show()
//    }
//
//    override fun unlike(quote: Quote) {
//        if (listId == 0) return bin(quote)
//        viewModel.Quotes().setLike(quote.id, false)
//        Snackbar.make(binding.root, "Unliked", Snackbar.LENGTH_SHORT).show()
//    }
//
//    override fun bin(quote: Quote) {
//        viewModel.ListQuotes().removeFrom(listId, quote)
//        Snackbar.make(binding.root, "Removed", Snackbar.LENGTH_SHORT).show()
//        binding.quotesRecycler.adapter?.notifyItemRemoved(quotes.find(quote))
//        quotes.remove(quote)
//        checkLength(quotes)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.list_activity_menu, menu)
//        toolbarMenu = menu
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.tune -> showSettings()
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    private fun quoteSelected(quote: Quote) {
//        if (listId == 0) quote.liked = true
//        viewModel.ListQuotes().addTo(listId, quote)
//        quotes.add(quote)
//        binding.quotesRecycler.adapter?.notifyItemInserted(quotes.find(quote))
//        binding.noQuotesText.visibility = View.GONE
//    }
//
//    private fun showSettings(): Boolean {
//        toolbarMenu?.findItem(R.id.tune)?.isEnabled = false
//        CustomiseListSheet().show(this, application) {
//            displayToolbar(false)
//            displayHandle(true)
//            displayPositiveButton(false)
//            displayNegativeButton(false)
//            //attachVariables(, listId)
//            onClose { toolbarMenu?.findItem(R.id.tune)?.isEnabled = true }
//        }
//        return true
//    }
//}

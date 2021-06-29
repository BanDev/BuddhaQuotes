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

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.QuoteListRecycler
import org.bandev.buddhaquotes.adapters.QuoteRecycler
import org.bandev.buddhaquotes.architecture.ListViewModel
import org.bandev.buddhaquotes.architecture.QuoteViewModel
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.custom.AddQuoteSheet
import org.bandev.buddhaquotes.databinding.ActivityScrollingBinding
import org.bandev.buddhaquotes.items.Quote
import org.bandev.buddhaquotes.items.QuoteItem
import org.bandev.buddhaquotes.items.QuoteListWithQuotes
import java.util.*
import kotlin.collections.ArrayList

/**
 * The activity where the user can see all the quotes they have in their
 * lists
 */
class ListActivity : LocalizationActivity(), QuoteRecycler.Listener {

    private lateinit var binding: ActivityScrollingBinding
    private lateinit var scrollingList: ArrayList<QuoteItem>
    private lateinit var recyclerAdapter: QuoteRecycler
    private lateinit var prefList: List<Int>
    private var toolbarMenu: Menu? = null
    private lateinit var quoteModel: QuoteViewModel
    private lateinit var listModel: ListViewModel
    private lateinit var icons: Icons


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAccentColour(this)
        window.setStatusBarAsAccentColour(this)
        window.setNavigationBarColourDefault(this)

        // Setup view binding
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quoteModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(QuoteViewModel::class.java)

        listModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(ListViewModel::class.java)

        icons = Icons(this)

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        with(binding.toolbar) {
            navigationIcon = icons.back()
            setBackgroundColor(toolbarColour(context))
            setNavigationOnClickListener { onBackPressed() }
        }

        setupRecycler((intent.extras ?: return).getInt("id"))
    }

    private fun setupRecycler(id: Int) {
        listModel.get(id) {
            with(binding.quotesRecycler) {
                layoutManager = LinearLayoutManager(context)
                adapter = QuoteRecycler(it.quotes, this@ListActivity, this@ListActivity)
                setHasFixedSize(false)
            }
            binding.toolbar.title = it.title
            checkLength(it)
        }
    }

    private fun checkLength(list: QuoteListWithQuotes) {
        if (list.quotes.isEmpty()) binding.noQuotesText.visibility = View.VISIBLE
    }

    override fun select(quote: Quote) {
        TODO("Not yet implemented")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        toolbarMenu = menu
        menu?.findItem(R.id.add)?.icon = icons.add()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                toolbarMenu?.findItem(R.id.add)?.isEnabled = false
                quoteModel.getAll { quotes ->
                    AddQuoteSheet().show(this) {
                        displayToolbar(false)
                        displayHandle(true)
                        with(quotes)
                        onPositive { quote ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                            } else {
                                binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            }
                            Toast.makeText(context, quote, Toast.LENGTH_SHORT).show()
                        }
                        onClose { toolbarMenu?.findItem(R.id.add)?.isEnabled = true }
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

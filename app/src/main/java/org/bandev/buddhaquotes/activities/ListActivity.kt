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
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.maxkeppeler.sheets.core.IconButton
import me.kosert.flowbus.GlobalBus
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.QuoteAdapter
import org.bandev.buddhaquotes.architecture.ViewModel
import org.bandev.buddhaquotes.bus.Message
import org.bandev.buddhaquotes.bus.MessageType
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.core.Accent.setAccentColour
import org.bandev.buddhaquotes.core.Bars.updateNavbarColour
import org.bandev.buddhaquotes.core.Insets.applyInsets
import org.bandev.buddhaquotes.custom.AddQuoteSheet
import org.bandev.buddhaquotes.custom.ListOptionsSheet
import org.bandev.buddhaquotes.databinding.ActivityListBinding
import org.bandev.buddhaquotes.items.List
import org.bandev.buddhaquotes.items.Quote

/**
 * The activity where the user can see all the
 * quotes they have in their lists and make some
 * list adjustments.
 *
 * @date 28/7/21
 */

class ListActivity : LocalizationActivity(), QuoteAdapter.Listener {

    private lateinit var binding: ActivityListBinding
    private lateinit var vm: ViewModel
    private lateinit var list: List
    private lateinit var quotes: MutableList<Quote>
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Bind to view
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Styling things
        setAccentColour(Prefs(this).Settings().accent)
        with(window) {
            statusBarColor = Color.TRANSPARENT
            updateNavbarColour()
            setDarkStatusIcons()
        }
        Animations().toolbarShadowScroll(binding.recycler, binding.appBar)
        setDecorFitsSystemWindows(window, false)

        // Create or get ViewModel
        vm = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application)
            .create(ViewModel::class.java)

        // On back button pressed
        binding.toolbar.apply {
            setSupportActionBar(this)
            applyInsets(InsetType.STATUS_BARS)
            setNavigationOnClickListener { onBackPressed() }
        }

        binding.add.apply {
            backgroundTintList = ColorStateList.valueOf(resolveColorAttr(R.attr.colorAccent))
            applyInsets(InsetType.NAVIGATION_BARS)
            setOnClickListener {
                Feedback.virtualKey(it)
                it.isEnabled = false
                vm.Quotes().getAll { mutableList ->
                    mutableList.removeAll(quotes)
                    AddQuoteSheet().show(context) {
                        displayToolbar(false)
                        displayHandle(true)
                        with(mutableList)
                        onPositive { quote ->
                            Feedback.confirm(binding.root)
                            onQuoteAdded(quote)
                        }
                        onClose { it.isEnabled = true }
                    }
                }
            }
        }

        // Get id from intent extras
        id = (intent.extras ?: return).getInt("id")

        // Get metadata about the list from db
        vm.Lists().get(id) {
            list = it
            binding.toolbar.title = if (list.id == 0) getString(R.string.favourites) else list.title

            // Get quotes from the list
            vm.ListQuotes().getFrom(id) { quotes ->
                this.quotes = quotes
                binding.recycler.apply {
                    adapter = QuoteAdapter(quotes, this@ListActivity, list.id)
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(false)
                }
                checkLength()
            }

        }

        binding.recycler.applyInsets(InsetType.NAVIGATION_BARS, InsetOption.PADDING)
    }

    private fun checkLength() {
        if (quotes.isEmpty()) binding.empty.visibility = View.VISIBLE else View.GONE
    }

    override fun onQuoteLiked(quote: Quote) {
        // Like the quote in the db
        vm.Quotes().setLike(quote.id, true)
        GlobalBus.post(Message(MessageType.LIKE_UPDATE, 1))
    }

    override fun onQuoteUnliked(quote: Quote) {
        GlobalBus.post(Message(MessageType.LIKE_UPDATE, -1))
        // Unlike the quote in the db
        vm.Quotes().setLike(quote.id, false)
        if (id == 0) {
            quotes.remove(quote)
            list.count--
            GlobalBus.post(Message(MessageType.UPDATE_LIST, list))
        }
        checkLength()
    }

    override fun onQuoteRemoved(quote: Quote) {
        // Remove the quote from the db
        vm.ListQuotes().removeFrom(id, quote)
        quotes.remove(quote)
        list.count--
        GlobalBus.post(Message(MessageType.UPDATE_LIST, list))
        checkLength()
    }

    private fun onQuoteAdded(quote: Quote) {
        // Add a quote to the list
        if (id == 0) quote.liked = true
        vm.ListQuotes().addTo(id, quote)
        quotes.add(quote)
        list.count++
        binding.recycler.adapter?.notifyItemInserted(quotes.find(quote))
        GlobalBus.post(Message(MessageType.UPDATE_LIST, list))
        binding.empty.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.tune -> {
                item.isEnabled = false
                ListOptionsSheet().show(this, application) {
                    title(R.string.list_icon)
                    closeIconButton(IconButton(R.drawable.ic_down_arrow))
                    displayPositiveButton(false)
                    displayNegativeButton(false)
                    attachVariables(vm, this@ListActivity.id)
                    onClose { item.isEnabled = true }
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}

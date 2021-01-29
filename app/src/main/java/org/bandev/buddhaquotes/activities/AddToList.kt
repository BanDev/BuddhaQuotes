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

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.Menu
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.AddQuoteRecycler
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.AddlistContentBinding
import org.bandev.buddhaquotes.items.AddQuoteItem
import java.util.*


class AddToList : AppCompatActivity(), AddQuoteRecycler.ClickListener {

    private lateinit var binding: AddlistContentBinding
    private lateinit var rAdapter: AddQuoteRecycler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set theme, navigation bar and language
        Colours().setAccentColour(this, window, resources)
        Compatibility().setNavigationBarColourDefault(this, window, resources)
        Languages(baseContext).setLanguage()

        // Setup view binding
        binding = AddlistContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        rAdapter = AddQuoteRecycler(genList(), this@AddToList)

        with(binding.recycler) {
            adapter = rAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    override fun onClick(quote: String) {
        val list = (intent.extras ?: return).getString("list").toString()
        val lists = Lists()
        if (!lists.queryInList(quote, list, this)) {
            lists.addToList(quote, list, this)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                binding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
            } else {
                binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            }

            val intent2 = Intent(this, ScrollingActivity::class.java)
            intent2.putExtra("list", list)
            startActivity(intent2)
            finish()

        } else {
            Snackbar.make(binding.root, "This quote is already in the list!", Snackbar.LENGTH_SHORT)
                .show()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                binding.root.performHapticFeedback(HapticFeedbackConstants.REJECT)
            } else {
                binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            }
        }
    }

    private fun genList(): ArrayList<AddQuoteItem> {
        val list = ArrayList<AddQuoteItem>()
        val max = 237
        var i = 1
        while (i != max) {
            val quote = Quotes().getQuote(i, this)
            list.add(AddQuoteItem(quote))
            i++
        }

        return list
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val searchItem = menu!!.findItem(R.id.appSearchBar)
        val searchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                rAdapter.filter.filter(newText)
                return false
            }
        })
        return true
    }

    override fun onBackPressed() {
        val list = (intent.getStringExtra("list") ?: return).toString()
        val intent2 = Intent(this, ScrollingActivity::class.java)
        val mBundle = Bundle()
        mBundle.putString("list", list)
        intent2.putExtras(mBundle)
        this.startActivity(intent2)
        finish()
    }
}

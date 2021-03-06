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
import android.graphics.Color
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.QuoteRecycler
import org.bandev.buddhaquotes.core.*
import org.bandev.buddhaquotes.databinding.ActivityScrollingBinding
import org.bandev.buddhaquotes.items.QuoteItem
import java.util.*
import kotlin.collections.ArrayList

/**
 * The activity where the user can see all the quotes they have in their
 * lists
 */
class ScrollingActivity : AppCompatActivity(), QuoteRecycler.OnItemClickFinder {

    private lateinit var binding: ActivityScrollingBinding
    private lateinit var scrollingList: ArrayList<QuoteItem>
    private lateinit var adapter: QuoteRecycler
    private lateinit var prefList: List<Int>
    private var listTmp: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Colours().setAccentColour(this, window, resources)
        Compatibility().setNavigationBarColourDefault(this, window, resources)
        Languages(baseContext).setLanguage()

        // Setup view binding
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val returnDrawable =
            IconicsDrawable(this, RoundedGoogleMaterial.Icon.gmr_arrow_back).apply {
                colorInt = Color.WHITE
                sizeDp = 16
            }
        val list = (intent.getStringExtra("list") ?: return).toString()
        listTmp = list
        val pref = getSharedPreferences("ListV2", 0)
        val prefString = pref.getString(listTmp, "")
        val prefListTmp: MutableList<String> = (prefString ?: return).split("//").toMutableList()
        prefListTmp.remove("null")

        prefList = ListsV2(this).getList(listTmp)
        scrollingList = generateDummyList(prefList.size)
        adapter = QuoteRecycler(scrollingList, this@ScrollingActivity)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = if (listTmp == "Favourites") {
            getString(R.string.favourites)
        } else listTmp

        (supportActionBar ?: return).setDisplayShowTitleEnabled(true)
        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)

        binding.toolbar.navigationIcon = returnDrawable
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.recyclerView.adapter = adapter

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val addIcon = IconicsDrawable(this, RoundedGoogleMaterial.Icon.gmr_add).apply {
            colorInt = Color.WHITE
            sizeDp = 16
        }
        menuInflater.inflate(R.menu.add_menu, menu)
        menu?.findItem(R.id.add)?.icon = addIcon
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                val intent2 = Intent(this, AddToList::class.java)
                intent2.putExtra("list", listTmp)
                startActivity(intent2)
                finish()
                overridePendingTransition(
                    R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onLikeClick(position: Int, text: String) {
        if (listTmp != "Favourites") {
            val clickedItem = scrollingList[position]

            if (clickedItem.resource == R.drawable.heart_full_red) {
                clickedItem.resource = R.drawable.like

                ListsV2(this).removeFromList(Quotes().getFromString(text, this), "Favourites")
            } else {
                clickedItem.resource = R.drawable.heart_full_red

                ListsV2(this).addToList(Quotes().getFromString(text, this), "Favourites")
            }
            binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            adapter.notifyItemChanged(position)
        }
    }

    override fun onCardClick(position: Int) {
        val clickedItem = scrollingList[position]
        binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            val text = clickedItem.quote + "\n\n" + getString(R.string.attribution_buddha)
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onBinClick(position: Int, text: String) {
        binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)

        scrollingList.removeAt(position)
        adapter.notifyItemRemoved(position)

        ListsV2(this).removeFromList(Quotes().getFromString(text, this), listTmp)
    }

    private fun generateDummyList(max: Int): ArrayList<QuoteItem> {

        val list = ArrayList<QuoteItem>()
        var item: QuoteItem

        val favs = ListsV2(this).getList("Favourites")

        for (id in prefList) {
            val special = listTmp == "Favourites"
            if (id == -1) continue

            val drawable = if (favs.contains(id)) {
                R.drawable.heart_full_red
            } else {
                R.drawable.like
            }

            item = QuoteItem(
                Quotes().getQuote(id, this),
                drawable,
                special
            )
            list += item
        }
        return list
    }

    override fun onBackPressed() {
        finish()
    }
}

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
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.QuoteRecycler
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages
import org.bandev.buddhaquotes.databinding.ActivityScrollingBinding
import org.bandev.buddhaquotes.items.QuoteItem
import java.util.*
import kotlin.collections.ArrayList

class ScrollingActivity : AppCompatActivity(), QuoteRecycler.OnItemClickFinder {

    private lateinit var binding: ActivityScrollingBinding
    private lateinit var scrollingList: ArrayList<QuoteItem>
    private lateinit var adapter: QuoteRecycler
    private lateinit var prefList: List<String>
    private var listTmp: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Colours().setAccentColour(this, window, resources)
        Compatibility().setNavigationBarColourDefault(this, window, resources)
        Languages(baseContext).setLanguage()

        // Setup view binding
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if ((intent.extras ?: return).getBoolean("duplicate", false)) {
            Snackbar.make(binding.scrolling, "Already in list!", Snackbar.LENGTH_SHORT).show()
        }

        val back = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        val list = (intent.getStringExtra("list") ?: return).toString()
        listTmp = list
        val pref = getSharedPreferences("List_system", 0)
        val prefString = pref.getString(listTmp, "")
        val prefListTmp: MutableList<String> = (prefString ?: return).split("//").toMutableList()
        prefListTmp.remove("null")

        prefList = prefListTmp
        scrollingList = generateDummyList(prefList.size)
        adapter = QuoteRecycler(scrollingList, this@ScrollingActivity)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = listTmp

        (supportActionBar ?: return).setDisplayShowTitleEnabled(true)
        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)

        binding.toolbar.navigationIcon = back
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
        menuInflater.inflate(R.menu.add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                val intent2 = Intent(this, AddToList::class.java)
                val b = Bundle()
                b.putString("list", listTmp) // Your id
                intent2.putExtras(b)
                this.startActivity(intent2)
                finish()
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

                val pref = getSharedPreferences("List_system", 0)
                val editor = pref.edit()
                val listArr = pref.getString("Favourites", "")
                val listArrTemp: MutableList<String> =
                    (listArr?.split("//") ?: return).toMutableList()
                val listArrFinal = LinkedList(listArrTemp)
                listArrFinal.remove(text)
                val stringOut = listArrFinal.joinToString(separator = "//")
                editor.putString("Favourites", stringOut)
                editor.apply()
            } else {
                clickedItem.resource = R.drawable.heart_full_red

                val pref = getSharedPreferences("List_system", 0)
                val editor = pref.edit()
                val listArr = pref.getString("Favourites", "")
                val listArrTemp: MutableList<String> =
                    (listArr?.split("//") ?: return).toMutableList()
                val listArrFinal = LinkedList(listArrTemp)
                listArrFinal.push(text)
                val stringOut = listArrFinal.joinToString(separator = "//")
                editor.putString("Favourites", stringOut)
                editor.apply()
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

            val text = clickedItem.quote + "\n\n~Buddha"

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

        val pref = getSharedPreferences("List_system", 0)
        val editor = pref.edit()
        val listArr = pref.getString(listTmp, "")
        val listArrTemp: MutableList<String> =
            (listArr?.split("//") ?: return).toMutableList()
        val listArrFinal = LinkedList(listArrTemp)
        listArrFinal.remove(text)
        val stringOut = listArrFinal.joinToString(separator = "//")
        editor.putString(listTmp, stringOut)
        editor.apply()
    }

    private fun generateDummyList(max: Int): ArrayList<QuoteItem> {

        val list = ArrayList<QuoteItem>()

        var i = 0
        var item: QuoteItem

        val pref = getSharedPreferences("List_system", 0)
        val listArr = pref.getString("Favourites", "")
        val listArrTemp: MutableList<String> = listArr?.split("//")!!.toMutableList()
        val listArrFinal = LinkedList(listArrTemp)

        while (i != max) {
            var special = false
            if (listTmp == "Favourites") {
                special = true
            }
            if ((listArrFinal as MutableList<String?>).contains(prefList[i])) {
                if (prefList[i] != "") {
                    item = QuoteItem(prefList[i], R.drawable.heart_full_red, special)
                    list += item
                }
            } else {
                item = QuoteItem(prefList[i], R.drawable.like, special)
                list += item
            }

            i++
        }
        return list
    }

    override fun onBackPressed() {
        startActivity(Intent(this@ScrollingActivity, Main::class.java))
        finish()
        overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
    }
}

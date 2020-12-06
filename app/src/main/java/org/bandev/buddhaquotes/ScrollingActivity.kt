package org.bandev.buddhaquotes

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages
import org.bandev.buddhaquotes.databinding.ActivityScrollingBinding
import java.util.*
import kotlin.collections.ArrayList

class ScrollingActivity : AppCompatActivity(), ScrollingAdapter.OnItemClickFinder {

    private lateinit var binding: ActivityScrollingBinding
    private lateinit var scrollingList: ArrayList<ExampleItem>
    private lateinit var adapter: ScrollingAdapter
    private lateinit var prefList: List<String>
    private var listTmp: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Colours().setAccentColor(this, window, resources)
        Compatibility().setNavigationBar(this, window, resources)
        Languages().setLanguage(this)

        //Setup view binding & force portrait mode
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if ((intent.extras ?: return).getBoolean("duplicate", false)) {
            val contextView = findViewById<View>(R.id.scrolling)
            Snackbar.make(contextView, "Already in list!", Snackbar.LENGTH_SHORT)
                .show()
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
        adapter = ScrollingAdapter(scrollingList, this@ScrollingActivity)

        setSupportActionBar(binding.toolbar)
        binding.toolbarLayout.title = list

        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)
        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)

        binding.toolbar.navigationIcon = back

        binding.recyclerView.adapter = adapter

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.scroll_mode_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                val intent2 = Intent(this, AddQuote::class.java)
                val b = Bundle()
                b.putString("list", listTmp) // Your id
                intent2.putExtras(b)
                this.startActivity(intent2)
                true
            }
            android.R.id.home -> {
                val intent2 = Intent(this, YourLists::class.java)
                this.startActivity(intent2)
                finish()
                super.onOptionsItemSelected(item)
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
                val listArrFinal = LinkedList(listArr?.split("//"))
                listArrFinal.remove(text)
                val stringOut = listArrFinal.joinToString(separator = "//")
                editor.putString("Favourites", stringOut)
                editor.apply()

            } else {
                clickedItem.resource = R.drawable.heart_full_red

                val pref = getSharedPreferences("List_system", 0)
                val editor = pref.edit()
                val listArr = pref.getString("Favourites", "")
                val listArrFinal = LinkedList(listArr?.split("//"))
                listArrFinal.push(text)
                val stringOut = listArrFinal.joinToString(separator = "//")
                editor.putString("Favourites", stringOut)
                editor.apply()

            }
            binding.root.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            adapter.notifyItemChanged(position)
        }
    }

    override fun onShareClick(position: Int) {
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
        val listArrFinal = LinkedList(listArr?.split("//"))
        listArrFinal.remove(text)
        val stringOut = listArrFinal.joinToString(separator = "//")
        editor.putString(listTmp, stringOut)
        editor.apply()

    }

    private fun generateDummyList(max: Int): ArrayList<ExampleItem> {

        val list = ArrayList<ExampleItem>()

        var i = 0
        var item: ExampleItem

        val pref = getSharedPreferences("List_system", 0)
        val listArr = pref.getString("Favourites", "")
        val listArrFinal = LinkedList(listArr?.split("//"))


        while (i != max) {
            var special = false
            if (listTmp == "Favourites") {
                special = true
            }
            if ((listArrFinal as MutableList<String?>).contains(prefList[i])) {
                if (prefList[i] != "") {
                    item = ExampleItem(prefList[i], R.drawable.heart_full_red, special)
                    list += item
                }
            } else {
                item = ExampleItem(prefList[i], R.drawable.like, special)
                list += item
            }

            i++
        }
        return list
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent2 = Intent(this, YourLists::class.java)
        this.startActivity(intent2)
        finish()
    }
}


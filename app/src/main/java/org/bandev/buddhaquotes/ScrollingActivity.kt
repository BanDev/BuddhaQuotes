package org.bandev.buddhaquotes

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.*
import androidx.annotation.RequiresApi
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.content_scrolling.*
import java.util.*
import kotlin.collections.ArrayList

class ScrollingActivity : AppCompatActivity(), ScrollingAdapter.OnItemClickFinder {

    private lateinit var scrollingList: ArrayList<ExampleItem>
    private lateinit var adapter: ScrollingAdapter

    private var list_tmp: String = ""

    private lateinit var pref_list: List<String>

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        val back = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)

        val list = (intent.getStringExtra("list") ?: return).toString()
        list_tmp = list

        val pref = getSharedPreferences("List_system", 0)
        val pref_string = pref.getString(list_tmp, "")
        pref_list = (pref_string ?: return).split("//").toMutableList()

        //(pref_list as MutableList<String>).removeAt(0)

        scrollingList = generateDummyList(pref_list.size)

        adapter = ScrollingAdapter(scrollingList, this)

        setSupportActionBar(findViewById(R.id.toolbar))
        window.statusBarColor = ContextCompat.getColor(this@ScrollingActivity, R.color.colorTop)
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = list

        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)
        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)


        toolbar.navigationIcon = back

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(false)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }


        val view = View(this)
        view.doOnLayout {
            view.windowInsetsController?.show(WindowInsets.Type.ime())
            // You can also access it from Window
            window.insetsController?.show(WindowInsets.Type.ime())
        }

        view.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = insets.systemWindowInsetBottom)
            insets
        }
    }

    fun insertItem(text: String) {
        val index = 0
        val newItem = ExampleItem(
            text,
            R.drawable.like,
            false
        )

        scrollingList.add(index, newItem)
        adapter.notifyItemInserted(index)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.scroll_mode_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                val intent2 = Intent(this, AddList::class.java)
                val b = Bundle()
                b.putString("list", list_tmp) // Your id
                intent2.putExtras(b)
                this.startActivity(intent2)
                true
            }
            android.R.id.home -> {
                val intent2 = Intent(this, Favourites::class.java)
                this.startActivity(intent2)
                finish()
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onLikeClick(position: Int, text: String) {
        if (list_tmp != "Favourites") {
            val clickedItem = scrollingList[position]

            if (clickedItem.resource == R.drawable.heart_full_red) {
                clickedItem.resource = R.drawable.like

                val pref = getSharedPreferences("List_system", 0)
                val editor = pref.edit()
                val list_arr = pref.getString("Favourites", "")
                val list_arr_final = LinkedList(list_arr?.split("//"))
                list_arr_final.remove(text)
                val string_out = list_arr_final.joinToString(separator = "//")
                editor.putString("Favourites", string_out)
                editor.apply()

            } else {
                clickedItem.resource = R.drawable.heart_full_red

                val pref = getSharedPreferences("List_system", 0)
                val editor = pref.edit()
                val list_arr = pref.getString("Favourites", "")
                val list_arr_final = LinkedList(list_arr?.split("//"))
                list_arr_final.push(text)
                val string_out = list_arr_final.joinToString(separator = "//")
                editor.putString("Favourites", string_out)
                editor.apply()

            }

            vibratePhone()
            adapter.notifyItemChanged(position)
        }

    }

    override fun onShareClick(position: Int) {
        val clickedItem = scrollingList[position]
        vibratePhone()

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
        vibratePhone()

        scrollingList.removeAt(position)
        adapter.notifyItemRemoved(position)

        val pref = getSharedPreferences("List_system", 0)
        val editor = pref.edit()
        val list_arr = pref.getString(list_tmp, "")
        val list_arr_final = LinkedList(list_arr?.split("//"))
        list_arr_final.remove(text)
        val string_out = list_arr_final.joinToString(separator = "//")
        editor.putString(list_tmp, string_out)
        editor.apply()

    }

    private fun generateDummyList(max: Int): ArrayList<ExampleItem> {

        val list = ArrayList<ExampleItem>()

        var i = 0
        var item: ExampleItem

        val pref = getSharedPreferences("List_system", 0)
        val list_arr = pref.getString("Favourites", "")
        val list_arr_final = LinkedList(list_arr?.split("//"))


        while (i != max) {
            var special = false
            if (list_tmp == "Favourites") {
                special = true
            }
            item = if ((list_arr_final as MutableList<String?>).contains(pref_list[i])) {
                ExampleItem(pref_list[i], R.drawable.heart_full_red, special)
            } else {
                ExampleItem(pref_list[i], R.drawable.like, special)
            }

            list += item
            i++
        }
        return list
    }

    private fun vibratePhone() {
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        50,
                        VibrationEffect.EFFECT_HEAVY_CLICK
                    )
                )
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        50,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            }
            else -> {
                vibrator.vibrate(50)
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {

        val intent2 = Intent(this, Favourites::class.java)
        this.startActivity(intent2)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        val intent2 = Intent(this, Favourites::class.java)
        this.startActivity(intent2)
        finish()
    }
}


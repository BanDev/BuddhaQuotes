package org.bandev.buddhaquotes

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.content_scrolling.*
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Hardware
import org.bandev.buddhaquotes.core.Languages
import java.util.*
import kotlin.collections.ArrayList

class Favourites : AppCompatActivity(), ScrollingAdapter.OnItemClickFinder {
    override fun onLikeClick(position: Int, text: String) {
        TODO("Not yet implemented")
    }

    private lateinit var scrollingList: ArrayList<ListMenuItem>
    private lateinit var adapter: ListMenuAdapter
    private lateinit var favs: Array<String?>
    private lateinit var array: Array<String>

    override fun onShareClick(position: Int) {
        val intent2 = Intent(this, ScrollingActivity::class.java)
        val mBundle = Bundle()
        mBundle.putString("list", array[position])
        intent2.putExtras(mBundle)
        this.startActivity(intent2)
        finish()
    }

    override fun onBinClick(position: Int, text: String) {
        Hardware().vibrate(this)

        scrollingList.removeAt(position)
        adapter.notifyItemRemoved(position)

        val pref = getSharedPreferences("List_system", 0)
        val editor = pref.edit()
        val listArr = pref.getString("MASTER_LIST", "Favourites")
        val listArrFinal = LinkedList(listArr?.split("//"))
        listArrFinal.remove(text)
        val stringOut = listArrFinal.joinToString(separator = "//")
        editor.putString("MASTER_LIST", stringOut)
        editor.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Colours().setAccentColor(this, window)
        Compatibility().setNavigationBarColour(this, window, resources)
        Languages().setLanguage(this)
        setContentView(R.layout.activity_favourites)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val myToolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(myToolbar)
        (supportActionBar ?: return).setDefaultDisplayHomeAsUpEnabled(true)
        val back = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)

        myToolbar.navigationIcon = back

        WindowCompat.setDecorFitsSystemWindows(window, false)
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        val param = (myToolbar ?: return).layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0, statusBarHeight, 0, 0)
        myToolbar.layoutParams = param

        val view = View(this)
        view.doOnLayout {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                view.windowInsetsController?.show(WindowInsets.Type.ime())
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.show(WindowInsets.Type.ime())
            }
        }

        view.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = insets.systemWindowInsetBottom)
            insets
        }


        val pref = getSharedPreferences("List_system", 0)
        favs = arrayOf(pref.getString("MASTER_LIST", "Favourites"))
        array = (favs[0] ?: return).split("//".toRegex()).toTypedArray()
        array = array.distinct().toTypedArray()

        scrollingList = generateDummyList(array.size)
        adapter = ListMenuAdapter(scrollingList, this)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(false)

    }

    private fun generateDummyList(max: Int): ArrayList<ListMenuItem> {

        val list = ArrayList<ListMenuItem>()

        var i = 0
        while (i != max) {
            var special = false
            val pref = getSharedPreferences("List_system", 0)
            val array2 = pref.getString(array[i], "")!!.split("//")
            val count: Int = array2.size
            if (array[i] == "Favourites") {
                special = true
            }
            var summary = ""
            summary = if (count != 1) {
                "$count items"
            } else {
                "$count item"
            }
            val item = ListMenuItem(array[i], summary, special)
            list += item
            i++
        }
        return list
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.fav_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                val myIntent = Intent(this@Favourites, CreateNewList::class.java)
                this@Favourites.startActivity(myIntent)
                finish()
                true
            }
            android.R.id.home -> {
                val myIntent = Intent(this@Favourites, MainActivity::class.java)
                this@Favourites.startActivity(myIntent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val myIntent = Intent(this@Favourites, MainActivity::class.java)
        this@Favourites.startActivity(myIntent)

        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        val myIntent = Intent(this@Favourites, MainActivity::class.java)
        val mBundle = Bundle()
        this@Favourites.startActivity(myIntent)
        finish()
    }
}

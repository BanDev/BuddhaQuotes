package org.bandev.buddhaquotes

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.reddit.indicatorfastscroll.FastScrollItemIndicator
import com.reddit.indicatorfastscroll.FastScrollerView
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages
import java.util.*

class AddList : AppCompatActivity() {

    lateinit var toolbar: MaterialToolbar
    private lateinit var back: Drawable
    private lateinit var go: Button
    private lateinit var number: EditText
    private var num: String = ""
    private var quote = Quotes()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Colours().setAccentColor(this, window)
        Compatibility().setNavigationBarColour(this, window, resources)
        Languages().setLanguage(this)
        setContentView(R.layout.activity_addlist)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val list = (intent.getStringExtra("list") ?: return).toString()

        //Setup toolbar
        back = (ContextCompat.getDrawable(this, R.drawable.ic_arrow_back) ?: return)
        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Add to List"
        toolbar.navigationIcon = back
        setSupportActionBar(toolbar)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.transparent)
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        val param = toolbar.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0, statusBarHeight, 0, 0)
        toolbar.layoutParams = param

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

        val searchView = findViewById<SearchView>(R.id.searchView)
        val listView = findViewById<ListView>(R.id.listView)

        listView.isFastScrollEnabled = true

        val names = genList()

        val adapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.quotes_search, names)

        listView.adapter = adapter

        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->

                val pref = getSharedPreferences("List_system", 0)
                val editor = pref.edit()
                val listArr = pref.getString(list, "")
                val listArrFinal = LinkedList(listArr?.split("//"))
                val intent2 = Intent(this, ScrollingActivity::class.java)
                val mBundle = Bundle()
                mBundle.putString("list", list)
                intent2.putExtras(mBundle)
                if (listArrFinal.contains(listView.getItemAtPosition(position) as String?)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        window.decorView.performHapticFeedback(HapticFeedbackConstants.REJECT)
                    } else {
                        window.decorView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    }
                    Snackbar.make(view, "This quote is already in the list!", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    window.decorView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    listArrFinal.push(listView.getItemAtPosition(position) as String?)
                    this.startActivity(intent2)
                }

                val stringOut = listArrFinal.joinToString(separator = "//")

                editor.putString(list, stringOut)
                editor.apply()
            }

        val fastScrollerView: FastScrollerView = findViewById(R.id.fastscroller)

        fastScrollerView.useDefaultScroller = false
        fastScrollerView.itemIndicatorSelectedCallbacks += object :
            FastScrollerView.ItemIndicatorSelectedCallback {
            override fun onItemIndicatorSelected(
                indicator: FastScrollItemIndicator,
                indicatorCenterY: Int,
                itemPosition: Int
            ) {
                // Handle scrolling
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.clearFocus()
                if (names.contains(p0)) {
                    adapter.filter.filter(p0)
                } else {
                    Snackbar.make(view, "Not Found", Snackbar.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }
        })


    }

    private fun genList(): ArrayList<String> {
        val list = ArrayList<String>()

        val max = 237

        var i = 1

        while (i != max) {
            list.add(Quotes().random(i))
            i++
        }


        return list
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation

        finish()
    }
}

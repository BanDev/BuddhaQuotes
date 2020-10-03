package org.bandev.buddhaquotes

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class InfoPanel : AppCompatActivity() {

    private lateinit var favs: Array<String?>
    private var settings: SharedPreferences? = null
    private var fontsize: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info__panel)

        val myToolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(myToolbar)
        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)
        window.navigationBarColor = ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
        val intent = intent
        val quote = intent.getStringExtra("quote")
        val textview = findViewById<TextView>(R.id.text)
        textview.text = quote

        settings = getSharedPreferences("Settings", 0)
        val textsize: String? = settings?.getString("text_size", "md")
        fontsize = when (textsize) {
            "sm" -> "30"
            "lg" -> "50"
            else -> "40"
        }
        (textview ?: return).textSize = (fontsize ?: return).toFloat()

        // Is user using night mode
        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
            }
            Configuration.UI_MODE_NIGHT_NO -> // No
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            Configuration.UI_MODE_NIGHT_UNDEFINED -> // Who knows? Assume they are not
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val favourites = getSharedPreferences("Favs", 0)
        val editor = favourites.edit()

        val contextView = findViewById<View>(R.id.view)
        favs = arrayOf(favourites.getString("fav", ""))
        val array = (favs[0] ?: return).split("//VADER//".toRegex()).toTypedArray()
        val list: MutableList<String> = ArrayList(listOf(*array))

        val fab = findViewById<FloatingActionButton>(R.id.delete)

        fab.setOnClickListener {
            favs = arrayOf(favourites.getString("fav", ""))
            var array =
                (favs[0] ?: return@setOnClickListener).split("//VADER//".toRegex()).toTypedArray()
            val list: MutableList<String> = ArrayList(listOf(*array))
            val text = textview.text as String
            list.remove(text)
            Log.d("Array", list.toString())
            array = list.toTypedArray()
            var sb = ""
            for (i in array.indices) {
                if (array[i] != "") {
                    sb = sb + array[i] + "//VADER//"
                }
            }
            Log.d("Array", sb)
            editor.putString("fav", sb)
            editor.apply()

            val myIntent = Intent(this@InfoPanel, Favourites::class.java)
            this@InfoPanel.startActivity(myIntent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.fav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.back -> {
                val myIntent = Intent(this@InfoPanel, Favourites::class.java)
                this@InfoPanel.startActivity(myIntent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val myIntent = Intent(this@InfoPanel, Favourites::class.java)
        this@InfoPanel.startActivity(myIntent)
        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        val myIntent = Intent(this@InfoPanel, Favourites::class.java)
        this@InfoPanel.startActivity(myIntent)
        finish()
    }
}

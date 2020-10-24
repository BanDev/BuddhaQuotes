package org.bandev.buddhaquotes

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.appbar.MaterialToolbar

class Favourites : AppCompatActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
            } // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    APPEARANCE_LIGHT_NAVIGATION_BARS
                } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }
            }
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            window.navigationBarColor =
                ResourcesCompat.getColor(resources, R.color.transparent, null)
        } else {
            window.navigationBarColor =
                ResourcesCompat.getColor(resources, R.color.black, null)
        }

        val myToolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(myToolbar)
        window.statusBarColor = ContextCompat.getColor(this@Favourites, R.color.colorTop)
        (supportActionBar ?: return).setDefaultDisplayHomeAsUpEnabled(true)
        val back = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_24)

        myToolbar.navigationIcon = back

        val listview = findViewById<View>(R.id.listView) as ListView
        val pref = getSharedPreferences("List_system", 0)
        val favs = arrayOf(pref.getString("MASTER_LIST", "Favourites"))
        var array = (favs[0] ?: return).split("//".toRegex()).toTypedArray()
        array = array.distinct().toTypedArray()
        val adapter = ArrayAdapter(this, R.layout.aligned_right, array)
        listview.adapter = adapter
        listview.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, l ->
                val value = adapter.getItem(position)
                if (value.toString() != "") {
                    val intent = Intent(this@Favourites, ScrollingActivity::class.java)
                    val b = Bundle()
                    b.putString("list", value.toString()) // Your id
                    intent.putExtras(b) // Put your id to your next Intent
                    startActivity(intent)
                }
            }
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
        overridePendingTransition(
            R.anim.anim_slide_in_left,
            R.anim.anim_slide_out_left
        )
        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        val myIntent = Intent(this@Favourites, MainActivity::class.java)
        val mBundle = Bundle()
        this@Favourites.startActivity(myIntent)
        overridePendingTransition(
            R.anim.anim_slide_in_left,
            R.anim.anim_slide_out_left
        )
        finish()
    }
}

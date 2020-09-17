package org.bandev.buddhaquotes

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Favourites : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)


        val myToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(myToolbar)
        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)
        window.navigationBarColor = resources.getColor(R.color.colorPrimary)
        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
            }
            Configuration.UI_MODE_NIGHT_NO ->                 //No
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            Configuration.UI_MODE_NIGHT_UNDEFINED ->                 //Who knows? Assume they are not
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val listview = findViewById<View>(R.id.listView) as ListView
        val pref = getSharedPreferences("Favs", 0)
        val favs = arrayOf(pref.getString("fav", ""))
        var array = (favs[0] ?: return).split("//VADER//".toRegex()).toTypedArray()
        array = array.distinct().toTypedArray()
        val adapter = ArrayAdapter(this, R.layout.aligned_right, array)
        listview.adapter = adapter
        listview.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, l ->
                val value = adapter.getItem(position)
                if (value.toString() != "") {
                    val intent = Intent(this@Favourites, InfoPanel::class.java)
                    val b = Bundle()
                    b.putString("quote", value.toString()) //Your id
                    intent.putExtras(b) //Put your id to your next Intent
                    startActivity(intent)
                    overridePendingTransition(
                        R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right
                    )
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.fav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.back -> {
                val myIntent = Intent(this@Favourites, MainActivity::class.java)
                this@Favourites.startActivity(myIntent)
                overridePendingTransition(
                    R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left
                )
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


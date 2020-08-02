package org.bandev.buddhaquotes

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class favourites : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)
        val myToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(myToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.colorPrimary)
        }
        val nightModeFlags = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
            }
            Configuration.UI_MODE_NIGHT_NO ->                 //No
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            Configuration.UI_MODE_NIGHT_UNDEFINED ->                 //Who knows? Assume they are not
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val listview = findViewById<View>(R.id.listView) as ListView
        val pref = getSharedPreferences("Favs", 0)
        val editor = pref.edit()
        val favs = arrayOf(pref.getString("fav", ""))
        val array = favs[0]!!.split("//VADER//".toRegex()).toTypedArray()
        val listItem = resources.getStringArray(R.array.array_technology)
        val adapter = ArrayAdapter(this, R.layout.aligned_right, array)
        listview.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.fav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.back -> {
                val myIntent = Intent(this@favourites, MainActivity::class.java)
                this@favourites.startActivity(myIntent)
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
    }
}
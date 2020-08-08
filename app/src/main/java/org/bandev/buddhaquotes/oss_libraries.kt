package org.bandev.buddhaquotes

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class oss_libraries : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val b = intent.extras // or other values
        when (b!!.getString("from")) {
            "settings" -> overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
            "licenses" -> overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oss_libraries)
        val myToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(myToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val actionBar = supportActionBar
        assert(supportActionBar != null //null check
        )
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val nightModeFlags = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
            }
            Configuration.UI_MODE_NIGHT_NO -> window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            Configuration.UI_MODE_NIGHT_UNDEFINED -> window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        val listview = findViewById<View>(R.id.listView) as ListView
        val listItem = resources.getStringArray(R.array.array_technology)
        val adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listItem)
        listview.adapter = adapter
        listview.onItemClickListener = OnItemClickListener { adapterView, view, position, l ->
            val value = adapter.getItem(position)
            val intent = Intent(this@oss_libraries, licenses::class.java)
            val b = Bundle()
            b.putString("from", value) //Your id
            intent.putExtras(b) //Put your id to your next Intent
            startActivity(intent)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.colorPrimary)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val i = Intent(this, settings::class.java)
                startActivity(i)
                overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        overridePendingTransition(R.anim.anim_slide_in_right,
            R.anim.anim_slide_out_right)
    }

    override fun onSupportNavigateUp(): Boolean {
        overridePendingTransition(R.anim.anim_slide_in_right,
            R.anim.anim_slide_out_right)
        return true
    }
}
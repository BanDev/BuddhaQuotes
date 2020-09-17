package org.bandev.buddhaquotes

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class OSSLibraries : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val b = intent.extras // or other values
        when ((b ?: return).getString("from")) {
            "settings" -> overridePendingTransition(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left
            )
            "licenses" -> overridePendingTransition(
                R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right
            )
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oss_libraries)
        val myToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(myToolbar)
        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)

        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)
        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
            }
            Configuration.UI_MODE_NIGHT_NO -> window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            Configuration.UI_MODE_NIGHT_UNDEFINED -> window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        val listview = findViewById<View>(R.id.listView) as ListView
        val listItem = resources.getStringArray(R.array.array_technology)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, android.R.id.text1, listItem
        )
        listview.adapter = adapter
        var url = ""
        listview.onItemClickListener = OnItemClickListener { adapterView, view, position, l ->
            when (adapter.getItem(position)) {
                "@string/material_design_icons" -> url =
                    "https://github.com/google/material-design-icons/blob/master/LICENSE"
                "@string/kotlin" -> url =
                    "https://github.com/JetBrains/kotlin/blob/c5aa35e016aed6f83392e2f2b32fc0a088ee3b83/license/LICENSE.txt"
                "@string/androidx" -> url =
                    "https://github.com/androidx/androidx/blob/androidx-master-dev/LICENSE.txt"
                "@string/material_design_android" -> url =
                    "https://github.com/material-components/material-components-android/blob/master/LICENSE"
            }
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(i)
            overridePendingTransition(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left
            )
        }
        window.navigationBarColor = resources.getColor(R.color.colorPrimary)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val i = Intent(this, settings::class.java)
                startActivity(i)
                overridePendingTransition(
                    R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right
                )
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        overridePendingTransition(
            R.anim.anim_slide_in_right,
            R.anim.anim_slide_out_right
        )
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        overridePendingTransition(
            R.anim.anim_slide_in_right,
            R.anim.anim_slide_out_right
        )
        finish()
        return true
    }
}
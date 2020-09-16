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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class Info_Panel : AppCompatActivity() {

    private lateinit var favs: Array<String?>
    private var Settings: SharedPreferences? = null
    private var font_size:String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info__panel)

        val myToolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(myToolbar)
        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)
        window.navigationBarColor = resources.getColor(R.color.colorPrimary)
        val intent = intent
        val quote = intent.getStringExtra("quote")
        val textview = findViewById<TextView>(R.id.text)
        textview.text = quote

        Settings = getSharedPreferences("Settings", 0)
        val text_size: String? = Settings?.getString("text_size", "md")
        font_size = when (text_size) {
            "sm" -> "30"
            "lg" -> "50"
            else -> "40"
        }
        (textview ?: return).textSize = (font_size ?: return).toFloat()

        //Is user using night mode
        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
            }
            Configuration.UI_MODE_NIGHT_NO ->                 //No
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            Configuration.UI_MODE_NIGHT_UNDEFINED ->                 //Who knows? Assume they are not
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val Favourites = getSharedPreferences("Favs", 0)
        val editor = Favourites.edit()

        val contextView = findViewById<View>(R.id.view)
        favs = arrayOf(Favourites.getString("fav", ""))
        val array = (favs[0] ?: return).split("//VADER//".toRegex()).toTypedArray()
        val list: MutableList<String> = ArrayList(listOf(*array))


        val fab = findViewById<FloatingActionButton>(R.id.delete)



        fab.setOnClickListener {
            favs = arrayOf(Favourites.getString("fav", ""))
            var array = (favs[0] ?: return@setOnClickListener).split("//VADER//".toRegex()).toTypedArray()
            val list: MutableList<String> = ArrayList(listOf(*array))
            val text = textview.text as String
            list.remove(text)
            Log.d("Array", list.toString())
            array = list.toTypedArray()
            var sb = ""
            for (i in array.indices) {
                if(array[i] != "") {
                    sb = sb + array[i] + "//VADER//"
                }
            }
            Log.d("Array", sb)
            editor.putString("fav", sb)
            editor.apply()

            val myIntent = Intent(this@Info_Panel, favourites::class.java)
            this@Info_Panel.startActivity(myIntent)
            overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left)
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
                val myIntent = Intent(this@Info_Panel, favourites::class.java)
                this@Info_Panel.startActivity(myIntent)
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val myIntent = Intent(this@Info_Panel, favourites::class.java)
        this@Info_Panel.startActivity(myIntent)
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        val myIntent = Intent(this@Info_Panel, favourites::class.java)
        this@Info_Panel.startActivity(myIntent)
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
        finish()
    }
}
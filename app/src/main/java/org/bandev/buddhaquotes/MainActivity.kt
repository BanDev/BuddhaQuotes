package org.bandev.buddhaquotes

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity() {

    private var done: Boolean? = null
    private var QuoteView: TextView? = null
    private val quote = Quote()
    private var refresh: FloatingActionButton? = null
    private var favourite: FloatingActionButton? = null
    private var list: List<String?>? = null
    private lateinit var favs: Array<String?>
    private lateinit var array: Array<String?>
    private var font_size: String? = null
    private var heart_black: Drawable? = null
    var toolbar: Toolbar? = null
    private var Settings:SharedPreferences ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Define UI Variables
        setContentView(R.layout.activity_main)
        refresh = findViewById(R.id.refresh)
        favourite = findViewById(R.id.favourite)
        toolbar = findViewById(R.id.toolbar)
        heart_black = ContextCompat.getDrawable(this, R.drawable.heart_black)
        QuoteView = findViewById(R.id.quote)

        //Sets Up Toolbar And Adds Icons Etc
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar?.navigationIcon = heart_black

        //If Using Night Mode, Change Some Stuff
        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            Configuration.UI_MODE_NIGHT_UNDEFINED -> window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window.navigationBarColor = resources.getColor(R.color.colorPrimary)
        //Get Text Size From Shared Preferences  (Was Set In Settings, Defaults To Medium (40px)) & Sets It
        Settings = getSharedPreferences("Settings", 0)
        val text_size: String? = Settings?.getString("text_size", "md")
        font_size = when (text_size) {
            "sm" -> "30"
            "lg" -> "50"
            else -> "40"
        }
        QuoteView!!.textSize = font_size!!.toFloat()

        //When Refresh Is Clicked
        refresh!!.setOnClickListener { newQuote() }

        val Favourites = getSharedPreferences("Favs", 0)
        val editor = Favourites.edit()



        //When Favourite Is Clicked
        favourite!!.setOnClickListener {
            if (!done!!) {
                favourite!!.isEnabled = false
                //If It Is Not Liked Already
                if (favs[0] != "") {
                    favs[0] = QuoteView!!.text.toString() + "//VADER//" + favs[0]
                } else {
                    favs[0] = QuoteView!!.text as String + "//VADER//"
                }
                editor.putString("fav", favs[0])
                editor.apply()
                Log.d("Array", favs[0]!!)
                done = true
                favourite!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.heart_full_white
                    )
                )
                favourite!!.isEnabled = true
            } else {
                //If It Is Already Liked
                favourite!!.isEnabled = false
                favs = arrayOf(Favourites.getString("fav", ""))
                var array = favs[0]!!.split("//VADER//".toRegex()).toTypedArray()
                val list: MutableList<String> = ArrayList(listOf(*array))
                val text = QuoteView!!.text as String
                list.remove(text)
                array = list.toTypedArray()
                array = array.distinct().toTypedArray()
                var sb = ""
                for (i in array.indices) {
                    if (array[i] != "") {
                        sb = sb + array[i] + "//VADER//"
                    }
                }
                Log.d("Array", sb)
                editor.putString("fav", sb)
                editor.commit()
                done = false
                favourite!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.like_white_empty
                    )
                )
                favourite!!.isEnabled = true
            }
        }

        /* Get The First Quote */
        newQuote()
    }

    private fun newQuote() {
        val text = quote.random()
        QuoteView!!.text = text
        done = false
        favourite!!.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_baseline_stars_24))
        val pref = getSharedPreferences("Favs", 0)
        favs = arrayOf(pref.getString("fav", ""))
        array = favs[0]!!.split("//VADER//".toRegex()).toTypedArray()
        list = listOf(*array)
        if ((list as MutableList<String?>).contains(QuoteView!!.text)) {
            done = true
            favourite!!.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.heart_full_white))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_main_setting -> {
                val myIntent = Intent(this@MainActivity, settings::class.java)
                this@MainActivity.startActivity(myIntent)
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left)
                true
            }
            android.R.id.home -> {
                val intent2 = Intent(this@MainActivity, favourites::class.java)
                this@MainActivity.startActivity(intent2)
                overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right)
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        this.finishAffinity()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // add your animation
        this.finishAffinity()
    }
}
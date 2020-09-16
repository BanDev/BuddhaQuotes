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
    private var quotenumber: Int = 0
    private var quoteview: TextView? = null
    private val quote = Quote()
    private var refresh: FloatingActionButton? = null
    private var favourite: FloatingActionButton? = null
    private var list: List<String?>? = null
    private lateinit var favs: Array<String?>
    private lateinit var array: Array<String?>
    private var fontsize: String? = null
    private var heartblack: Drawable? = null
    var toolbar: Toolbar? = null
    private var settings: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        quotenumber = ((intent.extras ?: return).getString("quote") ?: return).toInt()

        //Define UI Variables
        setContentView(R.layout.activity_main)
        refresh = findViewById(R.id.refresh)
        favourite = findViewById(R.id.favourite)
        toolbar = findViewById(R.id.toolbar)
        heartblack = ContextCompat.getDrawable(this, R.drawable.heart_black)
        quoteview = findViewById(R.id.quote)

        //Sets Up Toolbar And Adds Icons Etc
        setSupportActionBar(toolbar)
        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)
        toolbar?.navigationIcon = heartblack

        //If Using Night Mode, Change Some Stuff
        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            Configuration.UI_MODE_NIGHT_UNDEFINED -> window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window.navigationBarColor = resources.getColor(R.color.colorPrimary)

        //Get Text Size From Shared Preferences  (Was Set In Settings, Defaults To Medium (40px)) & Sets It
        settings = getSharedPreferences("Settings", 0)
        val textsize: String? = settings?.getString("text_size", "md")
        fontsize = when (textsize) {
            "sm" -> "30"
            "lg" -> "50"
            else -> "40"
        }
        (quoteview ?: return).textSize = (fontsize ?: return).toFloat()

        //When Refresh Is Clicked
        (refresh ?: return).setOnClickListener { newQuote(0) }

        val favourites = getSharedPreferences("Favs", 0)
        val editor = favourites.edit()


        //When Favourite Is Clicked
        (favourite ?: return).setOnClickListener {
            if (!(done ?: return@setOnClickListener)) {
                (favourite ?: return@setOnClickListener).isEnabled = false
                //If It Is Not Liked Already
                if (favs[0] != "") {
                    favs[0] = (quoteview
                        ?: return@setOnClickListener).text.toString() + "//VADER//" + favs[0]
                } else {
                    favs[0] = (quoteview ?: return@setOnClickListener).text as String + "//VADER//"
                }
                editor.putString("fav", favs[0])
                editor.apply()
                Log.d("Array", favs[0] ?: return@setOnClickListener)
                done = true
                (favourite ?: return@setOnClickListener).setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.heart_full_white
                    )
                )
                (favourite ?: return@setOnClickListener).isEnabled = true
            } else {
                //If It Is Already Liked
                (favourite ?: return@setOnClickListener).isEnabled = false
                favs = arrayOf(favourites.getString("fav", ""))
                var array = (favs[0] ?: return@setOnClickListener).split("//VADER//".toRegex())
                    .toTypedArray()
                val list: MutableList<String> = ArrayList(listOf(*array))
                val text = (quoteview ?: return@setOnClickListener).text as String
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
                (favourite ?: return@setOnClickListener).setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.like_white_empty
                    )
                )
                (favourite ?: return@setOnClickListener).isEnabled = true
            }
        }

        /* Get The First Quote */
        if (quotenumber == null) {
            quotenumber = 0
        }
        newQuote(quotenumber)
    }

    private fun newQuote(Quote_Number_Local: Int) {
        val text = quote.random(Quote_Number_Local)
        (quoteview ?: return).text = text
        done = false
        (favourite ?: return).setImageDrawable(
            ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.ic_baseline_stars_24
            )
        )
        val pref = getSharedPreferences("Favs", 0)
        favs = arrayOf(pref.getString("fav", ""))
        array = (favs[0] ?: return).split("//VADER//".toRegex()).toTypedArray()
        list = listOf(*array)
        if ((list as MutableList<String?>).contains((quoteview ?: return).text)) {
            done = true
            (favourite ?: return).setImageDrawable(
                ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.heart_full_white
                )
            )
        }
        quotenumber = quote.Quote_Number_Global
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_main_setting -> {
                val myIntent = Intent(this@MainActivity, settings!!::class.java)
                val mBundle = Bundle()
                mBundle.putString("quote", quotenumber.toString())
                myIntent.putExtras(mBundle)
                this@MainActivity.startActivity(myIntent)
                overridePendingTransition(
                    R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left
                )
                true
            }
            android.R.id.home -> {
                val intent2 = Intent(this@MainActivity, Favourites::class.java)
                val mBundle = Bundle()
                mBundle.putString("quote", quotenumber.toString())
                intent2.putExtras(mBundle)
                this@MainActivity.startActivity(intent2)
                overridePendingTransition(
                    R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right
                )
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
package org.bandev.buddhaquotes

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.updatePadding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var done: Boolean? = null
    var QuoteView: TextView? = null
    val quote = Quote()
    var refresh: FloatingActionButton? = null
    var favourite: FloatingActionButton? = null
    var list: List<String?>? = null
    lateinit var favs: Array<String?>
    lateinit var array: Array<String?>
    var font_size: String? = null
    var heart_black: Drawable? = null
    var toolbar: Toolbar? = null
    var Settings:SharedPreferences ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        //Define UI Variables
        setContentView(R.layout.activity_main);
        refresh = findViewById(R.id.refresh);
        favourite = findViewById(R.id.favourite);
        toolbar = findViewById(R.id.toolbar)
        heart_black = ContextCompat.getDrawable(this, R.drawable.heart_black);
        QuoteView = findViewById(R.id.quote);

        //Sets Up Toolbar And Adds Icons Etc
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar?.setNavigationIcon(heart_black);

        //If Using Night Mode, Change Some Stuff
        val nightModeFlags = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_NO -> window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            Configuration.UI_MODE_NIGHT_UNDEFINED -> window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.colorPrimary)
        }
        //Get Text Size From Shared Preferences  (Was Set In Settings, Defaults To Medium (40px)) & Sets It
        Settings = getSharedPreferences("Settings", 0);
        var text_size: String? = Settings?.getString("text_size", "md")
        when (text_size) {
            "sm" -> font_size = "30"
            "lg" -> font_size = "50";
            else -> font_size = "40";
        }
        QuoteView!!.setTextSize(font_size!!.toFloat());

        //When Refresh Is Clicked
        refresh!!.setOnClickListener(View.OnClickListener { newQuote() })

        val Favourites = getSharedPreferences("Favs", 0)
        val editor = Favourites.edit()



        //When Favourite Is Clicked
        favourite!!.setOnClickListener(View.OnClickListener {
            if (!done!!) {
                favourite!!.isEnabled = false
                //If It Is Not Liked Already
                if (favs[0] != "") {
                    favs[0] = QuoteView!!.getText().toString() + "//VADER//" + favs[0]
                } else {
                    favs[0] = QuoteView!!.getText() as String + "//VADER//"
                }
                editor.putString("fav", favs[0])
                editor.commit()
                Log.d("Array", favs[0]!!)
                done = true
                favourite!!.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.heart_full_white))
                favourite!!.isEnabled = true
            } else {
                //If It Is Already Liked
                favourite!!.isEnabled = false
                favs = arrayOf<String?>(Favourites.getString("fav", ""))!!
                var array = favs[0]!!.split("//VADER//".toRegex()).toTypedArray()
                val list: MutableList<String> = ArrayList(Arrays.asList(*array))
                val text = QuoteView!!.getText() as String
                list.remove(text)
                array = list.toTypedArray()
                array = array.distinct().toTypedArray()
                var sb = ""
                for (i in array.indices) {
                    if(array[i] != "") {
                        sb = sb + array[i] + "//VADER//"
                    }
                }
                Log.d("Array", sb)
                editor.putString("fav", sb)
                editor.commit()
                done = false
                favourite!!.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.like_white_empty))
                favourite!!.isEnabled = true
            }
        })

        /* Get The First Quote */
        newQuote()
    }

    fun newQuote() {
        val text = quote.random()
        QuoteView!!.setText(text)
        done = false
        favourite!!.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_baseline_stars_24))
        val pref = getSharedPreferences("Favs", 0)
        favs = arrayOf(pref.getString("fav", ""))
        array = favs[0]!!.split("//VADER//".toRegex()).toTypedArray()
        list = Arrays.asList(*array)
        if ((list as MutableList<String?>).contains(QuoteView!!.getText())) {
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
}
@file:Suppress("Annotator", "Annotator", "Annotator")

package org.bandev.buddhaquotes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.plattysoft.leonids.ParticleSystem
import java.util.*


class MainActivity : AppCompatActivity() {

    private var done: Boolean? = null
    private var quotenumber: Int = 0
    private var quoteview: TextView? = null
    private val quote = Quotes()
    private var share: FloatingActionButton? = null
    private var refresh: FloatingActionButton? = null
    private var favourite: FloatingActionButton? = null
    private var list: List<String?>? = null
    private lateinit var favs: Array<String?>
    private lateinit var array: Array<String?>
    private var noanim = false
    private var firstpress = true
    private var fontsize: String? = null
    private var heartblack: Drawable? = null
    var toolbar: Toolbar? = null
    private var settings: SharedPreferences? = null

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        val quotenumber = sharedPref.getInt("Quote_Number", 0)

        //Define UI Variables
        setContentView(R.layout.activity_main)
        refresh = findViewById(R.id.refresh)
        share = findViewById(R.id.share)
        favourite = findViewById(R.id.favourite)
        toolbar = findViewById(R.id.toolbar)
        heartblack = ContextCompat.getDrawable(this, R.drawable.heart_white)
        quoteview = findViewById(R.id.quote)

        //Sets Up Toolbar And Adds Icons Etc
        setSupportActionBar(toolbar)
        (supportActionBar ?: return).setDisplayShowTitleEnabled(false)
        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)
        window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.colorTop)

        toolbar?.navigationIcon = heartblack

        WindowCompat.setDecorFitsSystemWindows(window, false)

        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        val param = (toolbar ?: return).layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0, statusBarHeight, 0, 0)
        (toolbar ?: return).layoutParams = param

        val view = View(this)
        view.doOnLayout {
            view.windowInsetsController?.show(WindowInsets.Type.ime())
            // You can also access it from Window
            window.insetsController?.show(WindowInsets.Type.ime())
        }

        view.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = insets.systemWindowInsetBottom)
            insets
        }

        var Allowed = true;
        findViewById<FloatingActionButton>(R.id.share).setOnClickListener {
            if(Allowed) {
                Allowed = false;
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND

                    val text = quoteview?.text.toString() + "\n\n~Buddha"

                    putExtra(Intent.EXTRA_TEXT, text)
                    type = "text/plain"

                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
            Thread.sleep(1_000)  // wait for 1 second
            Allowed = true

        }

        var navBarHeight = 0
        val resourceId2 = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId2 > 0) {
            navBarHeight = resources.getDimensionPixelSize(resourceId)
        }


        /* val param2 = (favourite ?: return).layoutParams as ViewGroup.MarginLayoutParams
        param2.setMargins(0, 0, 0, navBarHeight)
        (favourite ?: return).layoutParams = param2

        val param3 = (refresh ?: return).layoutParams as ViewGroup.MarginLayoutParams
        param3.setMargins(0, 0, 0, navBarHeight)
        (refresh ?: return).layoutParams = param3

        val param4 = (share ?: return).layoutParams as ViewGroup.MarginLayoutParams
        param4.setMargins(0, 0, 0, navBarHeight)
        (share ?: return).layoutParams = param4
*/
        //If Using Night Mode, Change Some Stuff
        // when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        //    Configuration.UI_MODE_NIGHT_NO -> {
        //    } // Night mode is not active, we're using the light theme
        //    Configuration.UI_MODE_NIGHT_YES -> {
        //    } // Night mode is active, we're using dark theme
        //}

        //Get Text Size From Shared Preferences  (Was Set In Settings, Defaults To Medium (40px)) & Sets It
        settings = getSharedPreferences("Settings", 0)
        val textsize: String? = settings?.getString("text_size", "md")
        fontsize = when (textsize) {
            "sm" -> "30"
            "lg" -> "50"
            else -> "40"
        }
        (quoteview ?: return).textSize = (fontsize ?: return).toFloat()

        val rotateAnimation = RotateAnimation(
            0F, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f

        )

        //When Refresh Is Clicked
        (refresh ?: return).setOnClickListener {
            if (firstpress) {
                rotateAnimation.duration = 2.toLong() * 250
                (refresh ?: return@setOnClickListener).startAnimation(rotateAnimation)
                firstpress = false
            } else if (rotateAnimation.hasEnded()) {
                noanim = true
                rotateAnimation.duration = 2.toLong() * 250
                (refresh ?: return@setOnClickListener).startAnimation(rotateAnimation)
            }

            newQuote(0)
        }

        val favourites = getSharedPreferences("Favs", 0)
        val editor = favourites.edit()


        //When Favourite Is Clicked
        (favourite ?: return).setOnClickListener {
            if (!(done ?: return@setOnClickListener)) {
                val like = ParticleSystem(this, 5, R.drawable.heart_full_red, 600)
                like.setSpeedRange(0.0750f, 0.0750f)
                like.setFadeOut(100)
                like.setScaleRange(0.5f, 1f)
                like.oneShot(favourite, 5)
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
                //val like = ParticleSystem(this, 5, R.drawable.heart_white, 600)
                //like.setSpeedRange(0.0625f, 0.0625f)
                // like.setFadeOut(100)
                // like.oneShot(favourite, 5);
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
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt("Quote_Number", quote.quotenumberglobal)
            commit()
        }
        quotenumber = quote.quotenumberglobal
        noanim = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_main_setting -> {
                val myIntent =
                    Intent(this@MainActivity, Settings::class.java)
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


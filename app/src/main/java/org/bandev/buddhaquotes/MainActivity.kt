package org.bandev.buddhaquotes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.preference.PreferenceManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.plattysoft.leonids.ParticleSystem
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatability
import org.bandev.buddhaquotes.core.Languages
import java.util.*


class MainActivity : AppCompatActivity() {

    private var done: Boolean? = null
    private var quoteNumber: Int = 0
    private var quoteView: TextView? = null
    private val quote = Quotes()
    private var share: FloatingActionButton? = null
    private var refresh: FloatingActionButton? = null
    private var favourite: FloatingActionButton? = null
    private var list: List<String?>? = null
    private lateinit var favs: Array<String?>
    private lateinit var array: Array<String?>
    private var noAnim = false
    private var firstPress = true
    private var fontSize: String? = null
    private var heartBlack: Drawable? = null
    var toolbar: MaterialToolbar? = null
    private var settings: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Colours().setAccentColor(this, window)
        Compatability().setNavigationBarColour(this, window, resources)
        Languages().setLanguage(this)

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val fun_mode = sharedPrefs.getString("fun_mode", "no")

        if(fun_mode == "yes"){
            setContentView(R.layout.activity_main2)
            window.statusBarColor = ResourcesCompat.getColor(resources, R.color.transparent, null)
        }else{
            setContentView(R.layout.activity_main)
        }





        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        val quotenumber = sharedPref.getInt("Quote_Number", 0)

        refresh = findViewById(R.id.refresh)
        share = findViewById(R.id.share)
        favourite = findViewById(R.id.favourite)
        toolbar = findViewById(R.id.toolbar)
        heartBlack = ContextCompat.getDrawable(this, R.drawable.format_list_bulleted_black_24dp)
        quoteView = findViewById(R.id.quote)

        // Sets up toolbar and adds icons
        setSupportActionBar(toolbar)
        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)

        setTheme(R.style.AppTheme_blue)
        toolbar?.navigationIcon = heartBlack

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                view.windowInsetsController?.show(WindowInsets.Type.ime())
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.show(WindowInsets.Type.ime())
            }
        }

        view.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = insets.systemWindowInsetBottom)
            insets
        }

        findViewById<FloatingActionButton>(R.id.share).setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND

                val text = quoteView?.text.toString() + "\n\n~Gautama Buddha"

                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        var navBarHeight = 0
        val resourceId2 = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId2 > 0) {
            navBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        // Get text size from shared preferences (was set in settings, defaults to medium (30px)) and sets it
        settings = getSharedPreferences("Settings", 0)
        val textsize: String? = settings?.getString("text_size", "md")
        fontSize = when (textsize) {
            "sm" -> "25"
            "lg" -> "35"
            else -> "30"
        }
        (quoteView ?: return).textSize = (fontSize ?: return).toFloat()

        val rotateAnimation = RotateAnimation(
            0F,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f

        )

        // When refresh is pressed
        (refresh ?: return).setOnClickListener {
            if (firstPress) {
                rotateAnimation.duration = 2.toLong() * 250
                (refresh ?: return@setOnClickListener).startAnimation(rotateAnimation)
                firstPress = false
            } else if (rotateAnimation.hasEnded()) {
                noAnim = true
                rotateAnimation.duration = 2.toLong() * 250
                (refresh ?: return@setOnClickListener).startAnimation(rotateAnimation)
            }

            newQuote(0)
        }

        val favourites = getSharedPreferences("Favs", 0)
        val editor = favourites.edit()

        // When favourite is pressed
        (favourite ?: return).setOnClickListener {
            if (!(done ?: return@setOnClickListener)) {
                val like = ParticleSystem(this, 5, R.drawable.heart_full_red, 600)
                like.setSpeedRange(0.0750f, 0.0750f)
                like.setFadeOut(100)
                like.setScaleRange(0.5f, 1f)
                like.oneShot(favourite, 5)
                (favourite ?: return@setOnClickListener).isEnabled = false
                // If It Is Not Liked Already

                val pref = getSharedPreferences("List_system", 0)
                val editor = pref.edit()
                val listArr = pref.getString("Favourites", "")
                val listArrFinal = LinkedList(listArr?.split("//"))
                listArrFinal.push((quoteView ?: return@setOnClickListener).text.toString())
                val stringOut = listArrFinal.joinToString(separator = "//")
                editor.putString("Favourites", stringOut)
                editor.apply()

                done = true
                (favourite ?: return@setOnClickListener).setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.heart_full_white
                    )
                )
                (favourite ?: return@setOnClickListener).isEnabled = true
            } else {
                // If it is already liked
                // val like = ParticleSystem(this, 5, R.drawable.heart_white, 600)
                // like.setSpeedRange(0.0625f, 0.0625f)
                // like.setFadeOut(100)
                // like.oneShot(favourite, 5);
                (favourite ?: return@setOnClickListener).isEnabled = false
                val pref = getSharedPreferences("List_system", 0)
                val editor = pref.edit()
                val listArr = pref.getString("Favourites", "")
                val listArrFinal = LinkedList(listArr?.split("//"))
                val text = (quoteView ?: return@setOnClickListener).text as String
                listArrFinal.remove(text)
                val stringOut = listArrFinal.joinToString(separator = "//")
                editor.putString("Favourites", stringOut)
                editor.apply()
                done = false
                (favourite ?: return@setOnClickListener).setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.heart_white
                    )
                )
                (favourite ?: return@setOnClickListener).isEnabled = true
            }
        }

        // Get the first quote
        newQuote(quotenumber)
    }

    private fun newQuote(Quote_Number_Local: Int) {
        val text = quote.random(Quote_Number_Local)
        (quoteView ?: return).text = text
        done = false
        (favourite ?: return).setImageDrawable(
            ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.heart_white
            )
        )

        val pref = getSharedPreferences("List_system", 0)
        val editor = pref.edit()
        val listArr = pref.getString("Favourites", "")
        val listArrFinal = LinkedList(listArr?.split("//"))

        if ((listArrFinal as MutableList<String?>).contains((quoteView ?: return).text)) {
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
        quoteNumber = quote.quotenumberglobal
        noAnim = false
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
                mBundle.putString("quote", quoteNumber.toString())
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
                mBundle.putString("quote", quoteNumber.toString())
                intent2.putExtras(mBundle)
                this@MainActivity.startActivity(intent2)
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
        this.finishAffinity()
    }
}

package org.bandev.buddhaquotes

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Languages
import org.bandev.buddhaquotes.core.Preferences
import org.bandev.buddhaquotes.databinding.ActivityMain2Binding
import org.bandev.buddhaquotes.databinding.ActivityMainBinding
import java.util.*

class OldMainActivity : AppCompatActivity() {

    private var done: Boolean? = null
    private var quoteNumber: Int = 0
    private var quoteView: TextView? = null
    private val quote = Quotes()
    private var share: FloatingActionButton? = null
    private var refresh: FloatingActionButton? = null
    private var favourite: FloatingActionButton? = null
    private var noAnim = false
    private var firstPress = true
    private var heartBlack: Drawable? = null
    var toolbar: MaterialToolbar? = null

    private lateinit var binding: ActivityMainBinding
    private lateinit var binding2: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Colours().setAccentColor(this, window, resources)
        Languages().setLanguage(this)

        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)

        val sharedPreferences = getSharedPreferences("Settings", 0)
        val editor = sharedPreferences.edit()

        if (sharedPreferences.getBoolean("first_time", true)) {
            editor.putBoolean("first_time", false)
            editor.apply()
            val i = Intent(this, AppIntro::class.java)
            startActivity(i)
        }

        val shapesMode = Preferences().shapesMode(this)
        if (shapesMode) {
            binding2 = ActivityMain2Binding.inflate(layoutInflater)
            setContentView(binding2.root)
            window.navigationBarColor = Color.TRANSPARENT
            window.statusBarColor = Color.TRANSPARENT
        } else {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        @Suppress("DEPRECATION")
                        window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        window.navigationBarColor = Color.TRANSPARENT
                    }
                }
                Configuration.UI_MODE_NIGHT_YES -> {
                    window.navigationBarColor = Color.TRANSPARENT
                }
            }
        }

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        val quotenumber = sharedPref.getInt("Quote_Number", 0)

        refresh = findViewById(R.id.refresh)
        share = findViewById(R.id.share)
        favourite = findViewById(R.id.favourite)
        toolbar = findViewById(R.id.toolbar)
        heartBlack = ContextCompat.getDrawable(this, R.drawable.ic_list)
        quoteView = findViewById(R.id.quote)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Sets up toolbar and adds icons
        setSupportActionBar(toolbar)
        (supportActionBar ?: return).setDisplayHomeAsUpEnabled(true)

        setTheme(R.style.AppTheme_Blue)
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

        val buttons: View = findViewById(R.id.buttons)

        setOnApplyWindowInsetsListener(buttons) { v: View, insets: WindowInsetsCompat ->
            val params = v.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = insets.systemWindowInsetBottom + 30
            insets.consumeSystemWindowInsets()
        }

        findViewById<FloatingActionButton>(R.id.share).setOnClickListener {
            val sendIntent: Intent = Intent().apply {

                action = Intent.ACTION_SEND
                val text = quoteView?.text.toString() + "\n\n~Gautama Buddha"

                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            window.decorView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        var navBarHeight = 0
        val resourceId2 = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId2 > 0) {
            navBarHeight = resources.getDimensionPixelSize(resourceId)
        }

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
            window.decorView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
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

        // When favourite is pressed
        (favourite ?: return).setOnClickListener {
            window.decorView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            if (!(done ?: return@setOnClickListener)) {
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
                        this@OldMainActivity,
                        R.drawable.heart_full_white
                    )
                )
                (favourite ?: return@setOnClickListener).isEnabled = true
            } else {
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
                        this@OldMainActivity,
                        R.drawable.heart_white
                    )
                )
                (favourite ?: return@setOnClickListener).isEnabled = true
            }
        }

        newQuote(quotenumber)
    }

    private fun newQuote(Quote_Number_Local: Int) {
        val text = quote.random(Quote_Number_Local, this)
        (quoteView ?: return).text = text
        done = false
        (favourite ?: return).setImageDrawable(
            ContextCompat.getDrawable(
                this@OldMainActivity,
                R.drawable.heart_white
            )
        )

        val pref = getSharedPreferences("List_system", 0)
        val listArr = pref.getString("Favourites", "")
        val listArrFinal = LinkedList(listArr?.split("//"))

        if ((listArrFinal as MutableList<String?>).contains((quoteView ?: return).text)) {
            done = true
            (favourite ?: return).setImageDrawable(
                ContextCompat.getDrawable(
                    this@OldMainActivity,
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
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_main_setting -> {
                val myIntent =
                    Intent(this@OldMainActivity, Settings::class.java)
                val mBundle = Bundle()
                mBundle.putString("quote", quoteNumber.toString())
                myIntent.putExtras(mBundle)
                this@OldMainActivity.startActivity(myIntent)
                overridePendingTransition(
                    R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left
                )
                true
            }
            android.R.id.home -> {
                val intent2 = Intent(this@OldMainActivity, YourLists::class.java)
                val mBundle = Bundle()
                mBundle.putString("quote", quoteNumber.toString())
                intent2.putExtras(mBundle)
                this@OldMainActivity.startActivity(intent2)
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

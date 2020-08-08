package org.bandev.buddhaquotes

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.security.AccessController.getContext
import java.util.*

class Info_Panel : AppCompatActivity() {

    var list: List<String?>? = null
    lateinit var favs: Array<String?>
    lateinit var array: Array<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info__panel)

        val myToolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(myToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.colorPrimary)
        }
        val intent = getIntent()
        val quote = intent.getStringExtra("quote")
        val textview = findViewById<TextView>(R.id.text)
        textview.setText(quote)

        //Is user using night mode
        val nightModeFlags = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
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
        favs = arrayOf<String?>(Favourites.getString("fav", ""))!!
        var array = favs[0]!!.split("//VADER//".toRegex()).toTypedArray()
        val list: MutableList<String> = ArrayList(Arrays.asList(*array))


        val fab = findViewById<FloatingActionButton>(R.id.delete)



        fab.setOnClickListener {
            favs = arrayOf<String?>(Favourites.getString("fav", ""))!!
            var array = favs[0]!!.split("//VADER//".toRegex()).toTypedArray()
            val list: MutableList<String> = ArrayList(Arrays.asList(*array))
            val text = textview!!.getText() as String
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
            editor.commit()
            Toast.makeText(this@Info_Panel, "Removed from favourites", Toast.LENGTH_SHORT).show()

            val myIntent = Intent(this@Info_Panel, favourites::class.java)
            this@Info_Panel.startActivity(myIntent)
            overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left)

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
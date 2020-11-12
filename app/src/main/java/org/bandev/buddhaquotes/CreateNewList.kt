package org.bandev.buddhaquotes

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import org.bandev.buddhaquotes.core.Colours
import org.bandev.buddhaquotes.core.Compatibility
import org.bandev.buddhaquotes.core.Languages

class CreateNewList : AppCompatActivity() {

    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var back: Drawable
    private lateinit var go: Button
    lateinit var name: EditText
    var text: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Colours().setAccentColor(this, window)
        Compatibility().setNavigationBarColour(this, window, resources)
        Languages().setLanguage(this)
        setContentView(R.layout.activity_create_new_list)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Setup toolbar
        back = (ContextCompat.getDrawable(this, R.drawable.ic_arrow_back) ?: return)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.title = "Create New List"
        toolbar.navigationIcon = back

        //Setup button & onclick
        go = findViewById(R.id.go)
        go.setOnClickListener {
            val nameBox = findViewById<TextInputLayout>(R.id.name_box)
            name = findViewById(R.id.name)
            text = name.text.toString()


            val error = getError(text)

            if (error != "safe") {
                nameBox.error = error
            } else {
                //Add new list to MASTER_LIST & create a list for itself
                window.decorView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                val pref = getSharedPreferences("List_system", 0)
                val editor = pref.edit()
                editor.putString(text, "null")
                editor.putString(
                    "MASTER_LIST",
                    (pref.getString("MASTER_LIST", "Favourites") + "//" + text)
                )
                editor.apply()

                //Leave & take users back to favourites
                val myIntent = Intent(this, YourLists::class.java)
                this.startActivity(myIntent)
                finish()
            }


        }
    }

    private fun getError(input: String): String {
        val pref = getSharedPreferences("List_system", 0)

        when {
            input == "" -> {
                return "Cannot be blank"
            }
            input.contains("//") -> {
                return "Cannot contain //"
            }
            input == "Favourites" -> {
                return ":( stop trynna break the app"
            }
            input == "favourites" -> {
                return ":( stop trynna break the app"
            }
            pref.getString("MASTER_LIST", "Favourites")!!.contains(input) -> {
                return "There is already a list named $input"
            }
            else -> {
                return "safe"
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val myIntent = Intent(this, YourLists::class.java)
                this.startActivity(myIntent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val myIntent = Intent(this, YourLists::class.java)
        this.startActivity(myIntent)
        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val myIntent = Intent(this, YourLists::class.java)
        this.startActivity(myIntent)
        finish()
    }
}
package org.bandev.buddhaquotes

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.MaterialToolbar
import java.util.*

class AddList : AppCompatActivity() {

    lateinit var toolbar: MaterialToolbar
    private lateinit var back: Drawable
    private lateinit var go: Button
    private lateinit var number: EditText
    private var num: String = ""
    private var quote = Quotes()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addlist)

        //Set status bar colors
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorTop)

        val list = (intent.getStringExtra("list") ?: return).toString()

        //Setup toolbar
        back = (ContextCompat.getDrawable(this, R.drawable.ic_arrow_back) ?: return)
        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Add to List"
        toolbar.navigationIcon = back

        //Setup button & onclick
        go = findViewById(R.id.go)
        go.setOnClickListener {
            number = findViewById(R.id.number)
            num = number.text.toString()

            val intent2 = Intent(this, ScrollingActivity::class.java)
            val mBundle = Bundle()

            val pref = getSharedPreferences("List_system", 0)
            val editor = pref.edit()
            val listArr = pref.getString(list, "")
            val listArrFinal = LinkedList(listArr?.split("//"))
            listArrFinal.push(quote.random(num.toInt()))
            val stringOut = listArrFinal.joinToString(separator = "//")

            Toast.makeText(this, stringOut, LENGTH_SHORT).show()

            editor.putString(list, stringOut)
            editor.apply()

            mBundle.putString("list", list)
            intent2.putExtras(mBundle)
            this.startActivity(intent2)
            overridePendingTransition(
                R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right
            )
        }
    }
}
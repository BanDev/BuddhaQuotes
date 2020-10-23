package org.bandev.buddhaquotes

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.MaterialToolbar

class AddList : AppCompatActivity() {

    lateinit var toolbar: MaterialToolbar
    private lateinit var back: Drawable
    private lateinit var go: Button
    private lateinit var number: EditText
    private var num: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addlist)

        //Set status bar colors
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorTop)

        val list = (intent.getStringExtra("list") ?: return).toString()

        //Setup toolbar
        back = (ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_24) ?: return)
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
            mBundle.putString("quote", num)
            mBundle.putString("list", list)
            mBundle.putBoolean("safe", true)
            intent2.putExtras(mBundle)
            this.startActivity(intent2)
            overridePendingTransition(
                R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right
            )
        }
    }
}
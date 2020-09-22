package org.bandev.buddhaquotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.TypedValue
import android.view.View
import android.view.WindowInsets
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.preference.ListPreference
import androidx.preference.Preference
import com.google.android.material.slider.Slider

class Slide_3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_3)


        WindowCompat.setDecorFitsSystemWindows(window, false)

        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

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

        var navBarHeight = 0
        val resourceId2 = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId2 > 0) {
            navBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        var slider:Slider = findViewById(R.id.slider)
        var button: Button = findViewById(R.id.button)
        var text2: TextView = findViewById(R.id.text)

        slider.addOnChangeListener { slider, value, fromUser ->
            // Responds to when slider's value is changed
            val pref = this.getSharedPreferences("Settings", 0)
            val editor = pref.edit()
            val fontsize = when (value) {
                0f -> 30f
                20f -> 50f
                else -> 40f
            }

            text2.setTextSize(fontsize);

            val string:String = when(fontsize){
                30f -> "sm"
                50f -> "lg"
                else -> "md"
            }
            editor.putString("text_size", string)
            editor.commit()
        }

        button?.setOnClickListener {
            if(slider.value == 10f){
                val pref = this.getSharedPreferences("Settings", 0)
                val editor = pref.edit()
                editor.putString("text_size", "md")
                editor.commit()
            }

            val myIntent = Intent(this@Slide_3, Slide_4::class.java)
            this@Slide_3.startActivity(myIntent)
            overridePendingTransition(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left
            )
        }




        window.navigationBarColor = resources.getColor(R.color.transparent)
        window.statusBarColor = resources.getColor(R.color.colorPrimary)
    }


}
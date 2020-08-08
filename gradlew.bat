package org.bandev.buddhaquotes

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    val done: Boolean? = null

    val textview: TextView? = null
    val quote = Quote()
    val fab: FloatingActionButton? = null
    val fab2: FloatingActionButton? = null
    val list: List<String?>? = null
    var favs: Array<String?>
    var array: Array<String?>
    val font_size: String? = null
    val DEBUG_TAG = "Gestures"
    var toolbar: Toolbar? = null
    val mDetector: GestureDetectorCompat? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //Setup Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)



        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.heart_black);
        myToolbar.setNavigationIcon(drawable);
        setSupportActionBar(myToolbar);


        textview = findViewById(R.id.text);
        fab = findViewById(R.id.refresh);
        fab2 = findViewById(R.id.fav2);



        SharedPreferences sharedPreferences = getSharedPreferences("Settings", 0);
        String text_size = sharedPreferences.getString("text_size", "md");
        Log.d("Size", text_size);
        switch(text_size){
            case "sm":
                font_size = "30";
                break;
            case "lg":
                font_size = "50";
                break;
            default:
                font_size = "40";
                break;
        }
        Log.d("text_size", font_size);

        if (Build.VERSION.SDK_INT >= Build.VERSION_
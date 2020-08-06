package org.bandev.buddhaquotes;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.preference.ListPreference;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Locale.US;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    Boolean done;

    TextView textview;
    Quote quote = new Quote();
    FloatingActionButton fab;
    FloatingActionButton fab2;
    List<String> list;
    String[] favs;
    String[] array;
    String font_size;
    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }

        textview.setTextSize(Float.parseFloat(font_size));

        //Is user using night mode
        int nightModeFlags = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                //Yes
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                //No
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                //Who knows? Assume they are not
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                break;
        }
        newQuote();

        final SharedPreferences pref = this.getSharedPreferences("Favs", 0);
        final SharedPreferences.Editor editor = pref.edit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newQuote();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!done) {
                    if (favs[0] != "") {
                        favs[0] = textview.getText() + "//VADER//" + favs[0];
                    } else {
                        favs[0] = (String) textview.getText() + "//VADER//";
                    }
                    Toast.makeText(MainActivity.this, "Added to favourites", Toast.LENGTH_SHORT).show();
                    editor.putString("fav", favs[0]);
                    editor.commit();
                    Log.d("Array", favs[0]);
                    done = true;
                            fab2.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.heart_full_white));
                }else{
                    fab2.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.like_white_empty));
                    favs = new String[]{pref.getString("fav", "")};

                    String[] array = favs[0].split("//VADER//");
                    List<String> list = new ArrayList<String>(Arrays.asList(array));
                    String text = (String)textview.getText();
                    list.remove(text);
                    array = list.toArray(new String[0]);

                    String sb = "";

                    for(int i = 0; i < array.length; i++) {
                        sb = sb + array[i] + "//VADER//";
                    }
                    Log.d("Array", sb);
                    editor.putString("fav", sb);
                    editor.commit();
                    Toast.makeText(MainActivity.this, "Removed from favourites", Toast.LENGTH_SHORT).show();

                }
            }
        });

        mDetector = new GestureDetectorCompat(this,this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);
        // Set the gesture detector as the double tap
        // listener.
    }

    void newQuote(){
        String text = quote.random();
        textview.setText(text);
        done = false;
        fab2.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_baseline_stars_24));
        final SharedPreferences pref = this.getSharedPreferences("Favs", 0);
        final SharedPreferences.Editor editor = pref.edit();
        favs = new String[]{pref.getString("fav", "")};
        array = favs[0].split("//VADER//");

        list = Arrays.asList(array);
        if(list.contains(textview.getText())){
            done = true;
            fab2.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.heart_full_white));
        }
    }


    //Create The Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    //When Option Is Clicked
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_setting:
                Intent myIntent = new Intent(MainActivity.this, settings.class);
                MainActivity.this.startActivity(myIntent);
                this.overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                return true;
            case android.R.id.home:
                Intent intent2 = new Intent(MainActivity.this, favourites.class);
                MainActivity.this.startActivity(intent2);
                this.overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        newQuote();
        Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {

        Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        return true;
    }

}
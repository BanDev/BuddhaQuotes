package org.bandev.buddhaquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import static java.util.Locale.US;

public class MainActivity extends AppCompatActivity{

    TextView textview;
    Quote quote = new Quote();
    FloatingActionButton fab;
    String font_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        textview = findViewById(R.id.text);
        fab = findViewById(R.id.refresh);



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
            case "xlg":
                font_size = "60";
                break;
            default:
                font_size = "40";
                break;
        }
        Log.d("text_size", font_size);

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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newQuote();
            }
        });





    }

    void newQuote(){
        String text = quote.random();
        textview.setText(text);
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
                MainActivity.this.startActivity(myIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
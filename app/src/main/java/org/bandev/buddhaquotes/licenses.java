package org.bandev.buddhaquotes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class licenses extends AppCompatActivity {
    Boolean from_settings = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licenses);
        String url2;
        Bundle b = getIntent().getExtras();// or other values

        switch(b.getString("from")){
            case "Material Design Lite":
                url2 = "file:///android_asset/mdl.html";
                break;
            case "Material Design Icons":
                url2 = "file:///android_asset/mdi.html";
                break;
            default:
                url2 = "file:///android_asset/license.html";
                from_settings = true;
                break;
        }

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionBar = getSupportActionBar();
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int nightModeFlags = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                break;
        }

        final ProgressBar progress = findViewById(R.id.loader);

        final WebView browser = (WebView) findViewById(R.id.webview);
        browser.loadUrl(url2);
        browser.setVisibility(View.GONE);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                browser.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        if(from_settings){
            Intent i = new Intent(this, settings.class);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        }else {
            Intent i = new Intent(this, oss_libraries.class);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // add your animation
        if(from_settings){
            Intent i = new Intent(this, settings.class);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        }else {
            Intent i = new Intent(this, oss_libraries.class);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(from_settings){
                    Intent i = new Intent(this, settings.class);
                    startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                }else {
                    Intent i = new Intent(this, oss_libraries.class);
                    startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
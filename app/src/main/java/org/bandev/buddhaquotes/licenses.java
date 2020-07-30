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

import java.util.concurrent.TimeUnit;

public class licenses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licenses);

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
        browser.loadUrl("file:///android_asset/license.html");
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
        Intent i = new Intent(this, settings.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(licenses.this).toBundle());
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // add your animation
        Intent i = new Intent(this, settings.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(licenses.this).toBundle());
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(this, settings.class);
                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(licenses.this).toBundle());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
package org.bandev.buddhaquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

public class settingsopener extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingsopener);

        startActivityForResult(new Intent(Settings.ACTION_DISPLAY_SETTINGS), 0);
        finish();

    }
}
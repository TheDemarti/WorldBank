package it.giudevo.worldbank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import it.giudevo.worldbank.searchApi.SearchByArg;
import it.giudevo.worldbank.searchApi.SearchByCountry;

public class MainActivity extends AppCompatActivity {
    public boolean choice;
    public boolean theme_boolean; //variabile usata per identificate il tema dell'activity corrente
    public SharedPreferences mPref;
    MenuItem menu;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //SharedPreferences viene usata per salvare lo stato di un'activity (dopo alcune modifiche)
        // prima della della chiusura e per ripristinarlo alla riapertura dopo
        mPref = getSharedPreferences("THEME", 0);
        theme_boolean = mPref.getBoolean("theme_boolean", true);

        SetTheme(theme_boolean);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Holder();

    }



    private void SetTheme(boolean bool) {
        if(bool){
            setTheme(R.style.AppTheme);
        }
        else{
            setTheme(R.style.Theme_MaterialComponents_DayNight_DarkActionBar);
        }
    }

    class Holder implements View.OnClickListener {

        Button btnCountry, btnArg, btnOff;
        TextView tvPowered;

        Holder(){
        btnArg = findViewById(R.id.btnArg);
        btnCountry = findViewById(R.id.btnCountry);
        btnOff = findViewById(R.id.btnFavorites);
        tvPowered = findViewById(R.id.tvPowered);
        tvPowered.setMovementMethod(LinkMovementMethod.getInstance());
        btnCountry.setOnClickListener(this);
        btnArg.setOnClickListener(this);
        btnOff.setOnClickListener(this);
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
        }

        @Override
        public void onClick(View v) {
             if(v.getId() == R.id.btnCountry){
                 choice = true;
                 Intent intent = new Intent(MainActivity.this, SearchByCountry.class);
                 intent.putExtra("choice", choice);
                 MainActivity.this.startActivity(intent);
             }
             if(v.getId() == R.id.btnArg){
                 choice = false;
                 Intent intent = new Intent(MainActivity.this, SearchByArg.class);
                 intent.putExtra("choice", choice);
                 MainActivity.this.startActivity(intent);
             }
             if(v.getId() == R.id.btnFavorites){
                 Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                 startActivity(intent);
             }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_theme: {
                if(theme_boolean){
                    Theme.changeToTheme(this, Theme.THEME_FIRST);
                    Theme.onActivityCreateSetTheme(this);
                    theme_boolean = false;
                }
                else{
                    Theme.changeToTheme(this, Theme.THEME_SECOND);
                    Theme.onActivityCreateSetTheme(this);
                    theme_boolean = true;
                }
                break;
            }
        }
        //una volta impostato, il tema viene salvatoin una variabile di tipo shared preferences così da poter essere utilzzabile successivamente
        SharedPreferences mPrefs = getSharedPreferences("THEME", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putBoolean("theme_boolean", theme_boolean).commit();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(@NonNull Menu menu) {
        invalidateOptionsMenu();
        if(theme_boolean) {
            menu.findItem(R.id.change_theme).setIcon(R.drawable.ic_outline_moon);
        }
        else{
            menu.findItem(R.id.change_theme).setIcon(R.drawable.ic_outline_sun);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}


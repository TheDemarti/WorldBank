package it.giudevo.worldbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import it.giudevo.worldbank.searchApi.SearchByArg;
import it.giudevo.worldbank.searchApi.SearchByCountry;

public class MainActivity extends AppCompatActivity {
    public boolean choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Holder();

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
}


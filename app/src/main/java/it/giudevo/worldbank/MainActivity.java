package it.giudevo.worldbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import it.giudevo.worldbank.searchApi.SearchByArg;
import it.giudevo.worldbank.searchApi.SearchByCountryFirst;

public class MainActivity extends AppCompatActivity {
    public boolean intero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Holder();
    }

    class Holder implements View.OnClickListener {

        Button btnCountry, btnArg, btnOff;

        Holder(){
        btnArg = findViewById(R.id.btnArg);
        btnCountry = findViewById(R.id.btnCountry);
        btnOff= findViewById(R.id.btnFavorites);

        btnCountry.setOnClickListener(this);
        btnArg.setOnClickListener(this);
        btnOff.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
             if(v.getId() == R.id.btnCountry){
                 Intent intent = new Intent(MainActivity.this, SearchByCountryFirst.class);
                 MainActivity.this.startActivity(intent);
             }
             if(v.getId() == R.id.btnArg){
                 intero = false;
                 Intent intent = new Intent(MainActivity.this, SearchByArg.class);
                 intent.putExtra("intero", intero);
                 MainActivity.this.startActivity(intent);
             }
             if(v.getId() == R.id.btnFavorites){
                 Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                 startActivity(intent);
             }
        }
    }
}


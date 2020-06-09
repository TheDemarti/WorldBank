package it.giudevo.worldbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import it.giudevo.worldbank.searchapi.SearchByCountry;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Holder();
    }




 class Holder implements View.OnClickListener{
   Button btnCountry, btnArg, btnOff;

        Holder(){
        btnArg = findViewById(R.id.btnArg);
        btnCountry = findViewById(R.id.btnCountry);
        btnOff= findViewById(R.id.btnOff);

        }


     @Override
     public void onClick(View v) {
         if(v.getId() == R.id.btnArg){
             Intent intent = new Intent(MainActivity.this, SearchByCountry.class);

             SearchByCountry.this.startActivity(intent);
         }
     }
 }
}


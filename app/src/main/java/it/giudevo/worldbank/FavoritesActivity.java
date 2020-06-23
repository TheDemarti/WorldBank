package it.giudevo.worldbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        new Holder();
    }

    class Holder implements View.OnClickListener {

        Button btnData, btnGraph;

        Holder(){

            btnData = findViewById(R.id.btnData);
            btnGraph = findViewById(R.id.btnGraph);

            btnData.setOnClickListener(this);
            btnGraph.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
//
//            if(v.getId() == R.id.btnData) {
//                Intent intent = new Intent(FavoritesActivity.this);
//                startActivity(intent);
//            }
//
//            if(v.getId() == R.id.btnGraph) {
//                Intent intent = new Intent(FavoritesActivity.this);
//                startActivity(intent);
//            }
//
        }
    }
}
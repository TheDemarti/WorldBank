package it.giudevo.worldbank.localDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import it.giudevo.worldbank.R;

public class showDetails extends AppCompatActivity {

    DataBaseHelper myDB;
    ListView lvDet;
    TextView tvDetails;
    public boolean theme_boolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences mPref = getSharedPreferences("THEME", 0);
        theme_boolean = mPref.getBoolean("theme_boolean", true);

        SetTheme(theme_boolean);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        Intent data = getIntent();
        String dat = data.getStringExtra("details");

        Log.w("id show Details", dat);

        tvDetails = findViewById(R.id.tvDetails);
        lvDet = findViewById(R.id.lvDet);
        myDB = new DataBaseHelper(this);

        tvDetails.setText(dat);

        ArrayList<String> theList = new ArrayList<>();
        final Cursor det = myDB.getOneData(dat);

        if (det.getCount() == 0) {
            Toast.makeText(this, R.string.no_content, Toast.LENGTH_LONG).show();
        } else {
            while (det.moveToNext()) {
                theList.add( getString(R.string.data_info) + det.getString(2) + " ----> " + getString(R.string.value) + det.getString(3));

                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                lvDet.setAdapter(listAdapter);

            }
        }
        myDB.close();
    }

    private void SetTheme(boolean bool) {
        if(bool){
            setTheme(R.style.AppTheme);
        }
        else{
            setTheme(R.style.Theme_MaterialComponents_DayNight_DarkActionBar);
        }
    }
}
package it.giudevo.worldbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class showDetails extends AppCompatActivity {

    DataBaseHelper myDB;
    ListView lvDet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        Intent data = getIntent();
        //data.getStringExtra("details");

        //Log.w("CA", String.valueOf(data.getStringExtra("details")));


        lvDet = findViewById(R.id.lvDet);
        myDB = new DataBaseHelper(this);

        //populate an ArrayList<String> from the database and then view it
        ArrayList<String> theList = new ArrayList<>();
        final Cursor det = myDB.getOneData(String.valueOf(data.getStringExtra("details")));
        if (det.getCount() == 0) {
            Toast.makeText(this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        } else {
            while (det.moveToNext()) {
                theList.add(det.getString(1) + det.getString(2));
                Log.w("CA", det.getString(1));
                //theList.add(data.getString(2));

                //data.getColumnIndex("DATE");
                //data.getString(1);


                //Log.w("CA", String.valueOf(res));


                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                lvDet.setAdapter(listAdapter);

            }
        }
    }
}
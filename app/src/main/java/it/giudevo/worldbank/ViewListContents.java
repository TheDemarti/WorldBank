package it.giudevo.worldbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.giudevo.worldbank.database.Arguments.Arguments;
import it.giudevo.worldbank.database.Final.Final;
import it.giudevo.worldbank.searchApi.FinalAdapter;
import it.giudevo.worldbank.searchApi.FinalSearch;
import it.giudevo.worldbank.searchApi.SearchByArg;
import it.giudevo.worldbank.searchApi.SearchByIndicator;

public class ViewListContents extends AppCompatActivity implements View.OnClickListener {

    DataBaseHelper myDB;
    ListView lvFav;
    public Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_contents);

        lvFav = findViewById(R.id.lvFav);
        myDB = new DataBaseHelper(this);

        //populate an ArrayList<String> from the database and then view it
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getListContents();
        if (data.getCount() == 0) {
            Toast.makeText(this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(0) + data.getString(1));
                //Log.w("CA", data.getString(0));
                //theList.add(data.getString(2));

                //data.getColumnIndex("DATE");
                //data.getString(1);
                //res = myDB.getOneData("BEL");

                //Log.w("CA", String.valueOf(res));


                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                lvFav.setAdapter(listAdapter);
                ///////////////////////////////////////////////////////////
                lvFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String fn= (String) parent.getAdapter().getItem(position);
                        Log.w("CA", String.valueOf(fn));
                    }
                });
                ///////////////////////////////////////
                //Log.w("CA", "////////////" + theList.get(0));
                //Log.w("CA", "/////" + listAdapter.getItem(0));
            }
        }
    }
    @Override
    public void onClick (View v){

    }
}
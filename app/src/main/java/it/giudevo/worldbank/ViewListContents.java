package it.giudevo.worldbank;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ViewListContents extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    DataBaseHelper myDB;
    ListView lvFav;
    public Cursor res;
    public Cursor data;
    private SparseBooleanArray selectedList = new SparseBooleanArray();
    public ArrayList<String> theList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_contents);

        lvFav = findViewById(R.id.lvFav);
        myDB = new DataBaseHelper(this);

        //populate an ArrayList<String> from the database and then view it
        theList = new ArrayList<>();
        //public final Cursor
        data = myDB.getListContents();
        if (data.getCount() == 0) {
            Toast.makeText(this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(1));
                Log.w("CA", data.getString(1));

                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                lvFav.setAdapter(listAdapter);
                lvFav.setOnItemLongClickListener(this);
                lvFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ViewListContents.this, showDetails.class);
                        //parent.getAdapter().getItem(position);
                        intent.putExtra("details", String.valueOf(parent.getAdapter().getItem(position)));
                        //Log.w("CA", data.getString(0));
                        ViewListContents.this.startActivity(intent);
                        Log.w("CA", (String) parent.getAdapter().getItem(position));
                    }
                });

            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

//        boolean isSelected = selectedList.get(position,false);
//        if(isSelected) {
//            parent.setSelected(false);
//            //setTypeface(null, Typeface.BOLD);
//        //  parent.getChildAt(position).setBackgroundColor(Color.WHITE);
//        } else {
//            parent.setSelected(true);
//            //setTypeface(null, Typeface.NORMAL);
//            parent.getChildAt(position).setBackgroundColor(Color.GRAY);
//        }

        boolean isSel = theList.get(position).isEmpty();


        if (isSel) {
            parent.setSelected(false);
            //selectedList.delete(position);
            parent.getChildAt(position).setBackgroundColor(Color.WHITE);
        } else {
            parent.setSelected(true);
            selectedList.put(position, true);
            parent.getChildAt(position).setBackgroundColor(Color.GREEN);
            String idd = theList.get(position);
            myDB.deletedata(idd);
            theList.remove(position);
        }
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

                case R.id.delete_all:{
                    Log.w("accesso a  del", "granted");
                    for (int index = theList.size() - 1; index >= 0; index--) {
                    String id = theList.get(index);
                    myDB.deletedata(id);
                    theList.remove(index);
                   }
                selectedList.clear();
                break;
                }


                case R.id.unselect_all:{
                selectedList.clear();
                break;}


                case R.id.select_all:{
                for (int i = 0; i < theList.size(); i++) {
                    selectedList.put(i, true);
                }
                break;}

        }
        return true;
    }
}

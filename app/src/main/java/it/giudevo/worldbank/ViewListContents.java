package it.giudevo.worldbank;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ViewListContents extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    DataBaseHelper myDB;
    ListView lvFav;
    public Cursor res;
    public Cursor data;
    public SparseBooleanArray selectedList = new SparseBooleanArray();
    public ArrayList<String> theList;
    public ArrayAdapter<String> listAdapter;

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

                Log.w("CA", "RESTORE ADAPTER");
                listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                lvFav.setAdapter(listAdapter);
                lvFav.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        boolean isSelected = selectedList.get(position,false);
                        if(isSelected) {
                            parent.setSelected(true);
                            selectedList.delete(position);
                            parent.getChildAt(position).setBackgroundColor(Color.WHITE);
                        } else {
                            parent.setSelected(false);
                            selectedList.put(position, true);
                            parent.getChildAt(position).setBackgroundColor(Color.GRAY);
                        }
                        Log.w("CA", String.valueOf(selectedList.size()));
                        return true;
                    }
                });
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
            Log.w("CA", "la lista e" + theList);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


        boolean isSelected = selectedList.get(position,false);
        if(isSelected) {
            parent.setSelected(true);
            selectedList.delete(position);
            parent.getChildAt(position).setBackgroundColor(Color.WHITE);
        } else {
            parent.setSelected(false);
            selectedList.put(position, true);
            parent.getChildAt(position).setBackgroundColor(Color.GRAY);
        }
        Log.w("CA", String.valueOf(selectedList.size()));

//        boolean isSel = theList.get(position).isEmpty();
//
//        if (isSel) {
//            parent.setSelected(false);
//            selectedList.delete(position);/////////////////////////////////////////
//            parent.getChildAt(position).setBackgroundColor(Color.RED);
//        } else {
//            parent.setSelected(true);
//            selectedList.put(position, true);
//            parent.getChildAt(position).setBackgroundColor(Color.GREEN);
//            //String idd = theList.get(position);
//            //myDB.deletedata(idd);
//            //theList.remove(position);
//        }
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
                        if(selectedList.get(index)) {
                            String id = theList.get(index);
                            myDB.deletedata(id);
                            theList.remove(index);
                            //theList.clear();

                        }
                    }
                    selectedList.clear();
                    listAdapter.notifyDataSetChanged();
                    Log.w("CA", "DELETE");
                break;
                }


                case R.id.unselect_all:{
                    for(int i = 0; i < theList.size()-1; i++){
                        //theList.set(i);
                        selectedList.delete(i);
                        //theList.set(i);
                        //theList.clear();

                    }
                    selectedList.clear();
                    listAdapter.notifyDataSetChanged();
                    break;
                }


                case R.id.select_all:{
                    for (int i = 0; i < theList.size(); i++) {
                        //Toast.makeText(this, selectedList.size(), Toast.LENGTH_LONG).show();
                        Log.w("CA", selectedList.size() + "aaaaaaaaaaaaaaaaaaaaaaaaaa");
                        selectedList.put(i, true);
                    }
                    selectedList.clear();
                    listAdapter.notifyDataSetChanged();
                    //listAdapter.notifyDataSetInvalidated();
                    //listAdapter.setNotifyOnChange(true);
                    break;
                }

        }
        return true;
    }

}

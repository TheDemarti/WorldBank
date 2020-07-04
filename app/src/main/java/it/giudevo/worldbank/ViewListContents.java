package it.giudevo.worldbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import java.util.List;
import java.util.Objects;

import it.giudevo.worldbank.database.Arguments.Arguments;
import it.giudevo.worldbank.database.Final.Final;
import it.giudevo.worldbank.searchApi.FinalAdapter;
import it.giudevo.worldbank.searchApi.FinalSearch;
import it.giudevo.worldbank.searchApi.SearchByArg;
import it.giudevo.worldbank.searchApi.SearchByIndicator;

public class ViewListContents extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    DataBaseHelper myDB;
    ListView lvFav;
    public Cursor res;
    public Cursor data;
    private SparseBooleanArray selectedList = new SparseBooleanArray();
    private SelectMode mListener;
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
//            parent.setSelected(true);
//            //setTypeface(null, Typeface.BOLD);
//            parent.setBackgroundColor(Color.YELLOW);
//            parent.getAdapter().getItem(position).
//        } else {
//            parent.setSelected(false);
//            //setTypeface(null, Typeface.NORMAL);
//            parent.setBackgroundColor(Color.GRAY);
//        }



        boolean isSel = selectedList.get(position, false);
        if(isSel) {
            parent.setSelected(false);
            selectedList.delete(position);
        } else {
            parent.setSelected(true);
            selectedList.put(position, true);
        }
        if (mListener != null) {
          mListener.onSelect(selectedList.size());  // Callback verso MainActivity
        }
        //notifyDataSetChanged();
        return true;
    }


    private class SelectMode {
        void onSelect(int size) {
            if (mActionMode != null) {
                if(size == 0) { // No Selections
                    mActionMode.finish(); // Close Menu
                }
                return;
            }
            mActionMode = startSupportActionMode(mActionModeCallback);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public ActionMode mActionMode;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_details, menu);
            return true;
      }
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete_all:
                    deleteAllSelected();
                    mode.finish();
                    return true;
                case R.id.unselect_all:
                    deselectAll();
                    mode.finish();
                    return true;
                case R.id.select_all:
                    selectAll();
                    return true;
                default:
                    return false;
            }
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    public void deselectAll() {
        selectedList.clear();

        //notifyDataSetChanged();
    }

    public void selectAll() {
        for(int i = 0; i < theList.size(); i++) {
            selectedList.put(i, true);
        }
        //notifyDataSetChanged();
    }

    public void deleteAllSelected() {
        if (selectedList.size()==0) { return; }
        for (int index = theList.size()-1; index >=0; index--) {
            if (selectedList.get(index,false)) {
                remove(index);
            }
        }
        selectedList.clear();
    }

    private void remove(int position) {
        theList.remove(position);
        //notifyItemRemoved(position);
    }
}
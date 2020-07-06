package it.giudevo.worldbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import it.giudevo.worldbank.database.Arguments.Arguments;
import it.giudevo.worldbank.searchApi.FinalAdapter;
import it.giudevo.worldbank.searchApi.SearchByCountry;


public class ViewListContents extends AppCompatActivity {

    DataBaseHelper myDB;
    RecyclerView rvFav;
    public Cursor res;
    public Cursor data;
    public SparseBooleanArray selectedList = new SparseBooleanArray();
    public ArrayList<String> theList;
    //public ArrayAdapter<String> listAdapter;
    public FinAdapter finAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_contents);

        rvFav = findViewById(R.id.rvFav);
        myDB = new DataBaseHelper(this);

        //populate an ArrayList<String> from the database and then view it
        theList = new ArrayList<>();
        //public final Cursor
        data = myDB.getListContents();
        if (data.getCount() == 0) {
            Toast.makeText(this, R.string.no_content, Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(1));
                Log.w("CA", data.getString(1));


                rvFav.setLayoutManager(new LinearLayoutManager((this)));
                finAdapter = new FinAdapter(data);
                rvFav.setAdapter(finAdapter);


//                listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
//                Log.w("CA", "RESTORE ADAPTER");
//                rvFav.setAdapter(listAdapter);
//                Log.w("CA", "RESTORE ADAPTER");
//                rvFav.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                    @Override
//                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                        boolean isSelected = selectedList.get(position,false);
//                        if(isSelected) {
//                            parent.setSelected(false);
//                            selectedList.delete(position);
//                            parent.getChildAt(position).setBackgroundColor(Color.WHITE);
//                        } else {
//                            parent.setSelected(true);
//                            selectedList.put(position, true);
//                            parent.getChildAt(position).setBackgroundColor(Color.GRAY);
//                        }
//                        Log.w("CA", String.valueOf(selectedList.size()));
//                        listAdapter.notifyDataSetChanged();
//                        return true;
//                    }
//                });
//                rvFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent(ViewListContents.this, showDetails.class);
//                        //parent.getAdapter().getItem(position);
//                        intent.putExtra("details", String.valueOf(parent.getAdapter().getItem(position)));
//                        //Log.w("CA", data.getString(0));
//                        ViewListContents.this.startActivity(intent);
//                        Log.w("CA", (String) parent.getAdapter().getItem(position));
//                    }
//                });

            }
            Log.w("CA", "la lista e" + theList);
        }
    }

//    @Override
//    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//        boolean isSelected = selectedList.get(position,false);
//        if(isSelected) {
//            parent.setSelected(true);
//            selectedList.delete(position);
//            parent.getChildAt(position).setBackgroundColor(Color.WHITE);
//        }
//        else {
//            parent.setSelected(true);
//            selectedList.put(position, true);
//            parent.getChildAt(position).setBackgroundColor(Color.GRAY);
//        }
//        Log.w("CA", String.valueOf(selectedList.size()));
////////////////////////////////////////////////////////////////////////////////////
////        boolean isSel = theList.get(position).isEmpty();
////
////        if (isSel) {
////            parent.setSelected(false);
////            selectedList.delete(position);/////////////////////////////////////////
////            parent.getChildAt(position).setBackgroundColor(Color.RED);
////        } else {
////            parent.setSelected(true);
////            selectedList.put(position, true);
////            parent.getChildAt(position).setBackgroundColor(Color.GREEN);
////            //String idd = theList.get(position);
////            //myDB.deletedata(idd);
////            //theList.remove(position);
////        }
//        listAdapter.notifyDataSetChanged();
//        return true;
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }


//    ActionMode mActionMode;
//
//    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
//        @Override
//        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//            mode.getMenuInflater().inflate(R.menu.menu_details, menu);
//            return true;
//        }
//        @Override
//        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//            return false;
//        }
//        @Override
//        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.delete_all:
//                    FinAdapter.deleteAllSelected();
//                    mode.finish();
//                    return true;
//                case R.id.add_fav:
//                    Toast.makeText(MainActivity.this, "ToDo", Toast.LENGTH_LONG).show();
//                case R.id.unselect_all:
//                    cocktailAdapter.deselectAll();
//                    mode.finish();
//                    return true;
//                case R.id.select_all:
//                    cocktailAdapter.selectAll();
//                    return true;
//                default:
//                    return false;
//            }
//        }
//        @Override
//        public void onDestroyActionMode(ActionMode mode) {
//            mActionMode = null;
//        }
//    };



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                case R.id.delete_all:{
                    finAdapter.deleteAllSelected();
                    return true;


//                    Log.w("accesso a  del", "granted");
//                    for (int index = 0; index < theList.size(); index++) {
//                        if(selectedList.get(index)) {
//                            String id = theList.get(index);
//                            myDB.deletedata(id);
//                            theList.remove(index);
//                            selectedList.delete(index);
//
//                        }
//                    }
//                    //theList.clear();
//                    //selectedList.clear();
//                    listAdapter.notifyDataSetChanged();
//                    Log.w("CA", "DELETE");
                //break;
                }


                case R.id.unselect_all:{
                    finAdapter.deselectAll();

//                    selectedList.clear();
//
//                    notifyDataSetChanged();

//                    for(int i = 0; i < theList.size()-1; i++){
//                        selectedList.delete(i);
//
//                    }
//                    selectedList.clear();
//                    listAdapter.notifyDataSetChanged();
                    return true;
                }


                case R.id.select_all:{
                    finAdapter.selectAll();
//                    for(int i = 0; i < theList.size(); i++) {
//                        selectedList.put(i, true);
//                    }
//                    notifyDataSetChanged();

//                    for (int i = 0; i < theList.size(); i++) {
//                        Log.w("CA", selectedList.size() + "aaaaaaaaaaaaaaaaaaaaaaaaaa");
//                        selectedList.put(i, true);
//                    }
//                    //selectedList.clear();
//                    listAdapter.notifyDataSetChanged();
                    //listAdapter.notifyDataSetInvalidated();
                    //listAdapter.setNotifyOnChange(true);
                    return true;
                }

        }
        return true;
    }


    public class FinAdapter extends RecyclerView.Adapter<Holder> implements View.OnClickListener, View.OnLongClickListener{
        Cursor dt;

        public FinAdapter(Cursor data) {
            dt = data;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl;
            cl = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.raw_view_list_contents, parent, false);
            cl.setOnClickListener(this);
            cl.setOnLongClickListener(this);
            return new Holder(cl);

        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            if(dt.moveToPosition(position)){
                holder.tvSave.setText(dt.getString(1));
                Log.w("CA", "valore" + dt.getString(1));

                boolean isSelected = selectedList.get(position,false);

                if(isSelected) {
                    holder.tvSave.setSelected(true);
                    holder.tvSave.setTypeface(null, Typeface.BOLD);
                } else {
                    holder.tvSave.setSelected(false);
                    holder.tvSave.setTypeface(null, Typeface.NORMAL);
                }
            }
        }

        @Override
        public int getItemCount() {
            return dt.getCount();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ViewListContents.this, showDetails.class);
            intent.putExtra("details", dt.getString(1));
            ViewListContents.this.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            int position = ((RecyclerView) v.getParent()).getChildAdapterPosition(v);
            boolean isSelected = selectedList.get(position, false);
            if(isSelected) {
                v.setSelected(false);
                selectedList.delete(position);
            } else {
                v.setSelected(true);
                selectedList.put(position, true);
            }
//            if (mListener != null) {
//                mListener.onSelect(selectedList.size());  // Callback verso MainActivity
//            }
            notifyDataSetChanged();
            return true;
        }

        public void deselectAll(){
            selectedList.clear();
            Log.w("CA", "UNSELECT ALL");
            notifyDataSetChanged();
        }

        public void selectAll() {
            for(int i = 0; i < selectedList.size(); i++) {
                selectedList.put(i, true);
            }
            Log.w("CA", "SELECT ALL");
            notifyDataSetChanged();
        }

        public void deleteAllSelected() {
            if (selectedList.size()==0) { return; }
            for (int index = theList.size()-1; index >=0; index--) {//////////////////////
                if (selectedList.get(index,false)) {
                    Log.w("CA", "DELETE" + theList.get(index));
                    remove(index);
                }
            }
            selectedList.clear();
            Log.w("CA", String.valueOf(dt.getCount()));
        }

        private void remove(int position) {
            theList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private static class Holder extends RecyclerView.ViewHolder {

        TextView tvSave;

        public Holder(@NonNull View cl) {
            super(cl);

            tvSave = cl.findViewById(R.id.tvSave);
        }
    }
}

package it.giudevo.worldbank.localDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import it.giudevo.worldbank.R;

public class ViewListContents extends AppCompatActivity {

    DataBaseHelper myDB;
    RecyclerView rvFav;
    public Cursor data;
    public SparseBooleanArray selectedList = new SparseBooleanArray();
    public ArrayList<String> theList;
    public SaveAdapter finAdapter;
    public boolean theme_boolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences mPref = getSharedPreferences("THEME", 0);
        theme_boolean = mPref.getBoolean("theme_boolean", true);

        SetTheme(theme_boolean);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_contents);

        rvFav = findViewById(R.id.rvFav);
        myDB = new DataBaseHelper(this);


        theList = new ArrayList<>();

        data = myDB.getListContents();
        if (data.getCount() == 0) {
            Toast.makeText(this, R.string.no_content, Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(1));

                rvFav.setLayoutManager(new LinearLayoutManager((this)));
                finAdapter = new SaveAdapter(theList);
                rvFav.setAdapter(finAdapter);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                case R.id.delete_all:{
                    finAdapter.deleteAllSelected();
                    break;
                }
                case R.id.unselect_all:{
                    finAdapter.deselectAll();
                    break;
                }
                case R.id.select_all:{
                    finAdapter.selectAll();
                    break;
                }

        }
        return true;
    }


    public class SaveAdapter extends RecyclerView.Adapter<Holder> implements View.OnClickListener, View.OnLongClickListener{
        ArrayList<String> dt;

        public SaveAdapter(ArrayList<String> theList) {
            dt = theList;
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
                holder.tvSave.setText(dt.get(position));

                boolean isSelected = selectedList.get(position,false);

                if(isSelected) {
                    holder.tvSave.setSelected(true);
                    holder.tvSave.setTypeface(null, Typeface.BOLD);
                    holder.cvListContents.setBackgroundColor(Color.RED);
                } else {
                    holder.tvSave.setSelected(false);
                    holder.tvSave.setTypeface(null, Typeface.NORMAL);
                    holder.cvListContents.setBackgroundColor(Color.WHITE);
                }
        }

        @Override
        public int getItemCount() {
            return dt.size();
        }

        @Override
        public void onClick(View v) {
            int position = ((RecyclerView) v.getParent()).getChildAdapterPosition(v);
            Intent intent = new Intent(ViewListContents.this, showDetails.class);
            intent.putExtra("details", dt.get(position));
            ViewListContents.this.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            int position = ((RecyclerView) v.getParent()).getChildAdapterPosition(v);
            boolean isSel = selectedList.get(position, false);
            if(isSel) {
                v.setSelected(false);
                selectedList.delete(position);
            } else {
                v.setSelected(true);
                selectedList.put(position, true);
            }
            notifyDataSetChanged();
            return true;
        }

        public void deselectAll(){
            selectedList.clear();
            notifyDataSetChanged();
        }

        public void selectAll() {
            for(int i = 0; i < theList.size(); i++) {
                selectedList.put(i, true);
            }
            notifyDataSetChanged();
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
            String id = theList.get(position);
            myDB.deletedata(id);
            theList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private static class Holder extends RecyclerView.ViewHolder {

        TextView tvSave;
        CardView cvListContents;

        public Holder(@NonNull View cl) {
            super(cl);

            tvSave = cl.findViewById(R.id.tvSave);
            cvListContents = cl.findViewById(R.id.cvListContents);
        }
    }
}

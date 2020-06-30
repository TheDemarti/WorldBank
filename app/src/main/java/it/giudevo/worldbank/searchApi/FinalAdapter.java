package it.giudevo.worldbank.searchApi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.giudevo.worldbank.DataBaseHelper;
import it.giudevo.worldbank.R;
import it.giudevo.worldbank.database.Final.Final;

public class FinalAdapter extends RecyclerView.Adapter<FinalAdapter.Holder> {

    public static List<Final> ultimate;

    public FinalAdapter(List<Final> cnt) {
            ultimate = cnt;
    }

    public static void AddData(Context context) {
        DataBaseHelper mDatabaseHelper = new DataBaseHelper(context);
        boolean insertData = mDatabaseHelper.addData(ultimate);
        Log.w("CA", String.valueOf(insertData));

        if(insertData){
            Toast.makeText(context, "saved", Toast.LENGTH_LONG).show();
        }   else {
            Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
        }
    }

    @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl;
            cl = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.raw_final_search, parent, false);
            return new Holder(cl);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
                holder.tvFinal.setText("Date: " + ultimate.get(position).getDate() + " --> " + ultimate.get(position).getValue());
        }

        @Override
        public int getItemCount() {
            return ultimate.size();
        }


        static class Holder extends RecyclerView.ViewHolder{
        TextView tvFinal;

        Holder(ConstraintLayout cl){
            super(cl);
            tvFinal = cl.findViewById(R.id.tvFinal);
        }
    }
}

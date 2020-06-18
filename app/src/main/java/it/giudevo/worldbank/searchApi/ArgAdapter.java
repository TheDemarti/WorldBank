package it.giudevo.worldbank.searchApi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.giudevo.worldbank.R;
import it.giudevo.worldbank.database.Arguments;

public class ArgAdapter extends RecyclerView.Adapter<ArgAdapter.ViewHolder> {
    public List<Arguments> arguments;

    public ArgAdapter(List<Arguments> cnt) {
        arguments = cnt;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout cl;
                 cl = (ConstraintLayout) LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.raw_arguments, parent, false);
                return new ViewHolder(cl);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvValue.setText(arguments.get(position).getValue());
        holder.tvSourceNote.setText(arguments.get(position).getSourceNote());
    }

    @Override
    public int getItemCount() {
        return arguments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvValue, tvSourceNote;

        public ViewHolder(ConstraintLayout cl) {
            super(cl);
            tvValue = cl.findViewById(R.id.tvValue);
            tvSourceNote = cl.findViewById(R.id.tvSourceNote);
        }
    }
}

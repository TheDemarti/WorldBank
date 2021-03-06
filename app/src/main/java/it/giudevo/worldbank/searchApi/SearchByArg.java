package it.giudevo.worldbank.searchApi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import java.lang.reflect.Type;
import java.util.List;
import it.giudevo.worldbank.R;
import it.giudevo.worldbank.database.Arguments.Arguments;
import it.giudevo.worldbank.database.Countries.Countries;

public class SearchByArg extends AppCompatActivity {
    public boolean choice; //variabile che identifica il percorso di ricerca selezionato all'inizio
    public Countries countries;
    public boolean theme_boolean; //variabile usata per impostare il tema corretto (true = Light, false = Dark)

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        SharedPreferences mPref = getSharedPreferences("THEME", 0);
        theme_boolean = mPref.getBoolean("theme_boolean", true);

        SetTheme(theme_boolean);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_arg);

        new Holder();
    }

    // funzione che imposta il tema dell'activity
    private void SetTheme(boolean bool) {
        if(bool){
            setTheme(R.style.AppTheme);
        }
        else{
            setTheme(R.style.Theme_MaterialComponents_DayNight_DarkActionBar);
        }
    }

    private class Holder{
        RecyclerView rvArguments;
        VolleyArguments model;

        Holder() {
            rvArguments = findViewById(R.id.rvArguments);
            this.model = new VolleyArguments() {

                @Override
                void fill(List<Arguments> cnt) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchByArg.this);
                    rvArguments.setLayoutManager(layoutManager);
                    rvArguments.setHasFixedSize(true);
                    ArgAdapter myAdapter = new ArgAdapter(cnt);
                    rvArguments.setAdapter(myAdapter);
                }
            };

            Intent data = getIntent();
            choice = data.getBooleanExtra("choice",false);
            if(choice){
                countries = data.getParcelableExtra("countries");
            }
            model.searchByArg();
        }
    }

private abstract class VolleyArguments implements Response.ErrorListener, Response.Listener<String> {
        abstract void fill(List<Arguments> cnt);

        void searchByArg() {
            String url = "http://api.worldbank.org/v2/topic?format=json";
            //url = String.format(url);
            apiCall(url);
        }

        private void apiCall(String url) {
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(SearchByArg.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    url,
                    this,
                    this);
            requestQueue.add(stringRequest);

        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(SearchByArg.this, R.string.goes_wrong, Toast.LENGTH_LONG).show();
            error.printStackTrace();
        }

        @Override
        public void onResponse(String response) {
            Gson gson = new Gson();
            String arguments;
            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONArray json = jsonArray.getJSONArray(1);
                arguments = json.toString();
                Type listType = new TypeToken<List<Arguments>>() {}.getType();
                List<Arguments> cnt = gson.fromJson(arguments, listType);
                if (cnt != null && cnt.size() > 0 && cnt.get(0).value != null) {
                    fill(cnt);
                }
            } catch (JSONException e) {
                Log.d("Avviso", "errore");
                e.printStackTrace();
            }
        }
    }

    public class ArgAdapter extends RecyclerView.Adapter<ArgAdapter.ViewHolder> implements View.OnClickListener{
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
                cl.setOnClickListener(this);
                return new ViewHolder(cl);

            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                holder.tvValue.setText(arguments.get(position).getValue());// titolo del topic
                holder.tvSourceNote.setText(arguments.get(position).getSourceNote());// descrizione del topic
            }

            @Override
            public int getItemCount() {
                return arguments.size();
            }

        @Override
        public void onClick(View v) {
            int position = ((RecyclerView) v.getParent()).getChildAdapterPosition(v);
            Arguments arg = arguments.get(position);
            Intent intent = new Intent(SearchByArg.this, SearchByIndicator.class);
            intent.putExtra("arguments", arg);

            //se la ricerca si svolge secondo: paese-ARGOMENTO-indicatore
            if(choice){
                intent.putExtra("choice", choice);
                intent.putExtra("countries", countries);
            }
            SearchByArg.this.startActivity(intent);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
                TextView tvValue, tvSourceNote;
                CardView cvArguments;

                public ViewHolder(ConstraintLayout cl) {
                    super(cl);
                    tvValue = cl.findViewById(R.id.tvValue);
                    tvSourceNote = cl.findViewById(R.id.tvSourceNote);
                    cvArguments = cl.findViewById(R.id.cvArguments);
                }
            }
    }
}

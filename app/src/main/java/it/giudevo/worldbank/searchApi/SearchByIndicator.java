package it.giudevo.worldbank.searchApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Locale;

import it.giudevo.worldbank.R;
import it.giudevo.worldbank.database.Arguments.Arguments;
import it.giudevo.worldbank.database.Countries.Countries;
import it.giudevo.worldbank.database.Indicators.AppIndicatorsDatabase;
import it.giudevo.worldbank.database.Indicators.Indicators;

public class SearchByIndicator extends AppCompatActivity {
    AppIndicatorsDatabase db;
    public boolean choice;
    public Countries countries;
    public Arguments arguments;
    public boolean theme_boolean;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences mPref = getSharedPreferences("THEME", 0);
        theme_boolean = mPref.getBoolean("theme_boolean", true);

        SetTheme(theme_boolean);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_indicator);

        new Holder();
        createDB();
    }

    private void SetTheme(boolean bool) {
        if(bool){
            setTheme(R.style.AppTheme);
        }
        else{
            setTheme(R.style.Theme_MaterialComponents_DayNight_DarkActionBar);
        }
    }

    private void createDB(){
        db = Room.databaseBuilder(getApplicationContext(),
                AppIndicatorsDatabase.class, "indicator.db").allowMainThreadQueries().
                build();
    }


    private class Holder{
        RecyclerView rvIndicators;
        VolleyIndicator model;

        Holder() {
            rvIndicators = findViewById(R.id.rvIndicators);
            this.model = new VolleyIndicator() {

                @Override
                void fill(List<Indicators> cnt) {
                    Log.w("CA", "fill");
                    fillList(cnt);
                }

                private void fillList(List<Indicators> cnt) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchByIndicator.this);
                    rvIndicators.setLayoutManager(layoutManager);
                    rvIndicators.setHasFixedSize(true);
                    IndAdapter myAdapter = new IndAdapter(cnt);
                    rvIndicators.setAdapter(myAdapter);
                }
            };
            Intent data = getIntent();
            arguments = data.getParcelableExtra("arguments");

            choice = data.getBooleanExtra("choice",false);
            if(choice) {
                countries = data.getParcelableExtra("countries");
            }
            model.searchByInd(arguments.id);
        }
}

    private abstract class VolleyIndicator implements Response.ErrorListener, Response.Listener<String>{
        abstract void fill(List<Indicators> cnt);

        void searchByInd(int s) {
            String url = "http://api.worldbank.org/v2/topic/%s/indicator?format=json&per_page=17447";
            url = String.format(url, s);
            apiCall(url);
        }

        private void apiCall(String url) {
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(SearchByIndicator.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    url,
                    this,
                    this);
            requestQueue.add(stringRequest);

        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(SearchByIndicator.this, R.string.goes_wrong, Toast.LENGTH_LONG).show();//
            error.printStackTrace();//
        }

        @Override
        public void onResponse(String response) {
            Gson gson = new Gson();
            String indicators;
            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONArray json = jsonArray.getJSONArray(1);

                indicators = json.toString();
                Type listType = new TypeToken<List<Indicators>>() {}.getType();
                List<Indicators> cnt = gson.fromJson(indicators, listType);
                if (cnt != null && cnt.size() > 0) {
                    db.indicatorsDAO().insertAll();
                    fill(cnt);
                }
            } catch (JSONException e) {
                Log.d("Prova", "errore");
                e.printStackTrace();
            }
        }
    }

    private class IndAdapter extends RecyclerView.Adapter<Holder1> implements View.OnClickListener{
        public List<Indicators> indicators;

        public IndAdapter(List<Indicators> cnt) {
            indicators = cnt;
        }

        @NonNull
        @Override
        public Holder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl;
            cl = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.raw_indicator, parent, false);
            cl.setOnClickListener(this);
            return new Holder1(cl);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder1 holder, int position) {
            holder.tvIndicator.setText(indicators.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return indicators.size();
        }

        @Override
        public void onClick(View v) {
            int position = ((RecyclerView) v.getParent()).getChildAdapterPosition(v);
            Indicators ind = indicators.get(position);
            if(choice){
                Intent intent = new Intent(SearchByIndicator.this, FinalSearch.class);
                intent.putExtra("indicators", ind);
                intent.putExtra("countries", countries);
                intent.putExtra("choice", choice);
                intent.putExtra("arguments", arguments);
                SearchByIndicator.this.startActivity(intent);
            }
            else{
                Intent intent = new Intent(SearchByIndicator.this, SearchByCountry.class);///////////////////////
                intent.putExtra("indicators", ind);
                intent.putExtra("arguments", arguments);
                intent.putExtra("choice", choice);
                SearchByIndicator.this.startActivity(intent);
            }
        }
    }

    private static class Holder1 extends RecyclerView.ViewHolder {
        TextView tvIndicator;
        CardView cvIndicator;

        Holder1(ConstraintLayout cl) {
            super(cl);
            tvIndicator = cl.findViewById(R.id.tvIndicator);
            cvIndicator = cl.findViewById(R.id.cvIndicator);
        }
    }
}
package it.giudevo.worldbank.searchApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

import it.giudevo.worldbank.Map_View;
import it.giudevo.worldbank.R;
import it.giudevo.worldbank.database.Arguments.Arguments;
import it.giudevo.worldbank.database.Countries.Countries;
import it.giudevo.worldbank.database.Indicators.Indicators;
import it.giudevo.worldbank.database.Countries.AppCountriesDatabase;

public class SearchByCountry extends AppCompatActivity {
    AppCountriesDatabase db;
    public boolean choice;
    public Indicators indicators;
    public Arguments arguments;
    public boolean theme_boolean;
    public String latitude;
    public String longitude;
    public String capitalCity;
    public int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences mPref = getSharedPreferences("THEME", 0);
        theme_boolean = mPref.getBoolean("theme_boolean", true);

        SetTheme(theme_boolean);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_country);

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
                AppCountriesDatabase.class, "countries.db").allowMainThreadQueries().
                build();
    }

    private class Holder{
        RecyclerView rvCountryFirst;
        VolleyCountries model;

        Holder() {
            rvCountryFirst = findViewById(R.id.rvCountryFirst);
            this.model = new VolleyCountries() {


                @Override
                void fill(List<Countries> cnt) {
                    Log.w("CA", "fill");
                    fillList(cnt);
                }

                private void fillList(List<Countries> cnt) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchByCountry.this);
                    rvCountryFirst.setLayoutManager(layoutManager);
                    rvCountryFirst.setHasFixedSize(true);
                    CountryAdapter myAdapter = new CountryAdapter(cnt);
                    rvCountryFirst.setAdapter(myAdapter);
                }
            };

            Intent data = getIntent();
            choice = data.getBooleanExtra("choice", false);
            Log.w("CA", String.valueOf(choice));
            if(!choice){
                arguments = data.getParcelableExtra("arguments");
                indicators = data.getParcelableExtra("indicators");
                model.CountriesAPI(getApplicationContext());
                model.searchByCountry();
            }
            model.CountriesAPI(getApplicationContext());
            model.searchByCountry();
        }
    }

    private abstract class VolleyCountries implements Response.ErrorListener, Response.Listener<String> {
        abstract void fill(List<Countries> cnt);

        RequestQueue requestQueue;

        void CountriesAPI(Context context) {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 20 * 1024 * 1024); // 20MB
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();
        }

        void searchByCountry() {
            String url = "http://api.worldbank.org/v2/country?format=json&per_page=304";
            //url = String.format(url);
            apiCall(url);
        }

        private void apiCall(String url) {
            //RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(SearchByCountry.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    url,
                    this,
                    this);
            requestQueue.add(stringRequest);

        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(SearchByCountry.this, R.string.goes_wrong, Toast.LENGTH_LONG).show();//
            error.printStackTrace();
        }

        @Override
        public void onResponse(String response) {
            Gson gson = new Gson();
            String countries;
            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONArray json = jsonArray.getJSONArray(1);

                countries = json.toString();
                Type listType = new TypeToken<List<Countries>>() {}.getType();
                List<Countries> cnt = gson.fromJson(countries, listType);
                if (cnt != null && cnt.size() > 0) {
                    Log.w("CA", "" + cnt.size());
                    //db.countriesDAO().insertAll();
                    fill(cnt);
                }
            } catch (JSONException e) {
                Log.d("Prova", "errore");
                e.printStackTrace();
            }
        }
    }

    public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> implements View.OnClickListener{

        public List<Countries> countries;

        public CountryAdapter(List<Countries> cnt) {
            countries = cnt;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl;
            cl = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.raw_countries, parent, false);
            cl.setOnClickListener(this);
            return new ViewHolder(cl);

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                holder.tvIsoCodeFirst.setText(countries.get(position).getName());
                latitude = countries.get(position).getLatitude();
                longitude = countries.get(position).getLongitude();
                capitalCity = countries.get(position).getCapitalCity();
        }

        @Override
        public int getItemCount() {
            return countries.size();
        }

        @Override
        public void onClick(View v) {
            position = ((RecyclerView) v.getParent()).getChildAdapterPosition(v);
                Countries cou = countries.get(position);
                if (choice) {
                    Intent intent = new Intent(SearchByCountry.this, SearchByArg.class);
                    intent.putExtra("countries", cou);
                    intent.putExtra("choice", choice);
                    SearchByCountry.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(SearchByCountry.this, FinalSearch.class);
                    intent.putExtra("countries", cou);
                    intent.putExtra("indicators", indicators);
                    intent.putExtra("arguments", arguments);
                    intent.putExtra("choice", choice);
                    SearchByCountry.this.startActivity(intent);
                }
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvIsoCodeFirst;
            CardView cvCountryFirst;
            Button btnMap;

            public ViewHolder(ConstraintLayout cl) {
                super(cl);
                tvIsoCodeFirst = cl.findViewById(R.id.tvIsoCodeFirst);
                cvCountryFirst = cl.findViewById(R.id.cvCountriesFirst);
                btnMap = cl.findViewById(R.id.btnMap);

                btnMap.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchByCountry.this, Map_View.class);
                intent.putExtra("latitude", Double.valueOf(latitude));
                intent.putExtra("longitude", Double.valueOf(longitude));
                intent.putExtra("capitalCity", capitalCity);
                Log.w("CA", String.valueOf((latitude)));
                Log.w("CA", String.valueOf((longitude)));
                Log.w("CA", capitalCity);
                SearchByCountry.this.startActivity(intent);
            }
        }
    }
}
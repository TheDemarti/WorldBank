package it.giudevo.worldbank.searchApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

import it.giudevo.worldbank.R;

import it.giudevo.worldbank.database.Countries.Countries;
import it.giudevo.worldbank.database.Countries.AppCountriesDatabase;
import it.giudevo.worldbank.database.Indicators.AppIndicatorsDatabase;


public class SearchByCountry extends AppCompatActivity {
    AppCountriesDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_country);
        new SearchByCountry.Holder();
        createDB();
    }
    private void createDB(){
        db = Room.databaseBuilder(getApplicationContext(),
                AppCountriesDatabase.class, "countries.db").allowMainThreadQueries().
                build();
    }

    private class Holder{
        RecyclerView rvCountry;
        SearchByCountry.VolleyCountries model;

        Holder() {
            rvCountry = findViewById(R.id.rvCountry);
            this.model = new SearchByCountry.VolleyCountries() {


                @Override
                void fill(List<Countries> cnt) {
                    Log.w("CA", "fill");
                    fillList(cnt);
                }

                private void fillList(List<Countries> cnt) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchByCountry.this);
                    rvCountry.setLayoutManager(layoutManager);
                    rvCountry.setHasFixedSize(true);
                    SearchByCountry.CountryAdapter myAdapter = new SearchByCountry.CountryAdapter(cnt);
                    rvCountry.setAdapter(myAdapter);
                }
            };

            model.searchByCountry();
            hideKeyboard(SearchByCountry.this);
        }

        void hideKeyboard(Activity activity) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private abstract class VolleyCountries implements Response.ErrorListener, Response.Listener<String> {
        abstract void fill(List<Countries> cnt);

        void searchByCountry() {
            String url = "http://api.worldbank.org/v2/country?format=json";
            //url = String.format(url);
            apiCall(url);
        }

        private void apiCall(String url) {
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(SearchByCountry.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    url,
                    this,
                    this);
            requestQueue.add(stringRequest);

        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(SearchByCountry.this, "Some Thing Goes Wrong", Toast.LENGTH_LONG).show();//
            error.printStackTrace();//
        }

        @Override
        public void onResponse(String response) {
            Log.d("Prova", "json approvato");
            Gson gson = new Gson();
            String countries;
            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONArray json = jsonArray.getJSONArray(1);/////modificato
                //JSONObject jsonObject1 = jsonObject.getJSONObject("value");
                //String id = jsonObject.getString("id");
                //String value = jsonObject.getString("value");
                //String sourceNote = jsonObject.getString("sourceNote");

                countries = json.toString();
                Type listType = new TypeToken<List<Countries>>() {}.getType();
                List<Countries> cnt = gson.fromJson(countries, listType);
                if (cnt != null && cnt.size() > 0) {
                    Log.w("CA", "" + cnt.size());
                    db.countriesDAO().insertAll();
                    fill(cnt);
                }
            } catch (JSONException e) {
                Log.d("Prova", "errore");
                e.printStackTrace();
            }
        }
    }

    public class CountryAdapter extends RecyclerView.Adapter<SearchByCountry.CountryAdapter.ViewHolder> {

        public List<Countries> countries;
        private String id;

        public CountryAdapter(List<Countries> cnt) {
             countries = cnt;
        }


        @NonNull
        @Override
        public SearchByCountry.CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl;
            cl = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.raw_countries, parent, false);
            return new SearchByCountry.CountryAdapter.ViewHolder(cl);

        }

        @Override
        public void onBindViewHolder(@NonNull SearchByCountry.CountryAdapter.ViewHolder holder, int position) {
            holder.tvValue.setText(countries.get(position).id);
            holder.tvSourceNote.setText(countries.get(position).capitalCity);
            id = String.valueOf(countries.get(position).getId());
        }

        @Override
        public int getItemCount() {
            return countries.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvValue, tvSourceNote;
            CardView cvCountry;

            public ViewHolder(ConstraintLayout cl) {
                super(cl);
                tvValue = cl.findViewById(R.id.tvValue);
                tvSourceNote = cl.findViewById(R.id.tvSourceNote);
                cvCountry = cl.findViewById(R.id.cvCountries);

                cvCountry.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.cvCountries){
                    Intent intent = new Intent(SearchByCountry.this, SearchByArg.class);
                    intent.putExtra("countries", id);
                    SearchByCountry.this.startActivity(intent);
                }
            }
        }
    }
}

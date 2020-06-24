package it.giudevo.worldbank.searchApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import it.giudevo.worldbank.database.Arguments.FinalSearch.FinalSearch;
import it.giudevo.worldbank.database.Arguments.Indicators.Indicators;
import it.giudevo.worldbank.database.Arguments.Countries.Country;

public class FinalSearchFromCountry extends AppCompatActivity {
    public boolean choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_search);

        new Holder();
    }

    private class Holder {
        VolleyCountries model;
        RecyclerView rvFinalFromCountry;

        Holder() {
            rvFinalFromCountry = findViewById(R.id.rvFinalFromCountry);
            this.model = new VolleyCountries() {

                @Override
                void fill(List<FinalSearch> cnt) {
                    Log.w("CA", "fill");
                    fillList(cnt);
                }

                private void fillList(List<FinalSearch> cnt) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FinalSearchFromCountry.this);
                    rvFinalFromCountry.setLayoutManager(layoutManager);
                    rvFinalFromCountry.setHasFixedSize(true);
                    FinalAdapter myAdapter = new FinalAdapter(cnt);
                    rvFinalFromCountry.setAdapter(myAdapter);
                }

            };

            Intent data = getIntent();
            Indicators search = data.getParcelableExtra("indicators");
            choice = data.getBooleanExtra("choice", true);
            if(choice) {
                Country countries = data.getParcelableExtra("countries");
                assert search != null;
                //model.CountriesAPI(getApplicationContext());
                assert countries != null;
                model.searchByCountry(countries.getIso2Code(), search.getId());
            }
            else{
                Country countries = data.getParcelableExtra("countries");
                assert search != null;
                //model.CountriesAPI(getApplicationContext());
                assert countries != null;
                model.searchByCountry(countries.getIso2Code(), search.getId());
            }///////////////////////////////////////////
            hideKeyboard(FinalSearchFromCountry.this);

            //tvProva.setText(cnt.get(0).getIso2Code());
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

        private abstract class VolleyCountries implements Response.ErrorListener, Response.Listener<String>{
            abstract void fill(List<FinalSearch> cnt);

            RequestQueue requestQueue;

//        void CountriesAPI(Context context) {
//            Cache cache = new DiskBasedCache(context.getCacheDir(), 20 * 1024 * 1024); // 20MB
//            Network network = new BasicNetwork(new HurlStack());
//            requestQueue = new RequestQueue(cache, network);
//            requestQueue.start();
//        }

            void searchByCountry(String s, String r) {
                String url = "http://api.worldbank.org/v2/country/%s/indicator/%s?format=json&per_page=500";
                url = String.format(url, s, r);
                apiCall(url);
            }

            private void apiCall(String url) {
                //RequestQueue requestQueue;
                requestQueue = Volley.newRequestQueue(FinalSearchFromCountry.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        url,
                        this,
                        this);
                requestQueue.add(stringRequest);

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FinalSearchFromCountry.this, "Some Thing Goes Wrong", Toast.LENGTH_LONG).show();//
                error.printStackTrace();//
            }

            @Override
            public void onResponse(String response) {
                Log.d("Prova", "json approvato");
                Gson gson = new Gson();
                String countries;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONArray json = jsonArray.getJSONArray(1);
                    countries = json.toString();
                    Type listType = new TypeToken<List<FinalSearch>>() {
                    }.getType();
                    List<FinalSearch> cnt = gson.fromJson(countries, listType);
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
    }

    private static class FinalAdapter extends RecyclerView.Adapter<Holder2> {
        public List<FinalSearch> ultimate;

        public FinalAdapter(List<FinalSearch> cnt) {
            ultimate = cnt;
        }


        @NonNull
        @Override
        public Holder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl;
            cl = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.raw_final_search, parent, false);
            return new Holder2(cl);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder2 holder, int position) {
            if(ultimate.get(position).getValue() != null) {
                holder.tvProva.setText(ultimate.get(position).getValue());
            }
        }


        @Override
        public int getItemCount() {
            return ultimate.size();
        }
    }

    private static class Holder2 extends RecyclerView.ViewHolder {
        TextView tvProva;
        Holder2(ConstraintLayout cl){
            super(cl);
            tvProva = cl.findViewById(R.id.tvProva);
        }
    }
    }
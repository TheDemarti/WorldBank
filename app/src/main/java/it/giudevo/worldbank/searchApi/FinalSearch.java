package it.giudevo.worldbank.searchApi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import it.giudevo.worldbank.database.Arguments.Countries.Countries;
import it.giudevo.worldbank.database.Arguments.Indicators.Indicators;
import it.giudevo.worldbank.database.Country.Countries.Country;

public class FinalSearch extends AppCompatActivity {
    public List<Countries> cnt;
    public boolean choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_search);

        new Holder();
    }

    private class Holder {
        VolleyCountries model;
        TextView tvProva;

        Holder() {
            tvProva = findViewById(R.id.tvProva);
            this.model = new VolleyCountries() {
            };

            Intent data = getIntent();
            Indicators search = data.getParcelableExtra("indicators");
            choice = data.getBooleanExtra("choice", false);
            if(!choice) {
                Countries countries = data.getParcelableExtra("countries");
                assert search != null;
                //model.CountriesAPI(getApplicationContext());
                assert countries != null;
                model.searchByCountry(countries.getCountryiso3code(), search.getId());
                hideKeyboard(FinalSearch.this);
            }
            else{
                Country country = data.getParcelableExtra("countries");
                assert search != null;
                //model.CountriesAPI(getApplicationContext());
                assert country != null;
                model.searchByCountry(country.getIso2Code(), search.getId());
                hideKeyboard(FinalSearch.this);
            }
            //Log.w("ID TOPIC", String.valueOf(search));
//            assert search != null;
//            //model.CountriesAPI(getApplicationContext());
//            assert countries != null;
//            model.searchByCountry(countries.getCountryiso3code(), search.getId());
//            hideKeyboard(FinalSearch.this);

            //tvProva.setText(cnt.get(0).getCountryiso3code());
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

        private abstract class VolleyCountries implements Response.ErrorListener, Response.Listener<String> {

            RequestQueue requestQueue;

//        void CountriesAPI(Context context) {
//            Cache cache = new DiskBasedCache(context.getCacheDir(), 20 * 1024 * 1024); // 20MB
//            Network network = new BasicNetwork(new HurlStack());
//            requestQueue = new RequestQueue(cache, network);
//            requestQueue.start();
//        }

            void searchByCountry(String s, String r) {
                String url = "http://api.worldbank.org/v2/country/%s/indicator/%s?format=json&per_page=2000";
                url = String.format(url, s, r);
                apiCall(url);
            }

            private void apiCall(String url) {
                //RequestQueue requestQueue;
                requestQueue = Volley.newRequestQueue(FinalSearch.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        url,
                        this,
                        this);
                requestQueue.add(stringRequest);

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FinalSearch.this, "Some Thing Goes Wrong", Toast.LENGTH_LONG).show();//
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
                    Type listType = new TypeToken<List<Countries>>() {
                    }.getType();
                    cnt = gson.fromJson(countries, listType);
                    if (cnt != null && cnt.size() > 0) {
                        Log.w("CA", "" + cnt.size());
                        //db.countriesDAO().insertAll();
                        //fill(cnt);
                    }
                } catch (JSONException e) {
                    Log.d("Prova", "errore");
                    e.printStackTrace();
                }
            }
        }
    }
}
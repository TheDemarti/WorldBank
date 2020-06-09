package it.giudevo.worldbank.searchapi;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import it.giudevo.worldbank.MainActivity;
import it.giudevo.worldbank.R;


public class SearchByArg extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MainActivity.Holder();
    }

    abstract static class Volley implements Response.ErrorListener, Response.Listener<String> {

        void searchCocktailsByName(String s) {
            String url = " http://api.worldbank.org/v2/topic?format=json";
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
        }

        @Override
        public void onResponse(String response) {
            Gson gson = new Gson();
            String drinks;
            try {
                JSONObject jsonObject = new JSONObject(response);
                drinks = jsonObject.getJSONArray("drinks").toString();
                Type listType = new TypeToken<List<Cocktail>>() {
                }.getType();
                List<Cocktail> cnt = gson.fromJson(drinks, listType);
                if (cnt != null && cnt.size() > 0) {
                    Log.w("CA", "" + cnt.size());
                    fill(cnt);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

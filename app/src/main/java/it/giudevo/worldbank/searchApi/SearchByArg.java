package it.giudevo.worldbank.searchApi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

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
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import it.giudevo.worldbank.R;
import it.giudevo.worldbank.database.AppArgumentsDatabase;
import it.giudevo.worldbank.database.Arguments;


public class SearchByArg extends AppCompatActivity {
    AppArgumentsDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_arg);

        new Holder();
        createDB();
    }

    private void createDB(){
        db = Room.databaseBuilder(getApplicationContext(),
                AppArgumentsDatabase.class, "arguments.db").allowMainThreadQueries().
                build();
    }

    private class Holder{
        RecyclerView rvArguments;
        VolleyArguments model;

        Holder() {
            rvArguments = findViewById(R.id.rvArguments);
            this.model = new VolleyArguments() {


                @Override
                void fill(List<Arguments> cnt) {
                    Log.w("CA", "fill");
                    fillList(cnt);
                }

                private void fillList(List<Arguments> cnt) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchByArg.this);
                    rvArguments.setLayoutManager(layoutManager);
                    rvArguments.setHasFixedSize(true);
                    ArgAdapter myAdapter = new ArgAdapter(cnt);
                    rvArguments.setAdapter(myAdapter);
                }
            };
            String search = "topic";
            model.searchByArg(search);
            hideKeyboard(SearchByArg.this);
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

private abstract class VolleyArguments implements Response.ErrorListener, Response.Listener<String> {
        abstract void fill(List<Arguments> cnt);

        void searchByArg(String s) {
            String url = "http://api.worldbank.org/v2/%s?format=json";
            url = String.format(url, s);
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
            Toast.makeText(SearchByArg.this, "Some Thing Goes Wrong", Toast.LENGTH_LONG).show();//
            error.printStackTrace();//
        }

        @Override
        public void onResponse(String response) {
            Log.d("Prova", "json approvato");
            Gson gson = new Gson();
            String arguments;
            try {
                JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(1);
                    //String id = jsonObject.getString("id");
                    //String value = jsonObject.getString("value");
                    //String sourceNote = jsonObject.getString("sourceNote");

                arguments = jsonObject.toString();
                Type listType = new TypeToken<List<Arguments>>() {}.getType();
                List<Arguments> cnt = gson.fromJson(arguments, listType);
                if (cnt != null && cnt.size() > 0) {
                    Log.w("CA", "" + cnt.size());
                    db.argumentsDAO().insertAll();
                    fill(cnt);
                }
            } catch (JSONException e) {
                Log.d("Prova", "errore");
                e.printStackTrace();
            }
        }
    }
}

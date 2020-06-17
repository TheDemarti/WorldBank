package it.giudevo.worldbank.searchApi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import it.giudevo.worldbank.R;
import it.giudevo.worldbank.database.AppArgumentsDatabase;
import it.giudevo.worldbank.database.Arguments;


public class SearchByArg extends AppCompatActivity {
    AppArgumentsDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Holder();
        createDB();
    }

    private void createDB(){
        db = Room.databaseBuilder(getApplicationContext(),
                AppArgumentsDatabase.class, "arguments.db").allowMainThreadQueries().
                build();
    }

    private class Holder implements View.OnClickListener{
        RecyclerView rvArguments;
        final VolleyArguments model;

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
                    ArgAdapter mAdapter = new ArgAdapter(cnt);
                    rvArguments.setAdapter(mAdapter);
                }
            };


            String search = "topic";
            model.searchByArg(search);
            hideKeyboard(SearchByArg.this);

        }

        @Override
        public void onClick(View v) {
//            String search = "topic";
//            model.searchByArg(search);
//            hideKeyboard(SearchByArg.this);
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

    abstract class VolleyArguments implements Response.ErrorListener, Response.Listener<String> {
        abstract void fill(List<Arguments> cnt);

        void searchByArg(String s) {
            String url = " http://api.worldbank.org/v2/%s?format=json";
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
                JSONArray values = new JSONArray(response);
                //JSONArray object = values.getJSONArray(1);
                arguments = values.get(1).toString();
                //arguments = jsonArray.getJSONArray(1).toString();//getJSONArray("arguments").toString();
                Type listType = new TypeToken<List<Arguments>>() {}.getType();
                List<Arguments> cnt = gson.fromJson(arguments, listType);
                if (cnt != null && cnt.size() > 0) {
                    Log.w("CA", "" + cnt.size());
                    db.argumentsDAO().insertAll();////
                    fill(cnt);
                }
            } catch (JSONException e) {
                Log.d("Prova", "errore");
                e.printStackTrace();
            }
        }

        public class ArgAdapter extends RecyclerView.Adapter<ArgAdapter.Holder> implements View.OnClickListener {
            private final List<Arguments> arguments;

            public ArgAdapter(List<Arguments> all) {
                arguments = all;
            }

            @NonNull
            @Override
            public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ConstraintLayout cl;
                cl = (ConstraintLayout) LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.raw_layout_arg, parent, false);
                cl.setOnClickListener(this);
                return new Holder(cl);
            }

            @Override
            public void onBindViewHolder(@NonNull Holder holder, int position) {
                holder.tvElement.setText(arguments.get(position).getValue());
                holder.tvElement.setText(arguments.get(position).value);
            }

            @Override
            public int getItemCount() {
                return arguments.size();
            }

            @Override
            public void onClick(View v) {

            }


            class Holder extends RecyclerView.ViewHolder {
                TextView tvElement;

                Holder(@NonNull View itemView) {
                    super(itemView);
                    tvElement = itemView.findViewById(R.id.tvElement);
                }
            }
        }
    }


}

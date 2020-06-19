package it.giudevo.worldbank.searchApi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

import java.lang.reflect.Type;
import java.util.List;

import it.giudevo.worldbank.R;
import it.giudevo.worldbank.database.Arguments.Arguments;
import it.giudevo.worldbank.database.Arguments.AppArgumentsDatabase;

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
            //hideKeyboard(SearchByArg.this);
        }

//        void hideKeyboard(Activity activity) {
//            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//            //Find the currently focused view, so we can grab the correct window token from it.
//            View view = activity.getCurrentFocus();
//            //If no view currently has focus, create a new one, just so we can grab a window token from it
//            if (view == null) {
//                view = new View(activity);
//            }
//            assert imm != null;
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
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
                    JSONArray json = jsonArray.getJSONArray(1);/////modificato
                    //JSONObject jsonObject1 = jsonObject.getJSONObject("value");
                    //String id = jsonObject.getString("id");
                    //String value = jsonObject.getString("value");
                    //String sourceNote = jsonObject.getString("sourceNote");

                arguments = json.toString();
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

            public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                TextView tvValue, tvSourceNote;
                CardView cvArguments;

                public ViewHolder(ConstraintLayout cl) {
                    super(cl);
                    tvValue = cl.findViewById(R.id.tvValue);
                    tvSourceNote = cl.findViewById(R.id.tvSourceNote);
                    cvArguments = cl.findViewById(R.id.cvArguments);

                    cvArguments.setOnClickListener(this);
                }

                @Override
                public void onClick(View v) {
                    if(v.getId() == R.id.cvArguments){
                        Intent intent = new Intent(SearchByArg.this, SearchByIndicator.class);
                        //intent.putExtra("arguments", arguments.get(1));
                        SearchByArg.this.startActivity(intent);
                    }
                }
            }
    }
}

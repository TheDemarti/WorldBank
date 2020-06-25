package it.giudevo.worldbank.searchApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import it.giudevo.worldbank.R;
import it.giudevo.worldbank.database.Countries.Countries;
import it.giudevo.worldbank.database.Final.Final;
import it.giudevo.worldbank.database.Indicators.Indicators;

public class FinalSearch extends AppCompatActivity  {
    public boolean choice;
    public LineChart mChart;
    public ArrayList<Entry> value = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_search);
        new Holder();

    }



    private class Holder {
        VolleyCountries model;
        RecyclerView rvFinal;

        @SuppressLint("WrongViewCast")
        Holder() {
            rvFinal = findViewById(R.id.rvFinal);
            mChart = findViewById(R.id.lcGraph);


            this.model = new VolleyCountries() {

                @Override
                void fill(List<Final> cnt) {
                    Log.w("CA", "fill");
                    fillList(cnt);
                }

                private void fillList(List<Final> cnt) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FinalSearch.this);
                    rvFinal.setLayoutManager(layoutManager);
                    rvFinal.setHasFixedSize(true);
                    FinalAdapter myAdapter = new FinalAdapter(cnt);
                    rvFinal.setAdapter(myAdapter);
                }

            };

            Intent data = getIntent();
            Indicators search = data.getParcelableExtra("indicators");
            choice = data.getBooleanExtra("choice", true);
            if(choice) {
                Countries countries = data.getParcelableExtra("countries");
                assert search != null;
                //model.CountriesAPI(getApplicationContext());
                assert countries != null;
                model.searchByCountry(countries.getIso2Code(), search.getId());
            }
            else{
                Countries countries = data.getParcelableExtra("countries");
                assert search != null;
                //model.CountriesAPI(getApplicationContext());
                assert countries != null;
                model.searchByCountry(countries.getIso2Code(), search.getId());
            }
            hideKeyboard(FinalSearch.this);

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
            abstract void fill(List<Final> cnt);

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
                    Type listType = new TypeToken<List<Final>>() {
                    }.getType();
                    List<Final> cnt = gson.fromJson(countries, listType);
                    if (cnt != null && cnt.size() > 0) {
                        Log.w("CA", "" + cnt.size());
                        //db.Final.DAO().insertAll();
                        CreateGraph(cnt);
                        fill(cnt);
                    }
                } catch (JSONException e) {
                    Log.d("Prova", "errore");
                    e.printStackTrace();
                }
            }
        }
    }

    public void CreateGraph(List<Final> cnt) {
        //mChart.setOnChartGestureListener(FinalSearch.this);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

      /*  for(int i = 0; i < cnt.size(); i++){
            if(cnt.get(i).value != null){
                String valore = cnt.get(i).value;
                 value.add(new Entry(i, Float.parseFloat(valore)) );
        }}*/
        value.add(new Entry(0, 23));
        value.add(new Entry(1, 24));
        value.add(new Entry(2, 22));
        value.add(new Entry(3, 10));
        value.add(new Entry(4, 14));
        value.add(new Entry(5, 45));
        value.add(new Entry(6, 23));
        value.add(new Entry(7, 22));


        LineDataSet assey = new LineDataSet(value,"Grafico");
        assey.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(assey);

        LineData data = new LineData(dataSets);
        mChart.setData(data);

    }

    public static class FinalAdapter extends RecyclerView.Adapter<Holder2> {
        public List<Final> ultimate;

        public FinalAdapter(List<Final> cnt) {
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
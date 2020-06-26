package it.giudevo.worldbank.searchApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
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
    public LineChart lcGraph;
    public SeekBar seekBarX, seekBarY;
    public TextView tvX, tvY;
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
            //mChart = findViewById(R.id.lcGraph);


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
                    Log.w("CA", String.valueOf(json));

                    Log.w("CA", String.valueOf(json.getJSONObject(0).get("value")));
                    int i = 0;
                    while(i < json.length()){
                        if(String.valueOf(json.getJSONObject(i).get("value")).equals("null")){
                            json.remove(i);
                        }
                        else{
                            i++;
                        }
                    }
                    Log.w("CA", String.valueOf(json));
                    Log.w("CA", "" + json.length());


                    countries = json.toString();
                    Type listType = new TypeToken<List<Final>>() {
                    }.getType();
                    List<Final> cnt = gson.fromJson(countries, listType);
                    if (cnt != null && cnt.size() > 0) {
                        Log.w("CA", String.valueOf(cnt.get(cnt.size()-1).date));
                        Log.w("CA", String.valueOf(cnt.get(cnt.size()-1).value));
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

    public void CreateGraph(List<Final> cnt){

        tvX = findViewById(R.id.tvX);
        tvY = findViewById(R.id.tvY);

        seekBarX = findViewById(R.id.seekBarX);
        seekBarY = findViewById(R.id.seekBarY);

        lcGraph = findViewById(R.id.lcGraph);
        lcGraph.setViewPortOffsets(0, 0, 0, 0);
        lcGraph.setBackgroundColor(Color.rgb(104, 241, 175));

        // no description text
        lcGraph.getDescription().setEnabled(false);

        // enable touch gestures
        lcGraph.setTouchEnabled(true);

        // enable scaling and dragging
        lcGraph.setDragEnabled(true);
        lcGraph.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lcGraph.setPinchZoom(true);

        lcGraph.setDrawGridBackground(false);
        lcGraph.setMaxHighlightDistance(300);

        XAxis x = lcGraph.getXAxis();
        x.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        x.setEnabled(true);
        x.setLabelCount(6, false);
        x.setTextColor(Color.BLACK);
        x.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        x.setDrawGridLines(false);
        x.setAxisLineColor(Color.BLACK);

        YAxis y = lcGraph.getAxisLeft();
        //y.setTypeface(tfLight);
        y.setLabelCount(6, false);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.BLACK);

        lcGraph.getAxisRight().setEnabled(false);
/////////////////////////////////////////////////////////
        setData(cnt);

        // add data
 //       seekBarY.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);
 //       seekBarX.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);

        // lower max, as cubic runs significantly slower than linear
        seekBarX.setMax(cnt.size());////////////////////////////////////////////////////////////////////////////////////
        Log.w("CA", String.valueOf(cnt.size()));

        seekBarX.setProgress(45);
        seekBarY.setProgress(100);

        lcGraph.getLegend().setEnabled(false);

        lcGraph.animateXY(2000, 2000);

        // don't forget to refresh the drawing
        lcGraph.invalidate();
    }

    private void setData(List<Final> cnt) {

        ArrayList<Entry> values = new ArrayList<>();

        for(int i = 0; i < cnt.size()-1; i++) {
            Log.w("CA", String.valueOf(cnt.get(i).getValue()));
            Log.w("CA", String.valueOf(cnt.get(i).getDate()));
                float date = (float) (cnt.get(i).getDate());
                float val = (float) (cnt.get(i).getValue());
                //Log.w("CA", String.valueOf(val));
                values.add(new Entry(date, val));



//            float val = (float) (Math.random() * (range + 1)) + 20;
//            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (lcGraph.getData() != null && lcGraph.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lcGraph.getData().getDataSetByIndex(1990);
            set1.setValues(values);
            lcGraph.getData().notifyDataChanged();
            lcGraph.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return lcGraph.getAxisLeft().getAxisMinimum();
                }
            });

            // create a data object with the data sets
            LineData data = new LineData(set1);
            //data.setValueTypeface(tfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            lcGraph.setData(data);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.line, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.viewGithub: {
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/CubicLineChartActivity.java"));
//                startActivity(i);
//                break;
//            }
//            case R.id.actionToggleValues: {
//                for (IDataSet set : lcGraph.getData().getDataSets())
//                    set.setDrawValues(!set.isDrawValuesEnabled());
//
//                lcGraph.invalidate();
//                break;
//            }
//            case R.id.actionToggleHighlight: {
//                if(lcGraph.getData() != null) {
//                    lcGraph.getData().setHighlightEnabled(!lcGraph.getData().isHighlightEnabled());
//                    lcGraph.invalidate();
//                }
//                break;
//            }
//            case R.id.actionToggleFilled: {
//
//                List<ILineDataSet> sets = lcGraph.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//
//                    if (set.isDrawFilledEnabled())
//                        set.setDrawFilled(false);
//                    else
//                        set.setDrawFilled(true);
//                }
//                lcGraph.invalidate();
//                break;
//            }
//            case R.id.actionToggleCircles: {
//                List<ILineDataSet> sets = lcGraph.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//                    if (set.isDrawCirclesEnabled())
//                        set.setDrawCircles(false);
//                    else
//                        set.setDrawCircles(true);
//                }
//                lcGraph.invalidate();
//                break;
//            }
//            case R.id.actionToggleCubic: {
//                List<ILineDataSet> sets = lcGraph.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//                    set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER
//                            ? LineDataSet.Mode.LINEAR
//                            :  LineDataSet.Mode.CUBIC_BEZIER);
//                }
//                lcGraph.invalidate();
//                break;
//            }
//            case R.id.actionToggleStepped: {
//                List<ILineDataSet> sets = lcGraph.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//                    set.setMode(set.getMode() == LineDataSet.Mode.STEPPED
//                            ? LineDataSet.Mode.LINEAR
//                            :  LineDataSet.Mode.STEPPED);
//                }
//                lcGraph.invalidate();
//                break;
//            }
//            case R.id.actionToggleHorizontalCubic: {
//                List<ILineDataSet> sets = lcGraph.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//                    set.setMode(set.getMode() == LineDataSet.Mode.HORIZONTAL_BEZIER
//                            ? LineDataSet.Mode.LINEAR
//                            :  LineDataSet.Mode.HORIZONTAL_BEZIER);
//                }
//                lcGraph.invalidate();
//                break;
//            }
//            case R.id.actionTogglePinch: {
//                if (lcGraph.isPinchZoomEnabled())
//                    lcGraph.setPinchZoom(false);
//                else
//                    lcGraph.setPinchZoom(true);
//
//                lcGraph.invalidate();
//                break;
//            }
//            case R.id.actionToggleAutoScaleMinMax: {
//                lcGraph.setAutoScaleMinMaxEnabled(!lcGraph.isAutoScaleMinMaxEnabled());
//                lcGraph.notifyDataSetChanged();
//                break;
//            }
//            case R.id.animateX: {
//                lcGraph.animateX(2000);
//                break;
//            }
//            case R.id.animateY: {
//                lcGraph.animateY(2000);
//                break;
//            }
//            case R.id.animateXY: {
//                lcGraph.animateXY(2000, 2000);
//                break;
//            }
//            case R.id.actionSave: {
//                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                    saveToGallery();
//                } else {
//                    requestStoragePermission(lcGraph);
//                }
//                break;
//            }
//        }
//        return true;
//    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        //setData(seekBarX.getProgress(), seekBarY.getProgress());

        // redraw
        lcGraph.invalidate();
    }
//
//    @Override
//    protected void saveToGallery() {
//        saveToGallery(lcGraph, "CubicLineChartActivity");
//    }
//
//    @Override
//    public void onStartTrackingTouch(SeekBar seekBar) {}
//
//    @Override
//    public void onStopTrackingTouch(SeekBar seekBar) {}

        ////////////////////////////////////////////////////////////////////////////////
        //mChart.setOnChartGestureListener(FinalSearch.this);
//        mChart.setDragEnabled(true);
//        mChart.setScaleEnabled(false);
//
//        for(int i = 0; i < cnt.size(); i++){
//            if(cnt.get(i).value != null){
//                String valore = cnt.get(i).value;
//                String xValue = cnt.get(i).date;
//                value.add(new Entry(1050+i, Float.parseFloat(valore)));
//                Log.w("CA", xValue);
//            }
//        }
//
//        LineDataSet assey = new LineDataSet(value,"Grafico");
//        assey.setFillAlpha(110);
//
//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//        dataSets.add(assey);
//
//        LineData data = new LineData(dataSets);
//        mChart.setData(data);
//        assey.setDrawFilled(true);
//        assey.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        assey.setDrawFilled(true);
//        data.setDrawValues(false);
//        mChart.invalidate();
/////////////////////////////////////////////////////////////////////

        /*value.add(new Entry(0, 23));
        value.add(new Entry(1, 24));
        value.add(new Entry(2, 22));
        value.add(new Entry(3, 10));
        value.add(new Entry(4, 14));
        value.add(new Entry(5, 45));
        value.add(new Entry(6, 23));
        value.add(new Entry(7, 22));*/


    class FinalAdapter extends RecyclerView.Adapter<Holder2> {
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
                holder.tvProva.setText(String.valueOf(ultimate.get(position).getValue()));
        }


        @Override
        public int getItemCount() {
            return ultimate.size();
        }
    }

    class Holder2 extends RecyclerView.ViewHolder {
        TextView tvProva;
        Holder2(ConstraintLayout cl){
            super(cl);
            tvProva = cl.findViewById(R.id.tvProva);
        }
    }
}
package it.giudevo.worldbank.searchApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import it.giudevo.worldbank.DataBaseHelper;
import it.giudevo.worldbank.MainActivity;
import it.giudevo.worldbank.R;
import it.giudevo.worldbank.database.Arguments.Arguments;
import it.giudevo.worldbank.database.Countries.Countries;
import it.giudevo.worldbank.database.Final.Final;
import it.giudevo.worldbank.database.Indicators.Indicators;

public class FinalSearch extends AppCompatActivity  {
    DataBaseHelper mDatabaseHelper;

    public boolean choice;
    public LineChart lcGraph;
    TextView tvResume;
    public Countries countries;
    public Indicators indicators;
    public Arguments arguments;
    public  String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_search);

        new Holder();
        lcGraph = findViewById(R.id.lcGraph);
        mDatabaseHelper = new DataBaseHelper(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class Holder{
        VolleyFinal model;
        RecyclerView rvFinal;

        @SuppressLint({"WrongViewCast", "SetTextI18n"})
        Holder() {
            rvFinal = findViewById(R.id.rvFinal);
            tvResume = findViewById(R.id.tvResume);


            this.model = new VolleyFinal() {


                @Override
                void fill(List<Final> cnt, String s) {
                    Log.w("CA", "fill");
                    fillList(cnt, s);
                }

                private void fillList(List<Final> cnt, String string) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FinalSearch.this);
                    rvFinal.setLayoutManager(layoutManager);
                    rvFinal.setHasFixedSize(true);
                    FinalAdapter myAdapter = new FinalAdapter(cnt, string);
                    rvFinal.setAdapter(myAdapter);
                }

            };

            Intent data = getIntent();
            //Indicators
            arguments = data.getParcelableExtra("arguments");
                    indicators = data.getParcelableExtra("indicators");
            choice = data.getBooleanExtra("choice", true);
            if(choice) {
                //Countries
                 countries = data.getParcelableExtra("countries");
                assert indicators != null;
                //model.CountriesAPI(getApplicationContext());
                assert countries != null;
                model.searchByCountry(countries.getIso2Code(), indicators.getId());
            }
            else{
                //Countries
                        countries = data.getParcelableExtra("countries");
                assert indicators != null;
                //model.CountriesAPI(getApplicationContext());
                assert countries != null;
                model.searchByCountry(countries.getIso2Code(), indicators.getId());
            }
            hideKeyboard(FinalSearch.this);
            Log.w("CA", countries.getName());
            Log.w("CA", indicators.getName());

            string = countries.getName() + " - " + arguments.getValue() + " - " + indicators.getName();

            tvResume.setText(string);

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

        private abstract class VolleyFinal implements Response.ErrorListener, Response.Listener<String>{
            abstract void fill(List<Final> cnt, String s);

//        void CountriesAPI(Context context) {
//            Cache cache = new DiskBasedCache(context.getCacheDir(), 20 * 1024 * 1024); // 20MB
//            Network network = new BasicNetwork(new HurlStack());
//            requestQueue = new RequestQueue(cache, network);
//            requestQueue.start();
//        }

            void searchByCountry(String s, String r) {
                String language;

                if(!Locale.getDefault().getLanguage().equals("en") || !Locale.getDefault().getLanguage().equals("es") ||
                        !Locale.getDefault().getLanguage().equals("fr") || !Locale.getDefault().getLanguage().equals("zh") ||
                        !Locale.getDefault().getLanguage().equals("ar")){
                    language = "en";
                }
                else{
                    language = Locale.getDefault().getLanguage();
                }

                Log.w("CA", language);
                String url = "http://api.worldbank.org/v2/%s/country/%s/indicator/%s?format=json&per_page=500";
                url = String.format(url, language, s, r);
                apiCall(url);
            }

            private void apiCall(String url) {
                RequestQueue requestQueue;
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
                error.printStackTrace();
            }

            @Override
            public void onResponse(String response){
                Gson gson = new Gson();
                String countries;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONArray json = jsonArray.getJSONArray(1);

                    int i = 0;
                    while(i < json.length()){
                        if(String.valueOf(json.getJSONObject(i).get("value")).equals("null")){
                            json.remove(i);
                        }
                        else{
                            i++;
                        }
                    }

                    countries = json.toString();
                    Type listType = new TypeToken<List<Final>>() {
                    }.getType();
                    List<Final>cnt = gson.fromJson(countries, listType);
                    if (cnt != null && cnt.size() > 0) {
                        CreateGraph(cnt);
                        fill(cnt, string);
                    }
                } catch (JSONException e) {
                    Log.d("Prova", "errore");
                    Toast.makeText(FinalSearch.this, "No Data Available", Toast.LENGTH_LONG).show();//////////////
                    e.printStackTrace();
                }
            }

        }
    }

    public void CreateGraph(List<Final> cnt){

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
        ///////lcGraph.setPinchZoom(true);

        lcGraph.setDrawGridBackground(false);
        lcGraph.setMaxHighlightDistance(300);

        XAxis x = lcGraph.getXAxis();
        //x.setLabelRotationAngle(25);
        x.setAvoidFirstLastClipping(true);
        x.setDrawLabels(true);
        x.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        x.setEnabled(true);
        x.setLabelCount(5, false);
        x.setTextColor(Color.BLACK);
        x.setDrawGridLines(true);
        x.setAxisLineColor(Color.BLACK);

        YAxis y = lcGraph.getAxisLeft();
        //y.setTypeface(tfLight);
        y.setLabelCount(5, false);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(true);
        y.setAxisLineColor(Color.BLACK);

        lcGraph.getAxisRight().setEnabled(false);
/////////////////////////////////////////////////////////
        setData(cnt);

        lcGraph.getLegend().setEnabled(false);

        lcGraph.animateXY(2000, 2000);

        // don't forget to refresh the drawing
        lcGraph.invalidate();
    }

    private void setData(List<Final> cnt) {

        ArrayList<Entry> values = new ArrayList<>();

        for(int i = 0; i < cnt.size(); i++) {
                int date = (cnt.get(cnt.size() - 1 - i).getDate());
                float val = cnt.get(cnt.size() - 1 - i).getValue();
                values.add(new Entry(date, val));
        }

        LineDataSet set1;

        if (lcGraph.getData() != null && lcGraph.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lcGraph.getData().getDataSetByIndex(0);
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
            //set1.setDrawHorizontalHighlightIndicator(true);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.line, menu);
        return super.onCreateOptionsMenu(menu);
    }


        @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet set : lcGraph.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());
                lcGraph.invalidate();
                break;
            }
            case R.id.actionToggleFilled: {
                List<ILineDataSet> sets = lcGraph.getData()
                        .getDataSets();
                for (ILineDataSet iSet : sets) {
                    LineDataSet set = (LineDataSet) iSet;
                    if (set.isDrawFilledEnabled())
                        set.setDrawFilled(false);
                    else
                        set.setDrawFilled(true);
                }
                lcGraph.invalidate();
                break;
            }
            case R.id.actionToggleCircles: {
                List<ILineDataSet> sets = lcGraph.getData()
                        .getDataSets();
                for (ILineDataSet iSet : sets) {
                    LineDataSet set = (LineDataSet) iSet;
                    if (set.isDrawCirclesEnabled())
                        set.setDrawCircles(false);
                    else
                        set.setDrawCircles(true);
                }
                lcGraph.invalidate();
                break;
            }
            case R.id.actionToggleCubic: {
                List<ILineDataSet> sets = lcGraph.getData()
                        .getDataSets();
                for (ILineDataSet iSet : sets) {
                    LineDataSet set = (LineDataSet) iSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER
                            ? LineDataSet.Mode.LINEAR
                            : LineDataSet.Mode.CUBIC_BEZIER);
                }
                lcGraph.invalidate();
                break;
            }
            case R.id.actionToggleStepped: {
                List<ILineDataSet> sets = lcGraph.getData()
                        .getDataSets();
                for (ILineDataSet iSet : sets) {
                    LineDataSet set = (LineDataSet) iSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.STEPPED
                            ? LineDataSet.Mode.LINEAR
                            : LineDataSet.Mode.STEPPED);
                }
                lcGraph.invalidate();
                break;
            }
            case R.id.actionToggleHorizontalCubic: {
                List<ILineDataSet> sets = lcGraph.getData()
                        .getDataSets();
                for (ILineDataSet iSet : sets) {
                    LineDataSet set = (LineDataSet) iSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.HORIZONTAL_BEZIER
                            ? LineDataSet.Mode.LINEAR
                            : LineDataSet.Mode.HORIZONTAL_BEZIER);
                }
                lcGraph.invalidate();
                break;
            }
            case R.id.actionTogglePinch: {
                if (lcGraph.isPinchZoomEnabled())
                    lcGraph.setPinchZoom(false);
                else
                    lcGraph.setPinchZoom(true);
                lcGraph.invalidate();
                break;
            }
            case R.id.animateX: {
                lcGraph.animateX(2000);
                break;
            }
            case R.id.animateY: {
                lcGraph.animateY(2000);
                break;
            }
            case R.id.animateXY: {
                lcGraph.animateXY(2000, 2000);
                break;
            }
            case R.id.graphSave: {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    lcGraph.saveToGallery(String.valueOf(tvResume.getText()) + Calendar.getInstance().getTime(),
                            "WorldBank", null, Bitmap.CompressFormat.PNG, 75);
                }
                lcGraph.saveToGallery(String.valueOf(tvResume.getText()) + Calendar.getInstance().getTime(),
                        "/WorldBank", null, Bitmap.CompressFormat.PNG, 75);
                Toast.makeText(this, "Grafico Salvato", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.dataSave: {
                FinalAdapter.AddData(this);
                //Toast.makeText(this, "Dati Salvati", Toast.LENGTH_LONG).show();
            }
            case R.id.home: {
                Intent intent = new Intent(FinalSearch.this, MainActivity.class);
                FinalSearch.this.startActivity(intent);
            }
        }
        return true;
    }
}
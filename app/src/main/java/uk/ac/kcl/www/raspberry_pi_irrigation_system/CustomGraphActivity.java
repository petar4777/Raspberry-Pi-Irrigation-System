package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * The type Custom graph activity.
 * This class is used to create custom graphs. First all the variables needed are initialised. Then in the onCreate
 * method they are linked to their corresponding items in the xml file and an onClickListener is made for the back button.
 * Since information is going to be downloaded, an AsyncTask is introduced in order let the UI build itself faster.
 */
public class CustomGraphActivity extends BaseActivity
{
    /**
     * The Temp data.
     */
    LineDataSet tempData;
    /**
     * The constant KEY.
     */
    public static String KEY = "PASSWORD";
    /**
     * The constant KEY3.
     */
    public static String KEY3 = "IP";
    /**
     * The constant KEY4.
     */
    public static String KEY4 = "USERNAME";
    /**
     * The Hum data.
     */
    LineDataSet humData;
    /**
     * The Info.
     */
    String[] info;
    /**
     * The Moist data.
     */
    LineDataSet moistData;
    /**
     * The Cons data.
     */
    BarDataSet consData;
    /**
     * The Line data.
     */
    ArrayList<ILineDataSet> lineData;
    /**
     * The Bar data.
     */
    ArrayList<IBarDataSet> barData;
    /**
     * The Custom graph activity back.
     */
    Button customGraphActivityBack;
    /**
     * The Spin.
     */
    Spinner spin;
    /**
     * The Check box temp.
     */
    CheckBox checkBoxTemp;
    /**
     * The Check box hum.
     */
    CheckBox checkBoxHum;
    /**
     * The Check box moist.
     */
    CheckBox checkBoxMoist;
    /**
     * The Check box cons.
     */
    CheckBox checkBoxCons;
    /**
     * The Chart.
     */
    CombinedChart chart;
    /**
     * The On c.
     */
    MediaPlayer onC;
    /**
     * The Off c.
     */
    MediaPlayer offC;
    /**
     * The Test.
     */
    HashMap<String, HashMap> test;
    /**
     * The Internet connection.
     */
    static boolean internetConnection = true;
    /**
     * The Chart data.
     */
    public static Map<String, ChartData> chartData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_graph);

        //BUTTONS
        customGraphActivityBack = (Button) findViewById(R.id.customGraphActivityBack);
        spin = (Spinner) findViewById(R.id.customGraphSpinner);
        checkBoxTemp = (CheckBox) findViewById(R.id.checkBoxTemp);
        checkBoxHum = (CheckBox) findViewById(R.id.checkBoxHum);
        checkBoxMoist = (CheckBox) findViewById(R.id.checkBoxMoist);
        checkBoxCons = (CheckBox) findViewById(R.id.checkBoxCons);
        chart = (CombinedChart) findViewById(R.id.chart1);
        onC = MediaPlayer.create(this, R.raw.on);
        offC = MediaPlayer.create(this, R.raw.off);
        new CustomGraphActivity.Task().execute();
        //END OF BUTTONS

        //ON CLICKS
        customGraphActivityBack.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                offC.start();
                startActivity(new Intent(CustomGraphActivity.this, GraphActivity.class));
                finish();
                onLeaveThisActivity();
            }
        });
        //END OF ON CLICKS
    }

    /**
     * The type Task. In the doInBackground method, the application checks whether there is internet connection
     * available. If there is internet connection available, an SSHConnectionFinal object is created and the retrieved information
     * is loaded into a chartData object. If there is no internet connection available, the internetConnection boolean is set to equal to False
     * in order to indicate to the onPostExecution method that there is no internet connection available. In the onPostExecution method, the
     * chartData filled with the information from the SSH connection is passed as input. Inside of the onPostExecution there is an if/else statement that checks
     * the value of the internetConnection boolean. If it is equal to true, the chartData is used to fill the information needed for the graph and
     * the onClickListeners are assigned. If the value of the boolean is equal to false, the application checks whether there is stored data in the chartData
     * object. If there is previously loaded data, it is used to fill the graph. If there is no data available, the layout is changed in order to
     * notify the user that there is a problem.
     *
     */
    public class Task extends AsyncTask<Object, Object, Map<String, ChartData>>
    {

        @Override
        protected Map<String, ChartData> doInBackground(Object... params) {
            //CHECK IF SSH IS ALIVE OR NOT ?!?!?!
            if (CheckInternet.isInternetAvailable(CustomGraphActivity.this))
            {
                String storedInformation = getUserName(CustomGraphActivity.this);
                info = storedInformation.split("/");
                SSHConnectionFinal sshFinal = new SSHConnectionFinal(new String[] {"cd Desktop;cd FinalTest1;cat database.txt"}, false, info);
                test = new HashMap<>();
                chartData = sshFinal.parseDataToTreeMap(sshFinal.getFileContents());
            }
            else
            {
                internetConnection =false;
                Log.e("TEST", "NEMA NET");
            }
            return chartData;
        }

        @Override
        protected void onPostExecute(final Map<String, ChartData> chartData)
        {
            super.onPostExecute(chartData);
            if (!internetConnection == false)
            {
                Set<String> keys = chartData.keySet();
                String[] keyValues = keys.toArray(new String[chartData.size()]);
                Log.e("Key Values: ", Arrays.deepToString(keyValues));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CustomGraphActivity.this, android.R.layout.simple_spinner_item, keyValues);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(adapter);
                spin.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_5));
                int selection = keys.size() - 1;
                spin.setSelection(selection);
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        checkBoxMoist.setChecked(true);
                        checkBoxHum.setChecked(true);
                        checkBoxTemp.setChecked(true);
                        checkBoxCons.setChecked(true);

                        ChartData containerDay = chartData.get(spin.getSelectedItem());

                        ArrayList<Entry> currentTemp = containerDay.getTemps();
                        ArrayList<Entry> currentHum = containerDay.getHumidities();
                        ArrayList<Entry> currentMoist = containerDay.getMoistures();
                        ArrayList<BarEntry> currentCons = containerDay.getLitres();
                        ArrayList<String> currentTime = containerDay.getTimes();
                        CustomMarkerView cMV = new CustomMarkerView(CustomGraphActivity.this, R.layout.custom_marker_view_layout, "");
                        CombinedData data = new CombinedData(currentTime);
                        barData = new ArrayList<IBarDataSet>();
                        tempData = new LineDataSet(currentTemp, "Temperature");
                        humData = new LineDataSet(currentHum, "Humidity");
                        moistData = new LineDataSet(currentMoist, "Moisture");
                        consData = new BarDataSet(currentCons, "Consumption");
                        lineData = new ArrayList<ILineDataSet>();
                        tempData.setCircleRadius(2f);
                        tempData.setFillColor(Color.parseColor("#d35400"));
                        tempData.setFillAlpha(90);
                        tempData.setColor(Color.parseColor("#d35400"));
                        tempData.setCircleColor(Color.BLACK);
                        tempData.setDrawCubic(true);
                        tempData.setDrawFilled(true);
                        tempData.setDrawValues(false);
                        humData.setCircleRadius(2f);
                        humData.setColor(Color.parseColor("#16a085"));
                        humData.setCircleColor(Color.BLACK);
                        humData.setFillColor(Color.parseColor("#16a085"));
                        humData.setFillAlpha(90);
                        humData.setDrawCubic(true);
                        humData.setDrawFilled(true);
                        humData.setDrawValues(false);
                        moistData.setCircleRadius(2f);
                        moistData.setColor(Color.parseColor("#27ae60"));
                        moistData.setCircleColor(Color.BLACK);
                        moistData.setFillColor(Color.parseColor("#27ae60"));
                        moistData.setFillAlpha(90);
                        moistData.setDrawCubic(true);
                        moistData.setDrawFilled(true);
                        moistData.setDrawValues(false);
                        consData.setDrawValues(false);
                        consData.setBarSpacePercent(5);
                        lineData.add(tempData);
                        lineData.add(humData);
                        lineData.add(moistData);
                        barData.add(consData);
                        LineData lineDataline = new LineData(currentTime, lineData);
                        BarData barDataline = new BarData(currentTime, barData);
                        data.setData(lineDataline);
                        data.setData(barDataline);
                        chart.getAxisRight().setEnabled(false);
                        chart.setMarkerView(cMV);

                        YAxis axis = chart.getAxisLeft();
                        axis.setDrawLabels(true);
                        axis.setAxisMinValue(0f);
                        axis.setAxisMaxValue(100f);

                        chart.setData(data);
                        chart.setDescription("Custom graph");
                        chart.notifyDataSetChanged();
                        chart.invalidate();
                        chart.animateY(2000);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                checkBoxTemp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onC.start();
                        boolean isClicked = (checkBoxTemp.isChecked());
                        if (isClicked) {
                            if (!(lineData.contains(tempData))) {
                                lineData.add(tempData);
                                chart.invalidate();
                            }
                        } else {
                            if (lineData.contains(tempData)) {
                                lineData.remove(tempData);
                                chart.invalidate();
                            }
                        }
                    }
                });

                checkBoxHum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onC.start();
                        boolean isClicked = (checkBoxHum.isChecked());
                        if (isClicked) {
                            if (!(lineData.contains(humData))) {
                                lineData.add(humData);
                                chart.invalidate();
                            }
                        } else {
                            if (lineData.contains(humData)) {
                                lineData.remove(humData);
                                chart.invalidate();
                            }
                        }
                    }
                });

                checkBoxMoist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onC.start();
                        boolean isClicked = (checkBoxMoist.isChecked());
                        if (isClicked) {
                            if (!(lineData.contains(moistData))) {
                                lineData.add(moistData);
                                chart.invalidate();
                            }
                        } else {
                            if (lineData.contains(moistData)) {
                                lineData.remove(moistData);
                                chart.invalidate();
                            }
                        }
                    }
                });

                checkBoxCons.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onC.start();
                        boolean isClicked = (checkBoxCons.isChecked());
                        if (isClicked) {
                            if (!(barData.contains(consData))) {
                                barData.add(consData);
                                chart.invalidate();
                            }
                        } else {
                            if (barData.contains(consData)) {
                                barData.remove(consData);
                                chart.invalidate();
                            }
                        }
                    }
                });
            }
            else
            {
                if(chartData != null)
                {
                    Set<String> keys = chartData.keySet();
                    String[] keyValues = keys.toArray(new String[chartData.size()]);
                    Log.e("K    ey Values: ", Arrays.deepToString(keyValues));
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CustomGraphActivity.this, android.R.layout.simple_spinner_item, keyValues);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin.setAdapter(adapter);
                    int selection = keys.size() - 1;
                    spin.setSelection(selection);
                    spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            checkBoxMoist.setChecked(true);
                            checkBoxHum.setChecked(true);
                            checkBoxTemp.setChecked(true);
                            checkBoxCons.setChecked(true);

                            ChartData containerDay = chartData.get(spin.getSelectedItem());

                            ArrayList<Entry> currentTemp = containerDay.getTemps();
                            ArrayList<Entry> currentHum = containerDay.getHumidities();
                            ArrayList<Entry> currentMoist = containerDay.getMoistures();
                            ArrayList<BarEntry> currentCons = containerDay.getLitres();
                            ArrayList<String> currentTime = containerDay.getTimes();
                            CustomMarkerView cMV = new CustomMarkerView(CustomGraphActivity.this, R.layout.custom_marker_view_layout, "");
                            CombinedData data = new CombinedData(currentTime);
                            barData = new ArrayList<IBarDataSet>();
                            tempData = new LineDataSet(currentTemp, "Temperature");
                            humData = new LineDataSet(currentHum, "Humidity");
                            moistData = new LineDataSet(currentMoist, "Moisture");
                            consData = new BarDataSet(currentCons, "Consumption");
                            lineData = new ArrayList<ILineDataSet>();
                            tempData.setCircleRadius(2f);
                            tempData.setFillColor(Color.parseColor("#d35400"));
                            tempData.setFillAlpha(90);
                            tempData.setColor(Color.parseColor("#d35400"));
                            tempData.setCircleColor(Color.BLACK);
                            tempData.setDrawCubic(true);
                            tempData.setDrawFilled(true);
                            tempData.setDrawValues(false);
                            humData.setCircleRadius(2f);
                            humData.setColor(Color.parseColor("#16a085"));
                            humData.setCircleColor(Color.BLACK);
                            humData.setFillColor(Color.parseColor("#16a085"));
                            humData.setFillAlpha(90);
                            humData.setDrawCubic(true);
                            humData.setDrawFilled(true);
                            humData.setDrawValues(false);
                            moistData.setCircleRadius(2f);
                            moistData.setColor(Color.parseColor("#27ae60"));
                            moistData.setCircleColor(Color.BLACK);
                            moistData.setFillColor(Color.parseColor("#27ae60"));
                            moistData.setFillAlpha(90);
                            moistData.setDrawCubic(true);
                            moistData.setDrawFilled(true);
                            moistData.setDrawValues(false);
                            consData.setDrawValues(false);
                            consData.setBarSpacePercent(5);
                            lineData.add(tempData);
                            lineData.add(humData);
                            lineData.add(moistData);
                            barData.add(consData);
                            LineData lineDataline = new LineData(currentTime, lineData);
                            BarData barDataline = new BarData(currentTime, barData);
                            data.setData(lineDataline);
                            data.setData(barDataline);
                            chart.getAxisRight().setEnabled(false);
                            chart.setMarkerView(cMV);

                            YAxis axis = chart.getAxisLeft();
                            axis.setDrawLabels(true);
                            axis.setAxisMinValue(0f);
                            axis.setAxisMaxValue(100f);

                            chart.setData(data);
                            chart.setDescription("Custom graph");
                            chart.notifyDataSetChanged();
                            chart.invalidate();
                            chart.animateY(2000);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    checkBoxTemp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onC.start();
                            boolean isClicked = (checkBoxTemp.isChecked());
                            if (isClicked) {
                                if (!(lineData.contains(tempData))) {
                                    lineData.add(tempData);
                                    chart.invalidate();
                                }
                            } else {
                                if (lineData.contains(tempData)) {
                                    lineData.remove(tempData);
                                    chart.invalidate();
                                }
                            }
                        }
                    });

                    checkBoxHum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onC.start();
                            boolean isClicked = (checkBoxHum.isChecked());
                            if (isClicked) {
                                if (!(lineData.contains(humData))) {
                                    lineData.add(humData);
                                    chart.invalidate();
                                }
                            } else {
                                if (lineData.contains(humData)) {
                                    lineData.remove(humData);
                                    chart.invalidate();
                                }
                            }
                        }
                    });

                    checkBoxMoist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onC.start();
                            boolean isClicked = (checkBoxMoist.isChecked());
                            if (isClicked) {
                                if (!(lineData.contains(moistData))) {
                                    lineData.add(moistData);
                                    chart.invalidate();
                                }
                            } else {
                                if (lineData.contains(moistData)) {
                                    lineData.remove(moistData);
                                    chart.invalidate();
                                }
                            }
                        }
                    });

                    checkBoxCons.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onC.start();
                            boolean isClicked = (checkBoxCons.isChecked());
                            if (isClicked) {
                                if (!(barData.contains(consData))) {
                                    barData.add(consData);
                                    chart.invalidate();
                                }
                            } else {
                                if (barData.contains(consData)) {
                                    barData.remove(consData);
                                    chart.invalidate();
                                }
                            }
                        }
                    });
                }
                else
                {
                    chart.setNoDataText("No internet connection."); // this is the top line
                    chart.setNoDataTextDescription("..."); // this is one line below the no-data-text
                    chart.invalidate();
                }
            }
        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        offC.start();
        startActivity(new Intent(CustomGraphActivity.this, GraphActivity.class));
        finish();
        onLeaveThisActivity();
    }

    /**
     * Gets user name.
     *This method is used to get the information which is stored in the SharedPreferences file. This information is required to create an SSH connection.
     * @param context the context
     * @return the user name
     */
    public String getUserName(Context context)
    {
        SharedPreferences savedSession = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        SharedPreferences savedSession2 = context.getSharedPreferences(KEY3,Activity.MODE_PRIVATE);
        SharedPreferences savedSession3 = context.getSharedPreferences(KEY4,Activity.MODE_PRIVATE);
        String all = savedSession.getString("password","")+"/"+savedSession2.getString("IP","")+"/"+ savedSession3.getString("username","");
        return all;

    }
}

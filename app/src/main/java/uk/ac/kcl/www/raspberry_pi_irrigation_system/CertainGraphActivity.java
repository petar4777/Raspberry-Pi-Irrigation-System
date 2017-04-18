package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * The type Certain graph activity. This class is responisble for
 * visualizing single graphs. To download information an asynctask
 * is introduced.
 */
public class CertainGraphActivity extends BaseActivity
{
    /**
     * The Ds.
     */
    LineDataSet ds;
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
     * The Zero line.
     */
    boolean zeroLine;
    /**
     * The Max.
     */
    float max;
    /**
     * The Min.
     */
    float min;
    /**
     * The Format.
     */
    String format;
    /**
     * The Color.
     */
    String color;
    /**
     * The Data.
     */
    LineData data;
    /**
     * The Chart val 1.
     */
    String chartVal1;
    /**
     * The Chart val 2.
     */
    String chartVal2;
    /**
     * The Activity back.
     */
    Button activityBack;
    /**
     * The Current value.
     */
    ArrayList currentValue;
    /**
     * The Zoom in.
     */
    Button zoomIn;
    /**
     * The Zoom out.
     */
    Button zoomOut;
    /**
     * The Line chart.
     */
    LineChart lineChart;
    /**
     * The Test.
     */
    String[] test;
    /**
     * The Spin.
     */
    Spinner spin;
    /**
     * The On c.
     */
    MediaPlayer onC;
    /**
     * The Off c.
     */
    MediaPlayer offC;
    /**
     * The Internet connection.
     */
    static boolean internetConnection = true;
    /**
     * The Chart data.
     */
    public static Map<String, ChartData> chartData = null;
    /**
     * The C mv.
     */
    CustomMarkerView cMV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certain_graph);

        //BUTTONS
        activityBack = (Button) findViewById(R.id.ActivityBack);
        zoomIn = (Button) findViewById(R.id.ZoomIn);
        zoomOut = (Button) findViewById(R.id.ZoomOut);
        lineChart = (LineChart) findViewById(R.id.chart1);
        spin = (Spinner) findViewById(R.id.Spinner);
        onC = MediaPlayer.create(this, R.raw.on);
        offC = MediaPlayer.create(this, R.raw.off);
        new CertainGraphActivity.Task().execute();
        checkWhichActivity();
        //END OF BUTTONS

        //ON CLICKS
        activityBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                offC.start();
                startActivity(new Intent(CertainGraphActivity.this, GraphActivity.class));
                finish();
                onLeaveThisActivity();
            }
        });
        //END OF ON CLICKS
    }

    /**
     * The type Task. This Asynctask is used to download graph information. To do so
     * it uses the doInBackground and onPostExecution methods. In the doInBackground method,
     * the information is downloaded via SSH. To do so, a SSHConnectionFinal object is created and
     * the retrieved information is stored in a chartData object. It also checks whether internet connection
     * is available. If there is no internet connection available it sets the value of the internetConnection
     * boolean to false in order to indicate to the onPostExecution method that there is no internet connection available
     */
    public class Task extends AsyncTask<Object, Object, Map<String, ChartData>>
    {

        @Override
        protected Map<String, ChartData> doInBackground(Object... params)
        {
            //CHECK IF SSH IS ALIVE OR NOT ?!?!?!
            if (CheckInternet.isInternetAvailable(CertainGraphActivity.this))
            {
                String storedInformation = getUserName(CertainGraphActivity.this);
                test = storedInformation.split("/");
                SSHConnectionFinal sshFinal = new SSHConnectionFinal(new String[]{"cd Desktop;cd FinalTest1;cat database.txt"}, false, test);
                chartData = sshFinal.parseDataToTreeMap(sshFinal.getFileContents());
            }
            else
            {
                internetConnection = false;
            }
            return chartData;
        }

        @Override
        protected void onPostExecute(final Map<String, ChartData> chartData) {
            super.onPostExecute(chartData);
            if (!internetConnection == false)
            {
                zoomIn.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        onC.start();
                        lineChart.zoomIn();
                    }
                });

                zoomOut.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        offC.start();
                        lineChart.zoomOut();
                    }
                });
                Log.e("KEYSET", chartData.keySet().toString());
                Set<String> keys = chartData.keySet();
                Log.e("TEST KEYS", keys.toString());
                String[] keyValues = keys.toArray(new String[chartData.size()]);
                Log.e("Key Values: ", Arrays.deepToString(keyValues));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CertainGraphActivity.this, android.R.layout.simple_spinner_item, keyValues);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(adapter);
                int selection = keys.size() - 1;
                spin.setSelection(selection);
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        String selection = parent.getItemAtPosition(position).toString();
                        ChartData containerDay = chartData.get(selection);
                        ArrayList<String> currentTime = containerDay.getTimes();
                        if(GraphActivity.whichActivity == 1)
                        {
                            currentValue = containerDay.getTemps();
                        }
                        else if(GraphActivity.whichActivity == 2)
                        {
                            currentValue = containerDay.getHumidities();
                        }
                        else if(GraphActivity.whichActivity == 3)
                        {
                            currentValue = containerDay.getMoistures();
                        }
                        else if(GraphActivity.whichActivity == 4)
                        {
                            currentValue = containerDay.getLitres();
                        }

                        Log.e("currentTime: ", currentTime.toString());
                        Log.e("currentValue: ", currentValue.toString());
                        cMV = new CustomMarkerView(CertainGraphActivity.this, R.layout.custom_marker_view_layout, format);
                        ds = new LineDataSet(currentValue, chartVal1);
                        ArrayList<ILineDataSet> iLineData = new ArrayList<ILineDataSet>();
                        ds.setDrawCubic(true);
                        ds.setDrawFilled(true);
                        ds.setDrawValues(false);
                        ds.setCircleRadius(2f);
                        ds.setCircleColor(Color.BLACK);
                        ds.setColor(Color.parseColor(color));
                        ds.setFillColor(Color.parseColor(color));
                        ds.setFillAlpha(90);
                        iLineData.add(ds);
                        data = new LineData(currentTime, iLineData);
                        lineChart.getAxisRight().setEnabled(false);
                        YAxis axis = lineChart.getAxisLeft();
                        axis.setValueFormatter(new MyYAxisValueFormatter(format));
                        axis.setDrawLabels(true);
                        axis.setDrawZeroLine(zeroLine);
                        axis.setAxisMinValue(min);
                        axis.setAxisMaxValue(max);
                        lineChart.setData(data);
                        lineChart.setMarkerView(cMV);
                        lineChart.setDescription(chartVal2);
                        lineChart.notifyDataSetChanged();
                        lineChart.invalidate();
                        lineChart.animateY(2000);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent)
                    {
                    }
                });
            }
            else
            {
                if (chartData != null)
                {
                    zoomIn.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v) {
                            onC.start();
                            lineChart.zoomIn();
                        }
                    });

                    zoomOut.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v) {
                            offC.start();
                            lineChart.zoomOut();
                        }
                    });
                    Log.e("KEYSET", chartData.keySet().toString());
                    Set<String> keys = chartData.keySet();
                    Log.e("TEST KEYS", keys.toString());
                    String[] keyValues = keys.toArray(new String[chartData.size()]);
                    Log.e("Key Values: ", Arrays.deepToString(keyValues));
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CertainGraphActivity.this, android.R.layout.simple_spinner_item, keyValues);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin.setAdapter(adapter);
                    int selection = keys.size() - 1;
                    spin.setSelection(selection);
                    spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                    {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selection = parent.getItemAtPosition(position).toString();
                            ChartData containerDay = chartData.get(selection);
                            ArrayList<String> currentTime = containerDay.getTimes();
                            if(GraphActivity.whichActivity == 1)
                            {
                                currentValue = containerDay.getTemps();
                            }
                            else if(GraphActivity.whichActivity == 2)
                            {
                                currentValue = containerDay.getHumidities();
                            }
                            else if(GraphActivity.whichActivity == 3)
                            {
                                currentValue = containerDay.getMoistures();
                            }
                            else if(GraphActivity.whichActivity == 4)
                            {
                                currentValue = containerDay.getLitres();
                            }

                            Log.e("currentTime: ", currentTime.toString());
                            Log.e("currentValue: ", currentValue.toString());
                            cMV = new CustomMarkerView(CertainGraphActivity.this, R.layout.custom_marker_view_layout, format);
                            ds = new LineDataSet(currentValue, chartVal1);
                            ArrayList<ILineDataSet> iLineData = new ArrayList<ILineDataSet>();
                            ds.setDrawCubic(true);
                            ds.setDrawFilled(true);
                            ds.setDrawValues(false);
                            ds.setCircleRadius(2f);
                            ds.setCircleColor(Color.BLACK);
                            ds.setColor(Color.parseColor(color));
                            ds.setFillColor(Color.parseColor(color));
                            ds.setFillAlpha(90);
                            iLineData.add(ds);
                            data = new LineData(currentTime, iLineData);
                            lineChart.getAxisRight().setEnabled(false);
                            YAxis axis = lineChart.getAxisLeft();
                            axis.setValueFormatter(new MyYAxisValueFormatter(format));
                            axis.setDrawLabels(true);
                            axis.setDrawZeroLine(zeroLine);
                            axis.setAxisMinValue(min);
                            axis.setAxisMaxValue(max);
                            lineChart.setData(data);
                            lineChart.setMarkerView(cMV);
                            lineChart.setDescription(chartVal2);
                            lineChart.notifyDataSetChanged();
                            lineChart.invalidate();
                            lineChart.animateY(2000);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                } else {
                    LinearLayout linLay = (LinearLayout) findViewById(R.id.linLayout);
                    linLay.removeView(zoomOut);
                    zoomIn.setText("Refresh");
                    zoomIn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new CertainGraphActivity.Task().execute();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });
                    lineChart.setNoDataText("No internet connection."); // this is the top line
                    lineChart.setNoDataTextDescription("..."); // this is one line below the no-data-text
                    lineChart.invalidate();
                }
            }
        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        offC.start();
        startActivity(new Intent(CertainGraphActivity.this, GraphActivity.class));
        finish();
        onLeaveThisActivity();
    }

    private void checkWhichActivity()
    {
        if(GraphActivity.whichActivity == 1)
        {
            spin.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_4));
            spin.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_4));
            zoomIn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_4));
            zoomOut.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_4));
            activityBack.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_4));
            chartVal1 = "Temperature";
            chartVal2 = "Temperature Graph";
            color = "#d35400";
            zeroLine = true;
            max = 40f;
            min = -10f;
            format = "°C";
        }
        else if(GraphActivity.whichActivity == 2)
        {
            spin.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_2));
            spin.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_2));
            zoomIn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_2));
            zoomOut.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_2));
            activityBack.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_2));
            chartVal1 = "Humidity";
            chartVal2 = "Humidity Graph";
            color = "#16a085";
            zeroLine = false;
            max = 100f;
            min = 0f;
            format = "%";
        }
        else if(GraphActivity.whichActivity == 3)
        {
            spin.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_3));
            spin.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_3));
            zoomIn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_3));
            zoomOut.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_3));
            activityBack.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_3));
            chartVal1 = "Moisture";
            chartVal2 = "Moisture Graph";
            color = "#27ae60";
            zeroLine = false;
            max = 100f;
            min = 0f;
            format = "%";
        }
        else if(GraphActivity.whichActivity == 4)
        {
            spin.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_clicked));
            spin.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_clicked));
            zoomIn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_clicked));
            zoomOut.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_clicked));
            activityBack.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_clicked));
            chartVal1 = "Water Consumption";
            chartVal2 = "Water Consumption Graph";
            color = "#27ae60";
            zeroLine = false;
            max = 100f;
            min = 0f;
            format = "l";
        }
    }

    /**
     * Gets user name.
     *This method retrieves the stored login information for the SSH connection.
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



package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by User on 02/03/2017.
 * This class is used in order to format the Y-axis values of the graphs. It also appends a
 * unit of measurement symbol to it.
 */
public class MyYAxisValueFormatter implements YAxisValueFormatter
{
    String format;
    private DecimalFormat mFormat;

    public MyYAxisValueFormatter(String s)
    {
        format = s;
        mFormat = new DecimalFormat("###,###,##0");
    }
    @Override
    public String getFormattedValue(float v, YAxis yAxis)
    {
        String formatter = format;
        return mFormat.format(v) + formatter;
    }
}

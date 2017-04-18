package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

/**
 * Created by User on 02/03/2017.
 * This method is used by the graphs of the application. With its help,
 * once the user clicks on a certain point on the graph, its value is shown in a
 * small popup. Also, it attaches a unit of measurement symbol after this value.
 */

public class CustomMarkerView extends MarkerView
{
    private TextView tvContent;
    String test;

    public CustomMarkerView(Context context, int layoutResource, String s)
    {
        super(context, layoutResource);
        test = s;
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvContent.setTextColor(Color.BLACK);
        tvContent.setTextSize(16f);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight)
    {
        String newTest = test;
        Log.e("TUUUUUUUUUUUK", "asd");
        tvContent.setText(e.getVal()+newTest);
    }

    @Override
    public int getXOffset(float xpos)
    {
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos)
    {
        return -getHeight();
    }
}
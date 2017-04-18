package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class witch encapsulates all of the necessary properties for a chart
 * Created by User on 19/03/2017.
 * This clas is used to store the information for the graphs. To do so it uses 5 ArrayLists which are then loaded with
 * information via the insertData() method. It takes 5 Strings as parameters as well as an integer used for counter.
 * This information is loaded into the created arrayLists. At the end of the class, the equals() method is overwritten in
 * order to make unit testing possible. Also a few get methods are created in order to allow the programmer to
 * easily get the desired data set.
 */

public class ChartData
{
    private ArrayList<String> times = new ArrayList<>();
    private ArrayList<Entry> temps = new ArrayList<>();
    private ArrayList<Entry> humidities = new ArrayList<>();
    private ArrayList<Entry> moistures = new ArrayList<>();
    private ArrayList<BarEntry> litres = new ArrayList<>();

    public ChartData()
    {
    }

    public void insertData(String time, String temp, String humidity, String moisture, String litre, int count)
    {
            times.add(time);
            temps.add(new Entry(Float.parseFloat(temp), count));
            humidities.add(new Entry(Float.parseFloat(humidity), count));
            moistures.add(new Entry(Float.parseFloat(moisture), count));
            litres.add(new BarEntry(Float.parseFloat(litre), count));
    }


    public ArrayList<String> getTimes()
    {
        return times;
    }

    public ArrayList<Entry> getTemps(){
        return temps;
    }

    public ArrayList<Entry> getHumidities()
    {
        return humidities;
    }

    public ArrayList<Entry> getMoistures()
    {
        return moistures;
    }

    public ArrayList<BarEntry> getLitres()
    {
        return litres;
    }

    @Override
    public boolean equals(Object o)
    {
        ChartData dataSet = (ChartData) o;
        for(int i=0; i<this.times.size(); i++)
        {
            if(!this.times.get(i).equals(dataSet.getTimes().get(i)) ||  !this.temps.get(i).equalTo(dataSet.getTemps().get(i)) || !this.humidities.get(i).equalTo(dataSet.getHumidities().get(i)) || !this.moistures.get(i).equalTo(dataSet.getMoistures().get(i)) || !this.litres.get(i).equalTo(dataSet.getLitres().get(i)))
            {
                return false;
            }
        }
        return true;
    }
}

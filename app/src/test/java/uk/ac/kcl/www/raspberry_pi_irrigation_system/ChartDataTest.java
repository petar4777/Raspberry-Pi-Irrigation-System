package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by User on 19/03/2017.
 */
public class ChartDataTest
{
    final String time = "10:00";
    final String temp = "20.1";
    final String hum = "41.0";
    final String moist = "800";
    final String lit = "5.1";
    final int count = 0;
    private ArrayList<String> times = new ArrayList<>();
    private ArrayList<Entry> temps = new ArrayList<>();
    private ArrayList<Entry> humidities = new ArrayList<>();
    private ArrayList<Entry> moistures = new ArrayList<>();
    private ArrayList<BarEntry> litres = new ArrayList<>();
    final ChartData test = new ChartData();

    @Before
    public void setUp()
    {
        times.add(time);
        temps.add(new Entry(Float.parseFloat(temp), count));
        humidities.add(new Entry(Float.parseFloat(hum), count));
        moistures.add(new Entry(Float.parseFloat(moist), count));
        litres.add(new BarEntry(Float.parseFloat(lit), count));
        test.insertData(time, temp, hum, moist, lit, count);
    }

    @Test
    public void insertData() throws Exception
    {
        assertEquals(test.getTimes().toString(), times.toString());
        assertEquals(test.getTemps().toString(), temps.toString());
        assertEquals(test.getHumidities().toString(), humidities.toString());
        assertEquals(test.getMoistures().toString(), moistures.toString());
        assertEquals(test.getLitres().toString(), litres.toString());

    }

    @Test
    public void getTimes() throws Exception
    {
        assertEquals(times.toString(), test.getTimes().toString());
    }

    @Test
    public void getTemps() throws Exception
    {
        assertEquals(temps.toString(), test.getTemps().toString());
    }

    @Test
    public void getHumidities() throws Exception
    {
        assertEquals(humidities.toString(), test.getHumidities().toString());
    }

    @Test
    public void getMoistures() throws Exception
    {
        assertEquals(moistures.toString(), test.getMoistures().toString());
    }

    @Test
    public void getLitres() throws Exception
    {
        assertEquals(litres.toString(), test.getLitres().toString());
    }

    @Test
    public void equals() throws Exception
    {
        ChartData test1 = new ChartData();
        times.add(time);
        temps.add(new Entry(Float.parseFloat(temp), count));
        humidities.add(new Entry(Float.parseFloat(hum), count));
        moistures.add(new Entry(Float.parseFloat(moist), count));
        litres.add(new BarEntry(Float.parseFloat(lit), count));
        test1.insertData(time, temp, hum, moist, lit, count);

        assertTrue(test.equals(test1));
    }
}
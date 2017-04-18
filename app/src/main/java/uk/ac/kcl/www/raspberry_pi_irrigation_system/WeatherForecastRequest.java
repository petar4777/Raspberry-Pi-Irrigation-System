package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by User on 25/02/2017.
 * This class is responsible for the HTTP request that downloads the weather forecast information.
 */
public class WeatherForecastRequest
{
    private Thread connection;
    private String[] temperatureValues = new String[6];
    private String[] getWeather = new String[3];
    private String type;
    /**
     * The Url. This URL is used to store the URL which is responsible for requesting a three-day forecast from OpenWeatherMap Api.
     */
    URL url = null;

    /**
     * Instantiates a new Weather forecast request.
     * This is the constructor of the class, inside of it the HTTP request is made.
     */
    public WeatherForecastRequest()
    {
        connection = new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                try
                {
                    url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=london&units=metric&APPID=b3046b81a76c3f6dd985c7442b75fc12&cnt=3");
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
                HttpURLConnection urlConnection = null;
                try
                {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    Log.e("Weather Request: ", "Connected");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                try
                {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    while((line = in.readLine()) != null)
                    {
                        try
                        {
                            JSONObject object = new JSONObject(line);
                            JSONArray array = object.getJSONArray("list");
                            JSONObject expl;

                            int count = 0;
                            for(int i= 0; i<array.length(); i++)
                            {

                                expl = array.getJSONObject(i);
                                JSONObject temperature = expl.getJSONObject("temp");
                                type = expl.getString("weather");
                                JSONArray getWeatherV = new JSONArray(type);
                                JSONObject getDescription = getWeatherV.getJSONObject(0);
                                getWeather[i] = getDescription.getString("description");
                                String max = temperature.getString("day");
                                String min = temperature.getString("night");
                                temperatureValues[count] = max;
                                count++;
                                temperatureValues[count] = min;
                                count++;
                            }
                            Log.e("Weather Request: OK ", Arrays.deepToString(temperatureValues));
                        }
                        catch (Throwable t)
                        {
                            Log.e("Weather Error: ", "problem with json objects for loop");
                        }

                    }
                }

                catch (IOException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    urlConnection.disconnect();
                }
            }
        });
        connection.start();
    }

    /**
     * Gets connection.
     *This method returns the Thread. It is created in order to check whether the HTTP reuqest has finished
     * its execution.
     * @return the connection
     */
    public Thread getConnection()
    {
        return connection;
    }

    /**
     * Get temperature values string [ ].
     * This method returns the minimum and maximum temperature values.
     *
     * @return the string [ ]
     */
    public String[] getTemperatureValues()
    {
        return temperatureValues;
    }

    /**
     * Get weather string [ ].
     *This method is used to return the type of weather.
     * @return the string [ ]
     */
    public  String[] getWeather()
    {
        return getWeather;
    }

}

package uk.ac.kcl.www.raspberry_pi_irrigation_system;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Arrays;

/**
 * The type Main activity. This is the main activity of the application. Inside of it the user is presented with the most important information
 * about his irrigation system. On top the three-day weather forecast is located. Underneath it there are three buttons used to start the
 * automatic irrigation scripts. Below them a stop button is made in order to stop all irrigation scripts currently running on the
 * Raspberry pi. Underneath this button a grid layout is created. It consists of 5 buttons. First, a graph button is made which starts the GraphActivity.
 * The next three buttons are used as a dashboard where the user will be able to see the current sensor readings. Finally, the last button is a
 * refresh button which reloads the current sensor values. On the bottom of the screen, a button which leads to the manualIrrigationActivity is created.
 */
public class MainActivity extends BaseActivity
{
    /**
     * The Type of weather values.
     */
    String[] typeOfWeatherValues;
    /**
     * The Tod tow.
     */
    String todTOW;
    /**
     * The Tom tow.
     */
    String tomTOW;
    /**
     * The Atom tow.
     */
    String atomTOW;
    /**
     * The Graph button.
     */
    ImageButton graphButton;
    /**
     * The Temp button ma.
     */
    Button tempButtonMA;
    /**
     * The Hum button ma.
     */
    Button humButtonMA;
    /**
     * The Moist button ma.
     */
    Button moistButtonMA;
    /**
     * The Today.
     */
    Button today;
    /**
     * The Tomorrow.
     */
    Button tomorrow;
    /**
     * The After tomorrow.
     */
    Button afterTomorrow;
    /**
     * The Stop.
     */
    Button stop;
    /**
     * The Refresh button ma.
     */
    ImageButton refreshButtonMA;
    /**
     * The Automatic bot.
     */
    ImageButton automaticBOT;
    /**
     * The Automatic bom.
     */
    ImageButton automaticBOM;
    /**
     * The Automatic bof.
     */
    ImageButton automaticBOF;
    /**
     * The Manuar irrigation.
     */
    Button manuarIrrigation;
    /**
     * The On c.
     */
    MediaPlayer onC;
    /**
     * The Off c.
     */
    MediaPlayer offC;
    /**
     * The Fancy c.
     */
    MediaPlayer fancyC;
    /**
     * The Split values.
     */
    public static String[] splitValues;
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
     * The Split data.
     */
    String[] splitData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BUTTONS
        graphButton = (ImageButton) findViewById(R.id.mainActivityGraphs);
        tempButtonMA = (Button) findViewById(R.id.TempButtonMA);
        humButtonMA = (Button) findViewById(R.id.HumButtonMA);
        moistButtonMA = (Button) findViewById(R.id.MoistButtonMA);
        today = (Button) findViewById(R.id.Today);
        tomorrow = (Button) findViewById(R.id.Tomorrow);
        afterTomorrow = (Button) findViewById(R.id.AfterTomorrow);
        refreshButtonMA = (ImageButton) findViewById(R.id.RefreshButtonMA);
        automaticBOT = (ImageButton) findViewById(R.id.automaticBasedOnTemperatureButton);
        automaticBOM = (ImageButton) findViewById(R.id.automaticBasedOnMoistureButton);
        automaticBOF = (ImageButton) findViewById(R.id.automaticBasedOnForecastButton);
        manuarIrrigation = (Button) findViewById(R.id.manualIrrigationButton);
        stop = (Button) findViewById(R.id.buttonStop);
        onC = MediaPlayer.create(this, R.raw.on);
        offC = MediaPlayer.create(this, R.raw.off);
        fancyC = MediaPlayer.create(this, R.raw.fancy);
        new Task().execute();
        //END OF BUTTONS

        //ONCLICKS
        graphButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                onC.start();
                if(CheckInternet.isInternetAvailable(MainActivity.this))
                {
                    startActivity(new Intent(MainActivity.this, GraphActivity.class));
                }
                else
                {
                    new Task().execute();
                }
            }
        });

        manuarIrrigation.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                onC.start();
                if(CheckInternet.isInternetAvailable(MainActivity.this))
                {
                    startActivity(new Intent(MainActivity.this, ManualIrrigationActivity.class));
                }
                else
                {
                    new Task().execute();
                }
            }
        });
        //END OF ONCLICKS
    }

    /**
     * Tod to wicon. This method is used to compare the retrieved weather type from the weather HTTP request with all the possible weather types.
     * Once a match is found, the drawable which represents the weather type is assigned.
     */
    public void todTOWicon()
    {
        if(todTOW.equals("clear sky") || todTOW.equals("sky is clear"))
        {
            today.setCompoundDrawablesWithIntrinsicBounds(R.drawable.a,0,0,0);
        }
        if(todTOW.equals("few clouds"))
        {
            today.setCompoundDrawablesWithIntrinsicBounds(R.drawable.b,0,0,0);
        }
        if(todTOW.equals("scattered clouds"))
        {
            today.setCompoundDrawablesWithIntrinsicBounds(R.drawable.c,0,0,0);
        }
        if(todTOW.equals("broken clouds") || todTOW.equals("overcast clouds"))
        {
            today.setCompoundDrawablesWithIntrinsicBounds(R.drawable.d,0,0,0);
        }
        if(todTOW.equals("shower rain") || todTOW.equals("light intensity drizzle") || todTOW.equals("drizzle") || todTOW.equals("heavy intensity drizzle") || todTOW.equals("light intensity drizzle rain") || todTOW.equals("shower rain and drizzle") || todTOW.equals("heavy shower rain and drizzle") || todTOW.equals("shower drizzle") || todTOW.equals("light intensity shower rain") || todTOW.equals("heavy intensity shower rain") || todTOW.equals("ragged shower rain"))
        {
            today.setCompoundDrawablesWithIntrinsicBounds(R.drawable.e,0,0,0);
        }
        if(todTOW.equals("rain") || todTOW.equals("light rain") || todTOW.equals("moderate rain") || todTOW.equals("heavy intensity rain") || todTOW.equals("very heavy rain") || todTOW.equals("extreme rain"))
        {
            today.setCompoundDrawablesWithIntrinsicBounds(R.drawable.f,0,0,0);
        }
        if(todTOW.equals("thunderstorm") || todTOW.equals("thunderstorm with light rain") || todTOW.equals("thunderstorm with rain") || todTOW.equals("thunderstorm with heavy rain") || todTOW.equals("light thunderstorm") || todTOW.equals("heavy thunderstorm") || todTOW.equals("ragged thunderstorm") || todTOW.equals("thunderstorm with light drizzle") || todTOW.equals("thunderstorm with drizzle") || todTOW.equals("thunderstorm with heavy drizzle"))
        {
            today.setCompoundDrawablesWithIntrinsicBounds(R.drawable.g,0,0,0);
        }
        if(todTOW.equals("snow") || todTOW.equals("frezzing rain") || todTOW.equals("light snow") || todTOW.equals("heavy snow") || todTOW.equals("sleet") || todTOW.equals("shower sleet") || todTOW.equals("light rain and snow") || todTOW.equals("rain and snow") || todTOW.equals("light shower snow") || todTOW.equals("shower snow") || todTOW.equals("heavy shower snow"))
        {
            today.setCompoundDrawablesWithIntrinsicBounds(R.drawable.h,0,0,0);
        }
        if(todTOW.equals("mist") || todTOW.equals("smoke") || todTOW.equals("haze") || todTOW.equals("sand, dust whirls") || todTOW.equals("fog") || todTOW.equals("sand") || todTOW.equals("dust") || todTOW.equals("volcanic ash") || todTOW.equals("squalls") || todTOW.equals("tornado"))
        {
            today.setCompoundDrawablesWithIntrinsicBounds(R.drawable.i,0,0,0);
        }
    }

    /**
     * Tom to wicon. This method is used to compare the retrieved weather type from the weather HTTP request with all the possible weather types.
     * Once a match is found, the drawable which represents the weather type is assigned.
     */
    public void tomTOWicon()
    {
        if(tomTOW.equals("clear sky") || tomTOW.equals("sky is clear"))
        {
            tomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.a,0,0,0);
        }
        if(tomTOW.equals("few clouds"))
        {
            tomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.b,0,0,0);
        }
        if(tomTOW.equals("scattered clouds"))
        {
            tomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.c,0,0,0);
        }
        if(tomTOW.equals("broken clouds") || tomTOW.equals("overcast clouds"))
        {
            tomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.d,0,0,0);
        }
        if(tomTOW.equals("shower rain") || tomTOW.equals("light intensity drizzle") || tomTOW.equals("drizzle") || tomTOW.equals("heavy intensity drizzle") || tomTOW.equals("light intensity drizzle rain") || tomTOW.equals("shower rain and drizzle") || tomTOW.equals("heavy shower rain and drizzle") || tomTOW.equals("shower drizzle") || tomTOW.equals("light intensity shower rain") || tomTOW.equals("heavy intensity shower rain") || tomTOW.equals("ragged shower rain"))
        {
            tomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.e,0,0,0);
        }
        if(tomTOW.equals("rain") || tomTOW.equals("light rain") || tomTOW.equals("moderate rain") || tomTOW.equals("heavy intensity rain") || tomTOW.equals("very heavy rain") || tomTOW.equals("extreme rain"))
        {
            tomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.f,0,0,0);
        }
        if(tomTOW.equals("thunderstorm") || tomTOW.equals("thunderstorm with light rain") || tomTOW.equals("thunderstorm with rain") || tomTOW.equals("thunderstorm with heavy rain") || tomTOW.equals("light thunderstorm") || tomTOW.equals("heavy thunderstorm") || tomTOW.equals("ragged thunderstorm") || tomTOW.equals("thunderstorm with light drizzle") || tomTOW.equals("thunderstorm with drizzle") || tomTOW.equals("thunderstorm with heavy drizzle"))
        {
            tomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.g,0,0,0);
        }
        if(tomTOW.equals("snow") || tomTOW.equals("frezzing rain") || tomTOW.equals("light snow") || tomTOW.equals("heavy snow") || tomTOW.equals("sleet") || tomTOW.equals("shower sleet") || tomTOW.equals("light rain and snow") || tomTOW.equals("rain and snow") || tomTOW.equals("light shower snow") || tomTOW.equals("shower snow") || tomTOW.equals("heavy shower snow"))
        {
            tomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.h,0,0,0);
        }
        if(tomTOW.equals("mist") || tomTOW.equals("smoke") || tomTOW.equals("haze") || tomTOW.equals("sand, dust whirls") || tomTOW.equals("fog") || tomTOW.equals("sand") || tomTOW.equals("dust") || tomTOW.equals("volcanic ash") || tomTOW.equals("squalls") || tomTOW.equals("tornado"))
        {
            tomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.i,0,0,0);
        }
    }

    /**
     * Atom to wicon. This method is used to compare the retrieved weather type from the weather HTTP request with all the possible weather types.
     * Once a match is found, the drawable which represents the weather type is assigned.
     */
    public void atomTOWicon()
    {
        if(atomTOW.equals("clear sky") || atomTOW.equals("sky is clear"))
        {
            afterTomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.a,0,0,0);
        }
        if(atomTOW.equals("few clouds"))
        {
            afterTomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.b,0,0,0);
        }
        if(atomTOW.equals("scattered clouds"))
        {
            afterTomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.c,0,0,0);
        }
        if(atomTOW.equals("broken clouds") || atomTOW.equals("overcast clouds"))
        {
            afterTomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.d,0,0,0);
        }
        if(atomTOW.equals("shower rain") || atomTOW.equals("light intensity drizzle") || atomTOW.equals("drizzle") || atomTOW.equals("heavy intensity drizzle") || atomTOW.equals("light intensity drizzle rain") || atomTOW.equals("shower rain and drizzle") || atomTOW.equals("heavy shower rain and drizzle") || atomTOW.equals("shower drizzle") || atomTOW.equals("light intensity shower rain") || atomTOW.equals("heavy intensity shower rain") || atomTOW.equals("ragged shower rain"))
        {
            afterTomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.e,0,0,0);
        }
        if(atomTOW.equals("rain") || atomTOW.equals("light rain") || atomTOW.equals("moderate rain") || atomTOW.equals("heavy intensity rain") || atomTOW.equals("very heavy rain") || atomTOW.equals("extreme rain"))
        {
            afterTomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.f,0,0,0);
        }
        if(atomTOW.equals("thunderstorm") || atomTOW.equals("thunderstorm with light rain") || atomTOW.equals("thunderstorm with rain") || atomTOW.equals("thunderstorm with heavy rain") || atomTOW.equals("light thunderstorm") || atomTOW.equals("heavy thunderstorm") || atomTOW.equals("ragged thunderstorm") || atomTOW.equals("thunderstorm with light drizzle") || atomTOW.equals("thunderstorm with drizzle") || atomTOW.equals("thunderstorm with heavy drizzle"))
        {
            afterTomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.g,0,0,0);
        }
        if(atomTOW.equals("snow") || atomTOW.equals("frezzing rain") || atomTOW.equals("light snow") || atomTOW.equals("heavy snow") || atomTOW.equals("sleet") || atomTOW.equals("shower sleet") || atomTOW.equals("light rain and snow") || atomTOW.equals("rain and snow") || atomTOW.equals("light shower snow") || atomTOW.equals("shower snow") || atomTOW.equals("heavy shower snow"))
        {
            afterTomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.h,0,0,0);
        }
        if(atomTOW.equals("mist") || atomTOW.equals("smoke") || atomTOW.equals("haze") || atomTOW.equals("sand, dust whirls") || atomTOW.equals("fog") || atomTOW.equals("sand") || atomTOW.equals("dust") || atomTOW.equals("volcanic ash") || atomTOW.equals("squalls") || atomTOW.equals("tornado"))
        {
            afterTomorrow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.i,0,0,0);
        }
    }

    /**
     * The type Task. This AsyncTask is used to download the last sensor readings, the name of the current running script on the raspberry Pi and to
     * make a weather HTTP request. The downloaded data is filled in a string array. If there is no internet connection, the first three positions of
     * the string array are assigned with "Check" "your" and "connection", this is done in order to inform the onPostExecution method that there is no
     * internet conneciton. The onPostExecution method recieves the String Array created by the doInBackground method as input.
     * The information stored in it is used to fill all the buttons with the needed data and all the onClikcListeners are created. If the String Array is empty because there is no
     * internet connection, the layout of the app is changed. The button backgrounds are set to grey and they are made non clickable.
     */
    public class Task extends AsyncTask<Object, Object, String[]>
    {

        @Override
        protected String[] doInBackground(Object... params)
        {
            //CHECK IF SSH IS ALIVE OR NOT ?!?!?!
            if(CheckInternet.isInternetAvailable(MainActivity.this))
            {
                String storedInformation = getUserName(MainActivity.this);
                splitValues = storedInformation.split("/");
                SSHConnectionFinal ssh = new SSHConnectionFinal(new String[] {"cd Desktop;cd FinalTest1;python3 getLastSensorValues.py"}, false, splitValues);
                SSHConnectionFinal check = new SSHConnectionFinal(new String[] {"cd Desktop;cd FinalTest1;cat whichIsRunning.txt"}, false, splitValues);
                Log.e("STRING", "passed");
                WeatherForecastRequest request = new WeatherForecastRequest();
                while(request.getConnection().isAlive())
                {
                }

                String checkWhichIsRunning = check.getFileContents();
                String fileContents = ssh.getFileContents();
                String[] splitDataFirst = fileContents.split("/");
                String[] temperatureValues = request.getTemperatureValues();
                typeOfWeatherValues = request.getWeather();
                String splitDataFirstOne = splitDataFirst[0];
                String splitDataFirstTwo = splitDataFirst[1];
                String splitDataFirstThree = splitDataFirst[2];
                todTOW = typeOfWeatherValues[0];
                tomTOW = typeOfWeatherValues[1];
                atomTOW = typeOfWeatherValues[2];
                String dayOneDay = temperatureValues[0];
                String dayOneNight = temperatureValues[1];
                String dayTwoDay = temperatureValues[2];
                String dayTwoNight = temperatureValues[3];
                String dayThreeDay = temperatureValues[4];
                String dayThreeNight = temperatureValues[5];
                splitData = new String[13];
                splitData[0] = splitDataFirstOne;
                splitData[1] = splitDataFirstTwo;
                splitData[2] = splitDataFirstThree;
                splitData[3] = todTOW;
                splitData[4] = tomTOW;
                splitData[5] = atomTOW;
                splitData[6] = dayOneDay;
                splitData[7] = dayOneNight;
                splitData[8] = dayTwoDay;
                splitData[9] = dayTwoNight;
                splitData[10] = dayThreeDay;
                splitData[11] = dayThreeNight;
                splitData[12] = checkWhichIsRunning;
                Log.e("splitData: ", Arrays.deepToString(splitData));
                return splitData;
            }
            else
            {
               Log.e("PUSNI NETA BE ","PUSNI NETA BE");
                splitData = new String[13];
                splitData[0] = "Check";
                splitData[1] = "your";
                splitData[2] = "connection";
                splitData[3] = null;
                splitData[4] = null;
                splitData[5] = null;
                splitData[6] = null;
                splitData[7] = null;
                splitData[8] = null;
                splitData[9] = null;
                splitData[10] = null;
                splitData[11] = null;
                splitData[12] = null;
            }
            return splitData;
        }

        @Override
        protected void onPostExecute(final String[] splitData)
        {
            super.onPostExecute(splitData);
            if(splitData[0].equals("Check"))
            {
                tempButtonMA.setText("Check");
                humButtonMA.setText("connection");
                moistButtonMA.setText("your");
                Log.e("ASYNC FALSE", "TUK");
                automaticBOF.setClickable(false);
                automaticBOM.setClickable(false);
                automaticBOT.setClickable(false);
                manuarIrrigation.setClickable(false);
                stop.setClickable(false);
                graphButton.setClickable(false);
                stop.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_not_clickable));
                automaticBOF.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_not_clickable));
                automaticBOM.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_not_clickable));
                automaticBOT.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_not_clickable));
                manuarIrrigation.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_not_clickable));
                graphButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_not_clickable));
                tempButtonMA.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_not_clickable));
                humButtonMA.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_not_clickable));
                moistButtonMA.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_not_clickable));
                if(splitData[6] != null)
                {
                    today.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    tomorrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    afterTomorrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    today.setText("˄ " + splitData[6] + "°C\n˅ " + splitData[7] + "°C");
                    tomorrow.setText("˄ " + splitData[8] + "°C\n˅ " + splitData[9] + "°C");
                    afterTomorrow.setText("˄ " + splitData[10] + "°C\n˅ " + splitData[11] + "°C");

                    todTOWicon();
                    tomTOWicon();
                    atomTOWicon();

                }
                else
                {
                    today.setText("Weather");
                    tomorrow.setText("request");
                    afterTomorrow.setText("problem");
                    today.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tomorrow.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    afterTomorrow.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    today.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_not_clickable));
                    tomorrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_not_clickable));
                    afterTomorrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_not_clickable));

                }
            }
            else
            {
                tempButtonMA.setText("T: "+splitData[0]+"°C");
                humButtonMA.setText("H: "+splitData[1]+"%");
                moistButtonMA.setText("M: "+splitData[2].trim()+"%RH");
                Log.e("String problem", splitData[1].toString());
                Log.e("TRUE","TYK");
                automaticBOF.setClickable(true);
                automaticBOM.setClickable(true);
                automaticBOT.setClickable(true);
                manuarIrrigation.setClickable(true);
                graphButton.setClickable(true);
                stop.setClickable(true);
                tempButtonMA.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_2));
                humButtonMA.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_2));
                moistButtonMA.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_2));
                stop.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_6));
                automaticBOF.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                automaticBOM.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                automaticBOT.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                graphButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                manuarIrrigation.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                if(splitData[12].trim().equals("automaticBasedOnForecast.py"))
                {
                    Log.e("Check: ", "Forecast is on");
                    automaticBOF.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_clicked));
                }
                else if(splitData[12].trim().equals("automaticBasedOnTemperature.py"))
                {
                    Log.e("Check: ", "Temperature is on");
                    automaticBOT.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_clicked));
                }
                else if(splitData[12].trim().equals("automaticBasedOnMoisture.py"))
                {
                    Log.e("Check: ", "Moisture is on");
                    automaticBOM.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_clicked));
                }
                else if(splitData[12].trim().equals("manualBasedOnPlan.py"))
                {
                    Log.e("Check: ", "Manual mode is on");
                    manuarIrrigation.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_clicked));
                }
                else if(splitData[12].trim().equals("nothing"))
                {
                    Log.e("Check: ", "Nothing is running");
                    automaticBOT.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    automaticBOM.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    automaticBOF.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    manuarIrrigation.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                }

                if(splitData[6] != null)
                {
                    today.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    tomorrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    afterTomorrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    today.setText("˄ "+splitData[6]+"°C\n˅ "+splitData[7]+"°C");
                    tomorrow.setText("˄ "+splitData[8]+"°C\n˅ "+splitData[9]+"°C");
                    afterTomorrow.setText("˄ "+splitData[10]+"°C\n˅ "+splitData[11]+"°C");
                    todTOWicon();
                    tomTOWicon();
                    atomTOWicon();

                }
                else
                {
                    today.setText("Weather");
                    tomorrow.setText("request");
                    afterTomorrow.setText("problem");
                    today.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    tomorrow.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    afterTomorrow.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    today.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_not_clickable));
                    tomorrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_not_clickable));
                    afterTomorrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape_not_clickable));
                }
            }

            refreshButtonMA.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    onC.start();
                    new Task().execute();
                }
            });

            automaticBOT.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    fancyC.start();
                    String[] command = new String[5];
                    command[0] = "pkill -9 -f automaticBasedOnTemperature.py";
                    command[1] = "pkill -9 -f automaticBasedOnMoisture.py";
                    command[2] = "pkill -9 -f automaticBasedOnForecast.py";
                    command[3] = "pkill -9 -f manualBasedOnPlan.py";
                    command[4] = "cd Desktop;cd FinalTest1;nohup python3 automaticBasedOnTemperature.py &";
                    Log.e("SPLITDATA PROBELM:", Arrays.deepToString(splitData));
                    SSHConnectionFinal auto = new SSHConnectionFinal(command, true, splitValues);
                    Log.e("Check: ", "AUTOBOT");
                    automaticBOT.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_clicked));
                    automaticBOM.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    automaticBOF.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    manuarIrrigation.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                }
            });

            automaticBOM.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    fancyC.start();
                    String[] command = new String[5];
                    command[0] = "pkill -9 -f automaticBasedOnTemperature.py";
                    command[1] = "pkill -9 -f automaticBasedOnMoisture.py";
                    command[2] = "pkill -9 -f automaticBasedOnForecast.py";
                    command[3] = "pkill -9 -f manualBasedOnPlan.py";
                    command[4] = "cd Desktop;cd FinalTest1;nohup python3 automaticBasedOnMoisture.py &";
                    SSHConnectionFinal auto = new SSHConnectionFinal(command, true, splitValues);
                    automaticBOT.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    automaticBOM.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_clicked));
                    automaticBOF.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    manuarIrrigation.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                }
            });

            automaticBOF.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    fancyC.start();
                    String[] command = new String[5];
                    command[0] = "pkill -9 -f automaticBasedOnTemperature.py";
                    command[1] = "pkill -9 -f automaticBasedOnMoisture.py";
                    command[2] = "pkill -9 -f automaticBasedOnForecast.py";
                    command[3] = "pkill -9 -f manualBasedOnPlan.py";
                    command[4] = "cd Desktop;cd FinalTest1;nohup python3 automaticBasedOnForecast.py &";
                    SSHConnectionFinal auto = new SSHConnectionFinal(command, true, splitValues);
                    Log.e("Check: ", "AUTOBOF");
                    automaticBOT.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    automaticBOM.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    automaticBOF.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape_clicked));
                    manuarIrrigation.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                }
            });

            stop.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    offC.start();
                    String[] command = new String[5];
                    command[0] = "pkill -9 -f automaticBasedOnTemperature.py";
                    command[1] = "pkill -9 -f automaticBasedOnMoisture.py";
                    command[2] = "pkill -9 -f automaticBasedOnForecast.py";
                    command[3] = "pkill -9 -f manualBasedOnPlan.py";
                    command[4] = "cd Desktop; cd FinalTest1;python stopWrite.py";
                    SSHConnectionFinal auto = new SSHConnectionFinal(command, true, splitValues);
                    Log.e("Check: ", "All stopped");
                    automaticBOT.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    automaticBOM.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    automaticBOF.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                    manuarIrrigation.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_effect_button_shape));
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Gets user name. This method is used to get the IP, Username and Password which are required for the SSH Connection.
     *
     * @param context the context
     * @return the user name
     */
    public String getUserName(Context context) {
        SharedPreferences savedSession = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        SharedPreferences savedSession2 = context.getSharedPreferences(KEY3,Activity.MODE_PRIVATE);
        SharedPreferences savedSession3 = context.getSharedPreferences(KEY4,Activity.MODE_PRIVATE);
        String all = savedSession.getString("password","")+"/"+savedSession2.getString("IP","")+"/"+ savedSession3.getString("username","");
        return all;

    }
}

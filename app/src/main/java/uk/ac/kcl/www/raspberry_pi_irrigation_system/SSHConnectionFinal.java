package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Created by User on 19/03/2017.
 * This class is responsible for creating the SSH connection.
 */
public class SSHConnectionFinal
{
    // THINGS
    private String fileContents = "";
    private Thread connection;
    /**
     * The Session.
     */
    Session session;
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
    //END OF THINGS

    /**
     * Instantiates a new Ssh connection final.
     *
     * @param commands   the commands
     * @param dontRead   the dont read
     * @param storedInfo the stored info
     */
//SSH CONNECTION
    public SSHConnectionFinal(final String[] commands, final boolean dontRead, final String[] storedInfo)
    {
        connection = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                JSch jsch;
                try
                {
                    Log.e("STOEDINFO", Arrays.deepToString(storedInfo));
                    String ip = storedInfo[1].trim();
                    String username = storedInfo[2].trim();
                    String password = storedInfo[0].trim();
                    jsch = new JSch();
                    session = jsch.getSession(username, ip , 22);
                    session.setPassword(password);
                    Properties prop = new Properties();
                    prop.put("StrictHostKeyChecking", "no");
                    session.setConfig(prop);

                    session.connect(3000);
                    if(session.isConnected())
                    {
                        System.out.println("SSH Connection: SUCCESS");
                           for(String command: commands)
                           {
                               //connection.sleep(0,3);
                               Log.e("TEST", "TUK");
                               executeReadCommand(session, command, dontRead);
                           }

                    }
                }
                catch (Exception e)
                {
                    System.out.println("SSH Connection: FAILURE");
                    session.disconnect();
                    fileContents = "Check/your/connection";
                }
            }
        });

        connection.start();
        while(connection.isAlive())
        {
            //do nothing
        }
    }
    //END OF SSH CONNECTION

    //executeReadCommand
    private void executeReadCommand(Session session, String command, boolean dontRead) throws JSchException, IOException
    {
        ChannelExec channelssh = executeCommand(session, command);
        BufferedReader br = new BufferedReader(new InputStreamReader(channelssh.getInputStream()));
        String line = null;
        int count = 0;
        if(!dontRead) {
            while ((line = br.readLine()) != null) {
                fileContents += line + "\n";
                count++;
            }
        }
        channelssh.disconnect();
    }
    //END of executeReadCommand

    //executeCommand
    @NonNull
    private ChannelExec executeCommand(Session session, String command) throws JSchException, IOException
    {
        ChannelExec channelssh = (ChannelExec) session.openChannel("exec");
        channelssh.setCommand(command);
        channelssh.connect();
        return channelssh;
    }
    //END of executeCommand

    /**
     * Parse data to tree map map.
     *This method is used to store the information which is retrieved by the SSH connection inside of a TreeMap.
     * @param data the data
     * @return the map
     */
//Method for parsing the string data into
    // hashmaps of entry or barentry objects
    // use option generateBarData to change between Entry and BarEntry objects
    public Map<String, ChartData> parseDataToTreeMap(String data)
    {
        String[] splitDataLines = data.split("\n");
        Map<String, ChartData> barData = new TreeMap<>();
        int count = 0;
        for(String line: splitDataLines)
        {
            String[] splitLine = line.split("/");
            String date = splitLine[0];
            String time = splitLine[1];
            String temp = splitLine[2];
            String humidity = splitLine[3];
            String moisture = splitLine[4];
            String litres = splitLine[5];

            if(barData.get(date) == null){
                Log.e("TEST", date);
                count = 0;
                ChartData dataSet = new ChartData();
                dataSet.insertData(time, temp, humidity, moisture, litres, count);
                barData.put(date, dataSet);
            }else{
                ChartData dataSet = barData.get(date);
                dataSet.insertData(time, temp, humidity, moisture, litres, count);
            }
            count++;
        }
        Log.e("TESTING BARDATA", barData.toString());
        return barData;
    }
    //END OF HASHMAP

    /**
     * Parse data to array list array list.
     *This method is created in order to store the retrieved information from the SSH connection into an arrayList.
     * @param data the data
     * @return the array list
     */
//ParseDataToArrayList
    public ArrayList<String> parseDataToArrayList(String data)
    {
        String[] splitDataLines = data.split("\n");
        ArrayList<String> arrayListSplitData = new ArrayList<>(Arrays.asList(splitDataLines));
        return arrayListSplitData;
    }
    //END OF ParseDataToArrayList

    /**
     * Gets file contents.
     *This method returns the information of the SSH connection as a String.
     * @return the file contents
     */
    public String getFileContents()
    {
        return fileContents;
    }


}
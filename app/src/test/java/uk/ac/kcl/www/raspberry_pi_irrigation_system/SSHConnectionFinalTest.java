package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by User on 19/03/2017.
 */
public class SSHConnectionFinalTest {

    final String expectedOutput = "2017-02-28/13:30/20.0/41.0/798/3.195";
    final String [] command = {"cd Desktop;cd FinalTest1;cat testDataBaseCollect.txt"};

    @Test
    public void getFileContents() throws Exception
    {
        String[] test = new String[3];
        test[0] = "fed682jklvdw2482";
        test[1] = "88.98.228.21";
        test[2] = "pi";
        SSHConnectionFinal ssh = new SSHConnectionFinal(command,false,test);
        assertEquals(ssh.getFileContents().trim(), expectedOutput);
    }

    @Test
    public void parseDataToHashMap()
    {
        String[] test = new String[3];
        test[0] = "fed682jklvdw2482";
        test[1] = "88.98.228.21";
        test[2] = "pi";
        SSHConnectionFinal ssh = new SSHConnectionFinal(command,false,test);
        String[] splitData = expectedOutput.split("/");
        ChartData expectedDataSet = new ChartData();
        expectedDataSet.insertData(splitData[1], splitData[2], splitData[3], splitData[4], splitData[5], 0);
        HashMap<String, ChartData> expectedContainer = new HashMap<>();
        expectedContainer.put(splitData[0], expectedDataSet);
        for(String key: expectedContainer.keySet())
        {
            ChartData dataSet = ssh.parseDataToTreeMap(ssh.getFileContents()).get(key);
            assertTrue(expectedContainer.get(key).equals(dataSet));
        }
    }

    @Test
    public void parseDataToArrayList()
    {
        String[] test = new String[3];
        test[0] = "fed682jklvdw2482";
        test[1] = "88.98.228.21";
        test[2] = "pi";
        SSHConnectionFinal ssh = new SSHConnectionFinal(command,false,test);
        ArrayList<String> arrayListSplitData = new ArrayList<String>();
        arrayListSplitData.add(expectedOutput);
        assertEquals(ssh.parseDataToArrayList(ssh.getFileContents()), arrayListSplitData);
    }

}
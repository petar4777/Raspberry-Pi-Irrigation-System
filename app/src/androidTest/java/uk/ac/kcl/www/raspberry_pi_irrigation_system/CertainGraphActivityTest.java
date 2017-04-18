package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Set;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.ac.kcl.www.raspberry_pi_irrigation_system.CertainGraphActivity.KEY;
import static uk.ac.kcl.www.raspberry_pi_irrigation_system.CertainGraphActivity.KEY3;
import static uk.ac.kcl.www.raspberry_pi_irrigation_system.CertainGraphActivity.KEY4;

/**
 * Created by User on 10/04/2017.
 */
public class CertainGraphActivityTest
{
    @Before
    public void before()
    {
        GraphActivity.whichActivity = 2;
    }
    @Rule

    public ActivityTestRule<CertainGraphActivity> mActivityRule = new ActivityTestRule<>(
            CertainGraphActivity.class);

    @Test
    public void activityExists()
    {
        Assert.assertNotNull(mActivityRule);
        Log.e("TEST", "PASSED 1st test");
    }

    @Test
    public void testBackButton()
    {
        onView(withId(R.id.ActivityBack)).check(matches(withText("Back")));

        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(GraphActivity.class.getName(),null,false);
        onView(withId(R.id.ActivityBack)).perform(click());
        GraphActivity nextActivity = (GraphActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }



    @Test
    public void testZoomOutButton()
    {
        onView(withId(R.id.ZoomOut)).check(matches(withText("Zoom out")));
    }

    @Test
    public void testZoomInButton()
    {
        onView(withId(R.id.ZoomIn)).check(matches(withText("Zoom in")));
    }
    @Test
    public void testSpinner()
    {
        String[] test = new String[3];
        test[0] = "fed682jklvdw2482";
        test[1] = "88.98.228.21";
        test[2] = "pi";
        Map<String, ChartData> chartData;
        SSHConnectionFinal sshFinal = new SSHConnectionFinal(new String[] {"cd Desktop;cd FinalTest1;cat database.txt"}, false, test);
        chartData = sshFinal.parseDataToTreeMap(sshFinal.getFileContents());
        Set<String> keys = chartData.keySet();
        String[] keyValues = keys.toArray(new String[chartData.size()]);
        for(int i = 0; i < chartData.size(); i++)
        {
            onData(allOf(is(instanceOf(String.class)), is(keyValues[i]))).perform(click());
            Log.e("TESTING", keyValues[i].toString());
        }
    }

}
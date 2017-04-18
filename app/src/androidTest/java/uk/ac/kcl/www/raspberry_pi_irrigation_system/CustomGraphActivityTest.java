package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.app.Instrumentation;
import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by User on 22/03/2017.
 */

public class CustomGraphActivityTest
{
    @Rule
    public ActivityTestRule<CustomGraphActivity> mActivityRule = new ActivityTestRule<>(
            CustomGraphActivity.class);

    @Test
    public void activityExists()
    {
        Assert.assertNotNull(mActivityRule);
        Log.e("TEST", "PASSED 1st test");
    }

    @Test
    public void testBackButton()
    {
        onView(withId(R.id.customGraphActivityBack)).check(matches(withText("Back")));
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(GraphActivity.class.getName(),null,false);
        onView(withId(R.id.customGraphActivityBack)).perform(click());
        GraphActivity nextActivity = (GraphActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testCheckBoxH()
    {
        onView(withId(R.id.checkBoxHum)).check(matches(withText("H")));
    }

    @Test
    public void testCheckBoxT()
    {
        onView(withId(R.id.checkBoxTemp)).check(matches(withText("T")));
    }

    @Test
    public void testCheckBoxM()
    {
        onView(withId(R.id.checkBoxMoist)).check(matches(withText("M")));
    }

    @Test
    public void testCheckBoxW()
    {
        onView(withId(R.id.checkBoxCons)).check(matches(withText("W")));
    }

    @Test
    public void testSpinner()
    {
        String[] test = new String[3];
        test[0] = "fed682jklvdw2482";
        test[1] = "88.98.228.21";
        test[2] = "pi";
        Map<String, ChartData> chartData;
        SSHConnectionFinal sshFinal = new SSHConnectionFinal(new String[] {"cd Desktop;cd FinalTest1;cat database.txt"}, false,test);
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

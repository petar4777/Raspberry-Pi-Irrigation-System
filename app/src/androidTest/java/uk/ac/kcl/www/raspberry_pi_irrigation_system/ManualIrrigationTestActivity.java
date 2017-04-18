package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Set;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by User on 22/03/2017.
 */

public class ManualIrrigationTestActivity
{
    @Rule
    public ActivityTestRule<ManualIrrigationActivity> mActivityRule = new ActivityTestRule<>(ManualIrrigationActivity.class);

    @Test
    public void activityExists()
    {
        Assert.assertNotNull(mActivityRule);
        Log.e("TEST", "PASSED 1st test");
    }

    @Test
    public void testTextView1ManualIrrigationActivity()
    {
        onView(withId(R.id.textView)).check(matches(withText("Create custom irrigation plan")));
    }

    @Test
    public void testTextView2ManualIrrigationActivity()
    {
        onView(withId(R.id.chooseTime)).check(matches(withText("Start at:")));
    }

    @Test
    public void testTextView3ManualIrrigationActivity()
    {
        onView(withId(R.id.chooseHowLong)).check(matches(withText("for:")));
    }

    @Test
    public void testUploadManualIrrigationActivity()
    {
        onView(withId(R.id.buttonUploadPlan)).check(matches(withText("Upload")));
    }

    @Test
    public void testReloadManualIrrigationActivity()
    {
        onView(withId(R.id.buttonGetPrevious)).check(matches(withText("Reload")));
    }

    @Test
    public void testDeleteManualIrrigationActivity()
    {
        onView(withId(R.id.buttonDeleteSelection)).check(matches(withText("Delete")));
    }

    @Test
    public void testAddManualIrrigationActivity()
    {
        onView(withId(R.id.buttonAddToListView)).check(matches(withText("Add")));
    }

    @Test
    public void testResetManualIrrigationActivity()
    {
        onView(withId(R.id.buttonResetListView)).check(matches(withText("Reset")));
    }

    @Test
    public void testBackButton()
    {
        onView(withId(R.id.manualIrrigationActivityBack)).check(matches(withText("Back")));
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null,false);
        onView(withId(R.id.manualIrrigationActivityBack)).perform(click());
        MainActivity nextActivity = (MainActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testSpinner1()
    {
        String[] timeValues = new String[24];
        timeValues[0] = "00:00";
        timeValues[1] = "01:00";
        timeValues[2] = "02:00";
        timeValues[3] = "03:00";
        timeValues[4] = "04:00";
        timeValues[5] = "05:00";
        timeValues[6] = "06:00";
        timeValues[7] = "07:00";
        timeValues[8] = "08:00";
        timeValues[9] = "09:00";
        timeValues[10] = "10:00";
        timeValues[11] = "11:00";
        timeValues[12] = "12:00";
        timeValues[13] = "13:00";
        timeValues[14] = "14:00";
        timeValues[15] = "15:00";
        timeValues[16] = "16:00";
        timeValues[17] = "17:00";
        timeValues[18] = "18:00";
        timeValues[19] = "19:00";
        timeValues[20] = "20:00";
        timeValues[21] = "21:00";
        timeValues[22] = "22:00";
        timeValues[23] = "23:00";
        for(int i = 0; i < timeValues.length; i++)
        {
            onView(withId(R.id.spinnerChooseTime)).perform(click());
            onData(is(timeValues[i])).perform(click());
            Log.e("TESTING", timeValues[i].toString());
        }
    }

    @Test
    public void testSpinner2()
    {
        String[] howLong = new String[5];
        howLong[0] = "2";
        howLong[1] = "4";
        howLong[2] = "6";
        howLong[3] = "8";
        howLong[4] = "10";
        for(int i = 0; i < howLong.length; i++)
        {
            onView(withId(R.id.spinnerChooseHowLong)).perform(click());
            onData(is(howLong[i])).perform(click());
            Log.e("TESTING", howLong[i].toString());
        }
    }

    @Test
    public void testListViewManualIrrigationActivity() throws InterruptedException
    {
        onView(withId(R.id.buttonAddToListView)).perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.manualIrrigationListView))
                .atPosition(0)
                .check(matches(withText("• Start at 00:00 for 2 sec")));
    }
}

package uk.ac.kcl.www.raspberry_pi_irrigation_system;

/**
 * Created by User on 21/03/2017.
 */
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Instrumentation;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.test.espresso.core.deps.guava.io.Resources;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.credentials.IdentityProviders;

import junit.framework.Assert;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;


public class MainActivityUITest
{
        @Rule
        public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
                MainActivity.class);

        @Test
        public void activityExists()
        {
            Assert.assertNotNull(mActivityRule);
                Log.e("TEST", "PASSED 1st test");
        }

        @Test
        public void testWeatherButtons()
        {
                WeatherForecastRequest request = new WeatherForecastRequest();
                while(request.getConnection().isAlive())
                {
//                        do nothing
                }
                String[] temperatureValues = request.getTemperatureValues();
                onView(withId(R.id.Today)).check(matches(withText("˄ " + temperatureValues[0] + "°C\n˅ " + temperatureValues[1] + "°C")));
                onView(withId(R.id.Tomorrow)).check(matches(withText("˄ " + temperatureValues[2] + "°C\n˅ " + temperatureValues[3] + "°C")));
                onView(withId(R.id.AfterTomorrow)).check(matches(withText("˄ " + temperatureValues[4] + "°C\n˅ " + temperatureValues[5] + "°C")));
                Log.e("TEST", "PASSED 2nd test");
        }

        @Test
        public void testHumTempMoistButtons()
        {
                String[] test = new String[3];
                 test[0] = "fed682jklvdw2482";
                 test[1] = "88.98.228.21";
                 test[2] = "pi";
                SSHConnectionFinal ssh = new SSHConnectionFinal(new String[] {"cd Desktop;cd FinalTest1;python3 getLastSensorValues.py"}, false, test);
                String fileContents = ssh.getFileContents();
                String[] splitDataFirst = fileContents.split("/");
                onView(withId(R.id.TempButtonMA)).check(matches(withText("T: "+splitDataFirst[0].trim()+"°C")));
                onView(withId(R.id.HumButtonMA)).check(matches(withText("H: "+splitDataFirst[1].trim()+"%")));
                onView(withId(R.id.MoistButtonMA)).check(matches(withText("M: "+splitDataFirst[2].trim())));
        }

        @Test
        public void testTextViewMainActivity()
        {
                onView(withId(R.id.textView2)).check(matches(withText("Choose automation method:")));
        }

        @Test
        public void testGraphButton()
        {
            Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(GraphActivity.class.getName(),null,false);
            onView(withId(R.id.mainActivityGraphs)).perform(click());
            GraphActivity nextActivity = (GraphActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
            Assert.assertNotNull(nextActivity);
            nextActivity.finish();
        }
    @Test
    public void testManualIrrigationButton()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ManualIrrigationActivity.class.getName(),null,false);
        onView(withId(R.id.manualIrrigationButton)).perform(click());
        ManualIrrigationActivity nextActivity = (ManualIrrigationActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testAutomaticBasedOnMoist() throws InterruptedException {
        Drawable test1 = mActivityRule.getActivity().automaticBOM.getBackground();
        onView(withId(R.id.automaticBasedOnMoistureButton)).perform(click());
        Thread.sleep(1000);
        Drawable test2 = mActivityRule.getActivity().automaticBOM.getBackground();
        Assert.assertFalse(test1.equals(test2));
    }

    @Test
    public void testAutomaticBasedOnTemperature() throws InterruptedException {
        Drawable test1 = mActivityRule.getActivity().automaticBOT.getBackground();
        onView(withId(R.id.automaticBasedOnTemperatureButton)).perform(click());
        Thread.sleep(1000);
        Drawable test2 = mActivityRule.getActivity().automaticBOT.getBackground();
        Assert.assertFalse(test1.equals(test2));
    }

    @Test
    public void testAutomaticBasedOnForecast() throws InterruptedException {
        Drawable test1 = mActivityRule.getActivity().automaticBOF.getBackground();
        onView(withId(R.id.automaticBasedOnForecastButton)).perform(click());
        Thread.sleep(1000);
        Drawable test2 = mActivityRule.getActivity().automaticBOF.getBackground();
        Assert.assertFalse(test1.equals(test2));
    }
}

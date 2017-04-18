package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by User on 10/04/2017.
 */
public class SignUpActivityTest
{
    @Rule
    public ActivityTestRule<SignUpActivity> mActivityRule = new ActivityTestRule<>(
            SignUpActivity.class);

    @Test
    public void activityExists()
    {
        Assert.assertNotNull(mActivityRule);
        Log.e("TEST", "PASSED 1st test");
    }

    @Test
    public void testLinkDeviceButton()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null,false);
        onView(withId(R.id.editTextSecondPIN)).perform(typeText("1234"));
        onView(withId(R.id.editTextFirstPIN)).perform(typeText("1234"));
        onView(withId(R.id.editTextUsernamePasswordA)).perform(typeText("fed682jklvdw2482"));
        onView(withId(R.id.editTextUsername)).perform(typeText("pi"));
        onView(withId(R.id.editTextIP)).perform(typeText("88.98.228.21"));
        pressBack();
        onView(withId(R.id.SignUpStop)).perform(click());
        MainActivity nextActivity = (MainActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testTextSIGNIN()
    {
        onView(withId(R.id.textViewGE)).check(matches(withText("Sign up to GardenExpert")));
    }

    @Test
    public void testTextIP()
    {
        onView(withId(R.id.textViewIP)).check(matches(withText("Enter the IP of your device below:")));
    }

    @Test
    public void testTextUsername()
    {
        onView(withId(R.id.textViewUsername)).check(matches(withText("Admin username:")));
    }

    @Test
    public void testTextUsernamePassword()
    {
        onView(withId(R.id.textViewUsernamePassword)).check(matches(withText("Password:")));
    }

    @Test
    public void testTextFirstPin()
    {
        onView(withId(R.id.textViewFirstPin)).check(matches(withText("Enter a 4 digit PIN:")));
    }


    @Test
    public void testTextSecondPin()
    {
        onView(withId(R.id.textViewSecondPin)).check(matches(withText("Confirm PIN:")));
    }

    @Test
    public void testTextViewChanging()
    {
        onView(withId(R.id.SignUpStop)).perform(click());
        onView(withId(R.id.textViewIP)).check(matches(withText("Please fill the IP.")));
        onView(withId(R.id.textViewUsername)).check(matches(withText("Please fill the username.")));
        onView(withId(R.id.textViewUsernamePassword)).check(matches(withText("Please fill the username password.")));
        onView(withId(R.id.textViewFirstPin)).check(matches(withText("Please enter a 4-digit PIN.")));
        onView(withId(R.id.textViewSecondPin)).check(matches(withText("Please confirm your PIN.")));
    }

    @Test
    public void testLoginBackButton()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ChooseSignOrLogActivity.class.getName(),null,false);
        onView(withId(R.id.SignUpBack)).perform(click());
        ChooseSignOrLogActivity nextActivity = (ChooseSignOrLogActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }
}
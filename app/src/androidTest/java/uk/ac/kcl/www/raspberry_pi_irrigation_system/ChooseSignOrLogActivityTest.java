package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by User on 10/04/2017.
 */
public class ChooseSignOrLogActivityTest
{
    @Rule
    public ActivityTestRule<ChooseSignOrLogActivity> mActivityRule = new ActivityTestRule<>(
            ChooseSignOrLogActivity.class);

    @Test
    public void activityExists()
    {
        Assert.assertNotNull(mActivityRule);
        Log.e("TEST", "PASSED 1st test");
    }

    @Test
    public void testLoginButton()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LoginScreenActivity.class.getName(),null,false);
        onView(withId(R.id.logInButton)).perform(click());
        LoginScreenActivity nextActivity = (LoginScreenActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testLinkDeviceButton()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(SignUpActivity.class.getName(),null,false);
        onView(withId(R.id.signUpButton)).perform(click());
        SignUpActivity nextActivity = (SignUpActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testTextViewChooseActivity()
    {
        onView(withId(R.id.textViewChooseActivity)).check(matches(withText("Welcome to GardenExpert")));
    }

    @Test
    public void testLoginButtonTextChooseActivity()
    {
        onView(withId(R.id.logInButton)).check(matches(withText("LOG IN")));
    }

    @Test
    public void testLinkDeviceTextButtonChooseActivity()
    {
        onView(withId(R.id.signUpButton)).check(matches(withText("LINK DEVICE")));
    }

}
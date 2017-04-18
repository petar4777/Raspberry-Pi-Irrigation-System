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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by User on 10/04/2017.
 */
public class LoginScreenActivityTest
{
    @Rule
    public ActivityTestRule<LoginScreenActivity> mActivityRule = new ActivityTestRule<>(
            LoginScreenActivity.class);

    @Test
    public void activityExists()
    {
        Assert.assertNotNull(mActivityRule);
        Log.e("TEST", "PASSED 1st test");
    }

    @Test
    public void testLoginButton()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null,false);
        onView(withId(R.id.LogInEditText)).perform(typeText("1234"));
        onView(withId(R.id.LogInWithAcc)).perform(click());
        MainActivity nextActivity = (MainActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testForgottenPinButton()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ForgottenPasswordActivity.class.getName(),null,false);
        onView(withId(R.id.ForgottenPass)).perform(click());
        ForgottenPasswordActivity nextActivity = (ForgottenPasswordActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testTextViewChooseActivity()
    {
        onView(withId(R.id.loginGardenExpertText)).check(matches(withText("Log in to your GardenExpert account")));
    }

    @Test
    public void testLoginBackButton()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ChooseSignOrLogActivity.class.getName(),null,false);
        onView(withId(R.id.LoginBack)).perform(click());
        ChooseSignOrLogActivity nextActivity = (ChooseSignOrLogActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testTextViewAboveLogin()
    {
        onView(withId(R.id.textView2222)).check(matches(withText("Enter your 4-digit PIN:")));
    }

    @Test
    public void testTextViewAboveLogin2()
    {
        onView(withId(R.id.LogInEditText)).perform(typeText("1224"));
        onView(withId(R.id.LogInWithAcc)).perform(click());
        onView(withId(R.id.textView2222)).check(matches(withText("Wrong PIN code.")));
    }
}
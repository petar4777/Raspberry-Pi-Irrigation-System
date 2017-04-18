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
 * Created by User on 11/04/2017.
 */
public class ForgottenPasswordActivityTest
{
    @Rule
    public ActivityTestRule<ForgottenPasswordActivity> mActivityRule = new ActivityTestRule<>(
            ForgottenPasswordActivity.class);

    @Test
    public void activityExists()
    {
        Assert.assertNotNull(mActivityRule);
        Log.e("TEST", "PASSED 1st test");
    }

    @Test
    public void testFPMAINButton()
    {
        onView(withId(R.id.editTextIPFP)).perform(typeText("88.98.228.21"));
        pressBack();
        onView(withId(R.id.editTextUsernameFP)).perform(typeText("pi"));
        pressBack();
        onView(withId(R.id.editTextUsernamePasswordFP)).perform(typeText("fed682jklvdw2482"));
        pressBack();
        onView(withId(R.id.RetrievePINButton)).perform(click());
        onView(withId(R.id.textViewHidded)).check(matches(withText("Your PIN code is:")));
        onView(withId(R.id.textViewPassForgot)).check(matches(withText("1234")));
    }

    @Test
    public void testFPMAINFAILButton()
    {
        onView(withId(R.id.editTextIPFP)).perform(typeText("88.98"));
        pressBack();
        onView(withId(R.id.editTextUsernameFP)).perform(typeText("pi"));
        pressBack();
        onView(withId(R.id.editTextUsernamePasswordFP)).perform(typeText("fed682jklvdw2482"));
        pressBack();
        onView(withId(R.id.RetrievePINButton)).perform(click());
        onView(withId(R.id.textViewHidded)).check(matches(withText("WRONG INFORMATION.")));
    }

    @Test
    public void testTextFORGOTPASS()
    {
        onView(withId(R.id.textView6FP)).check(matches(withText("Forgotten Pin Code")));
    }

    @Test
    public void testTextFORGOTPASS2()
    {
        onView(withId(R.id.textView6FFP)).check(matches(withText("To retrieve your PIN code, follow the instructions:")));
    }

    @Test
    public void testTextIPFP()
    {
        onView(withId(R.id.textViewIPFP)).check(matches(withText("Enter the IP of your registered device below:")));
    }

    @Test
    public void testTextUSERNAMEFP()
    {
        onView(withId(R.id.textViewUsernameFP)).check(matches(withText("Enter the registered admin username:")));
    }

    @Test
    public void testTextUSERNAMEPASSFP()
    {
        onView(withId(R.id.textViewUsernamePassFP)).check(matches(withText("Enter the registered username password:")));
    }


//    @Test
//    public void testTextSecondPin()
//    {
//        onView(withId(R.id.textViewSecondPin)).check(matches(withText("Confirm PIN:")));
//    }
//
//    @Test
//    public void testTextViewChanging()
//    {
//        onView(withId(R.id.SignUpStop)).perform(click());
//        onView(withId(R.id.textViewIP)).check(matches(withText("Please fill the IP.")));
//        onView(withId(R.id.textViewUsername)).check(matches(withText("Please fill the username.")));
//        onView(withId(R.id.textViewUsernamePassword)).check(matches(withText("Please fill the username password.")));
//        onView(withId(R.id.textViewFirstPin)).check(matches(withText("Please enter a 4-digit PIN.")));
//        onView(withId(R.id.textViewSecondPin)).check(matches(withText("Please confirm your PIN.")));
//    }

    @Test
    public void testFPBackButton()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LoginScreenActivity.class.getName(),null,false);
        onView(withId(R.id.ForgotPassBack)).perform(click());
        LoginScreenActivity nextActivity = (LoginScreenActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }
}
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


public class GraphActivityUITest
{
    @Rule
    public ActivityTestRule<GraphActivity> mActivityRule = new ActivityTestRule<>(
            GraphActivity.class);

    @Test
    public void activityExists()
    {
        Assert.assertNotNull(mActivityRule);
        Log.e("TEST", "PASSED 1st test");
    }

    @Test
    public void testTempGraphButton()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(CertainGraphActivity.class.getName(),null,false);
        onView(withId(R.id.tempTimeGraph)).perform(click());
        CertainGraphActivity nextActivity = (CertainGraphActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testHumGraphButton()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(CertainGraphActivity.class.getName(),null,false);
        onView(withId(R.id.humTimeGraph)).perform(click());
        CertainGraphActivity nextActivity = (CertainGraphActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testMoistGraphButton()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(CertainGraphActivity.class.getName(),null,false);
        onView(withId(R.id.moistTimeGraph)).perform(click());
        CertainGraphActivity nextActivity = (CertainGraphActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testWaterConsGraphButton()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(CertainGraphActivity.class.getName(),null,false);
        onView(withId(R.id.waterCons)).perform(click());
        CertainGraphActivity nextActivity = (CertainGraphActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testCustomGraphButton()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(CustomGraphActivity.class.getName(),null,false);
        onView(withId(R.id.customGraph)).perform(click());
        CustomGraphActivity nextActivity = (CustomGraphActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testBackButton()
    {
        onView(withId(R.id.graphActivityBack)).check(matches(withText("Back")));
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null,false);
        onView(withId(R.id.graphActivityBack)).perform(click());
        MainActivity nextActivity = (MainActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        Assert.assertNotNull(nextActivity);
        nextActivity.finish();
    }

}

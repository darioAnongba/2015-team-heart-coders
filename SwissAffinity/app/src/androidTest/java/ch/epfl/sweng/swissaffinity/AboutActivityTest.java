package ch.epfl.sweng.swissaffinity;

import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Max on 27/11/2015.
 */
public class AboutActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {


    private IntentServiceIdlingResource idlingResource;

    public AboutActivityTest() {
        super(MainActivity.class);
    }

    @Rule
    public ActivityTestRule<AboutActivity> activityRule = new ActivityTestRule<>(AboutActivity.class);

    @Before
    public void registerIntentIdling() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        idlingResource = new IntentServiceIdlingResource(instrumentation.getTargetContext());
        Espresso.registerIdlingResources(idlingResource);
    }

    @After
    public void unregisterIntentIdling() {
        Espresso.unregisterIdlingResources(idlingResource);
    }



    public void testlogin() {
        getActivity();
        onView(withId(R.id.action_about)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        pressBack();
        onView(withId(R.id.aboutLogedText)).check(matches(withText(R.string.welcome_not_logged_text)));
    }

    private class IntentServiceIdlingResource implements IdlingResource {
        private final Context context;
        private ResourceCallback resourceCallback;

        private IntentServiceIdlingResource(Context context) {
            this.context = context;
        }

        @Override
        public String getName() {
            return IntentServiceIdlingResource.class.getName();
        }

        @Override
        public boolean isIdleNow() {
            boolean idle = !isIntentServiceRunning();
            if (idle && resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
            }
            return idle;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
            this.resourceCallback = resourceCallback;
        }

        private boolean isIntentServiceRunning() {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo info : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (RepeatService.class.getName().equals(info.service.getClassName())) {
                    return true;
                }
            }
            return false;
        }
    }

}

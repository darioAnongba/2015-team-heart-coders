package ch.epfl.sweng.swissaffinity;

import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ch.epfl.sweng.swissaffinity.utilities.network.ServerTags;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by max on 28/11/2015.
 */
public class AboutActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public AboutActivityTest() {
        super(MainActivity.class);
    }

    @Rule
    public ActivityTestRule<AboutActivity> activityRule = new ActivityTestRule<>(AboutActivity.class);

    public void testLoginButton() {
        getActivity();
        onView(withId(R.id.action_about)).perform(click());
        if (!MainActivity.getSharedPrefs().getString(ServerTags.USERNAME.get(), "").equals("")) {
            onView(withId(R.id.login_button)).perform(click());
            pressBack();
            pressBack();
            onView(withId(R.id.mainWelcomeText)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.login_button)).perform(click());
            pressBack();
            onView(withId(R.id.aboutLogedText)).check(matches(withText(R.string.welcome_not_logged_text)));
        }
    }

}

package ch.epfl.sweng.swissaffinity;

import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.swissaffinity.utilities.network.ServerTags;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Lionel on 29.11.15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mActivityRule.getActivity();
    }

    @Test
    public void testGetSharedPrefs() throws Exception {
        SharedPreferences preferences = MainActivity.getPreferences();
        assert preferences != null;
    }

    @LargeTest
    public void testCanGreetUsers() {

        String userName = MainActivity.getPreferences().getString(ServerTags.USERNAME.get(), "");

        String welcomeText =
                String.format(
                        mActivityRule.getActivity()
                                     .getString(R.string.welcome_registered_text), userName);
        onView(withId(R.id.mainWelcomeText)).check(matches(withText(welcomeText)));
    }

    @LargeTest
    public void testAbout() {
        onView(withId(R.id.action_about)).perform(click());
        onView(withId(R.id.aboutSwissAffinityText)).check(matches(isDisplayed()));
    }

    @LargeTest
    public void testSettings() {
        onView(withId(R.id.action_settings)).perform(click());
        pressBack();

        String userName = MainActivity.getPreferences().getString(ServerTags.USERNAME.get(), "");

        String welcomeText = String.format(
                mActivityRule.getActivity().getString(R.string.welcome_registered_text),
                userName);
        onView(withId(R.id.mainWelcomeText)).check(matches(withText(welcomeText)));
    }
}
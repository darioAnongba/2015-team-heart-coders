package ch.epfl.sweng.swissaffinity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

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
public class AboutActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp() {
        mActivityRule.getActivity();
    }

    @Test
    public void testDisplayCorrectly() {
        onView(withId(R.id.action_about)).perform(click());
        onView(withId(R.id.aboutInfoText)).check(matches(withText(R.string.about_info_text)));
        onView(withId(R.id.aboutLeaderText)).check(matches(withText(R.string.about_leader)));
        onView(withId(R.id.aboutLinkText)).check(matches(withText(R.string.about_text_link)));
        onView(withId(R.id.aboutSwissAffinityText)).check(matches(withText(R.string.app_name)));
        onView(withId(R.id.aboutLogo)).check(matches(isDisplayed()));
    }

    @LargeTest
    public void testLoginButton() throws Exception {
        onView(withId(R.id.action_about)).perform(click());
        if (!MainActivity.getPreferences().getString(ServerTags.USERNAME.get(), "").equals("")) {
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
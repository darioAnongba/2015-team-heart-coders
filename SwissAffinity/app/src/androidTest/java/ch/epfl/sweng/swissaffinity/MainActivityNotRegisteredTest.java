package ch.epfl.sweng.swissaffinity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Test class for the MainActivity
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityNotRegisteredTest {

    private MainActivity activity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        MainActivity.USER_REGISTERED = false;
        activity = mActivityRule.getActivity();
    }

    @Test
    public void testTitle() {
        String title = (String) activity.getTitle();
        onView(withText(R.string.app_name)).check(matches(withText(title)));
    }

    @Test
    public void testMenuDisplayed() {
        openActionBarOverflowOrOptionsMenu(activity);
        onView(withId(R.id.action_about)).check(matches(isDisplayed()));
        onView(withId(R.id.action_settings)).check(matches(isDisplayed()));
    }

    @Test
    public void testLogoDisplayed() {
        onView(withId(R.id.mainLogo)).check(matches(isDisplayed()));
    }

    @Test
    public void testWelcomeText() {
        onView(withId(R.id.mainWelcomeText))
                .check(matches(withText(R.string.welcome_not_registered_text)));
    }

    @Test
    public void testButtonsDisplayed() {
        onView(withId(R.id.mainLoginButton)).check(matches(allOf(isDisplayed(), isClickable())));
        onView(withId(R.id.mainRegisterButton)).check(matches(allOf(isDisplayed(), isClickable())));
    }
}
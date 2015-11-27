package ch.epfl.sweng.swissaffinity;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import ch.epfl.sweng.swissaffinity.utilities.network.ServerTags;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by sahinfurkan on 26/11/15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    }

    public void testCanGreetUsers() {
        getActivity();

        String userName = MainActivity.getSharedPrefs().getString(ServerTags.USERNAME.get(), "");

        String welcomeText =
                String.format(getActivity().getString(R.string.welcome_registered_text), userName);
        onView(withId(R.id.mainWelcomeText)).check(matches(withText(welcomeText)));

    }

    public void testAbout() {
        getActivity();

        onView(withId(R.id.action_about)).perform(click());
        onView(withId(R.id.aboutSwissAffinityText)).check(matches(isDisplayed()));
    }

    public void testSettings() {
        getActivity();


        onView(withId(R.id.action_settings)).perform(click());
        pressBack();

        String userName = MainActivity.getSharedPrefs().getString(ServerTags.USERNAME.get(), "");

        String welcomeText = String.format(
                getActivity().getString(R.string.welcome_registered_text),
                userName);
        onView(withId(R.id.mainWelcomeText)).check(matches(withText(welcomeText)));


    }
}

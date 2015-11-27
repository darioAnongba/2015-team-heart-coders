package ch.epfl.sweng.swissaffinity;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EVENTS_ATTENDED;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;

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

        if (MainActivity.SHARED_PREFS.getString(USERNAME.get(), "").equals(""))
            onView(withId(R.id.mainWelcomeText)).check(matches(withText(R.string.welcome_not_registered_text)));
        else {
            String welcomeText = String.format(
                    MainActivity.mContext.getString(R.string.welcome_registered_text),
                    MainActivity.SHARED_PREFS.getString(USERNAME.get(), ""));
            onView(withId(R.id.mainWelcomeText)).check(matches(withText(welcomeText)));
        }
    }

    public void testAbout(){
        getActivity();

        onView(withId(R.id.action_about)).perform(click());
        onView(withId(R.id.aboutSwissAffinityText)).check(matches(isDisplayed()));
    }

    public void testSettings(){
        getActivity();
        onView(withId(R.id.action_settings)).perform(click());
        pressBack();

        if (MainActivity.SHARED_PREFS.getString(USERNAME.get(), "").equals(""))
            onView(withId(R.id.mainWelcomeText)).check(matches(withText(R.string.welcome_not_registered_text)));
        else {
            String welcomeText = String.format(
                    MainActivity.mContext.getString(R.string.welcome_registered_text),
                    MainActivity.SHARED_PREFS.getString(USERNAME.get(), ""));
            onView(withId(R.id.mainWelcomeText)).check(matches(withText(welcomeText)));

        }
    }

    public void testEventListAttended(){
        getActivity();

        assert(MainActivity.mListAdapter.getChildrenCount(0)
                == MainActivity.mUser.getEventsAttended().size());
    }

    public void testGroupNumOfEventList(){
        getActivity();
        if (MainActivity.SHARED_PREFS.getString(USERNAME.get(), "").equals(""))
            assert(MainActivity.mListAdapter.getGroupCount() == 0);
        else{
            assert(MainActivity.mListAdapter.getGroupCount() == 2);
        }
    }

}
